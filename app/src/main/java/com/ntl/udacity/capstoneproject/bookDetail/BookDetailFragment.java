package com.ntl.udacity.capstoneproject.bookDetail;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.common.base.MoreObjects;
import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

public class BookDetailFragment extends Fragment implements BookDetailContract.View
{
    @BindView(R.id.textView_bookDetail_title)
    TextView title_tv;
    @BindView(R.id.textView_bookDetail_author)
    TextView author_tv;
    @BindView(R.id.textView_bookDetail_descriptionHolder)
    TextView description_tv;
    @BindView(R.id.textView_bookDetail_publisher)
    TextView publisher_tv;
    @BindView(R.id.textView_bookDetail_publishDate)
    TextView publishDate_tv;
    @BindView(R.id.button_bookDetail_add)
    TextView addToShelf_btn;
    @BindView(R.id.button_bookDetail_openPage)
    TextView openBookPage_btn;
    @BindView(R.id.imageView_bookDetail)
    ImageView bookThumbnail_iv;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.adView)
    AdView mAdView;

    private BookItem mBookItem;
    private BookDetailContract.Presenter mBookDetailPresenter;
    private Unbinder unbinder;

    public static BookDetailFragment getInstance(BookItem bookItem)
    {
        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BookDetailActivity.BOOK, bookItem);
        bookDetailFragment.setArguments(bundle);
        return bookDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            this.mBookItem = bundle.getParcelable(BookDetailActivity.BOOK);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(BookDetailContract.Presenter presenter)
    {
        this.mBookDetailPresenter = presenter;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mBookDetailPresenter.start();
    }

    @Override
    public void showBookDetail()
    {

        if (mBookItem.getVolumeInfo().getAuthors() != null)
        {
            author_tv.setText(MoreObjects.firstNonNull(mBookItem.getVolumeInfo().getAuthors().get(0), ""));
        } else
        {
            author_tv.setText("");
        }


        title_tv.setText(MoreObjects.firstNonNull(mBookItem.getVolumeInfo().getTitle(), ""));
        title_tv.setText(MoreObjects.firstNonNull(mBookItem.getVolumeInfo().getTitle(), ""));
        description_tv.setText(MoreObjects.firstNonNull(mBookItem.getVolumeInfo().getDescription(), ""));
        publishDate_tv.setText(MoreObjects.firstNonNull(mBookItem.getVolumeInfo().getPublishedDate(), ""));
        publisher_tv.setText(MoreObjects.firstNonNull(mBookItem.getVolumeInfo().getPublisher(), ""));
        ratingBar.setRating(Float.parseFloat(MoreObjects.firstNonNull(mBookItem.getVolumeInfo().getAverageRating(), "0")));
        try
        {
            Picasso.with(getActivity()).load(checkNotNull(mBookItem.getVolumeInfo().getImageLinks().getThumbnail())).into(bookThumbnail_iv);
        } catch (NullPointerException e)
        {
            Timber.e(e);
            //TODO thumbnail not available
        }
    }

    @OnClick(R.id.button_bookDetail_addToFav)
    @Override
    public void addBookToFavourite()
    {
        Bitmap bitmap = ((BitmapDrawable) bookThumbnail_iv.getDrawable()).getBitmap();
        mBookDetailPresenter.addBookToFavourite(mBookItem, bitmap, getActivity());
    }

    @OnClick(R.id.button_bookDetail_removeFromFav)
    @Override
    public void removeBookFromFavourite()
    {
        mBookDetailPresenter.removeBookFromFavourite(mBookItem.getId(), getActivity());
    }


    @OnClick(R.id.button_bookDetail_add)
    @Override
    public void chooseBookshelf()
    {
        ChooseBookshelfDialog chooseBookshelfFragment = ChooseBookshelfDialog.getInstance(mBookItem);
        chooseBookshelfFragment.setCancelable(false);
        chooseBookshelfFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.add_to_bookshelf_tag));
    }

    @Override
    public void onBookAddedToFavourite(Uri uri)
    {
        Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_SHORT).show();
        //UpdateWidgetData.startActionUpdateData(getActivity());
    }

    @Override
    public void onBookFailAddToFavourite()
    {
        Toast.makeText(getActivity(), R.string.book_failed_add_to_fav, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBookRemovedFromFavourite()
    {
        Toast.makeText(getActivity(), R.string.success_book_removed, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFailRemoveFromFavourite()
    {
        Toast.makeText(getActivity(), R.string.fail_book_remove, Toast.LENGTH_LONG).show();

    }

    @OnClick(R.id.button_bookDetail_openPage)
    public void openBookPage()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mBookItem.getVolumeInfo().getInfoLink()));
        getActivity().startActivity(intent);
    }
}

