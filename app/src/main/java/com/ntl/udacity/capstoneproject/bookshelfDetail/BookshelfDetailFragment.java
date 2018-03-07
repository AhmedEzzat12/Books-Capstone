package com.ntl.udacity.capstoneproject.bookshelfDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.bookDetail.BookDetailActivity;
import com.ntl.udacity.capstoneproject.bookshelfDetail.plus.BookshelfDetailAdapter;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.home.plus.RecyclerViewWithEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

public class BookshelfDetailFragment extends Fragment implements BookshelfDetailContract.View
{


    @BindView(R.id.recylcerview_bookshelf_detail)
    RecyclerViewWithEmpty mRecyclerView;
    @BindView(R.id.progressbar_bookshelf_detail)
    ProgressBar progressBar;
    @BindView(R.id.textview_bookshelf_detail_emptyview)
    TextView empty_tv;
    @BindView(R.id.button_bookshelf_detail_clearAll)
    Button clearAll_btn;

    private BookshelfDetailContract.Presenter mPresenter;
    private Unbinder unbinder;
    private List<BookItem> mBookItemList;
    private BookshelfDetailAdapter mBookshelfDetailAdapter;
    private String bookshelfId;

    public static BookshelfDetailFragment getInstance(String bookshelfId)
    {
        BookshelfDetailFragment bookshelfDetailFragment = new BookshelfDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BookshelfDetailActivity.BOOKSHELF_ID, bookshelfId);
        bookshelfDetailFragment.setArguments(bundle);
        return bookshelfDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        //setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            bookshelfId = bundle.getString(BookshelfDetailActivity.BOOKSHELF_ID);
        }

        mBookshelfDetailAdapter = new BookshelfDetailAdapter(mBookItemList, this, getActivity());
        mRecyclerView.setAdapter(mBookshelfDetailAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setEmptyView(empty_tv);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPresenter.getBooks(String.valueOf(bookshelfId));

    }

    @Override
    public void setPresenter(BookshelfDetailContract.Presenter presenter)
    {
        this.mPresenter = presenter;
    }

    @Override
    public void onBooksLoaded(List<BookItem> bookItemList)
    {
        progressBar.setVisibility(View.GONE);
        this.mBookItemList = bookItemList;
        mBookshelfDetailAdapter.setItems(mBookItemList);
    }

    @Override
    public void onBookRemoved()
    {
        Toast.makeText(getActivity(), R.string.success_book_removed, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        mPresenter.getBooks(String.valueOf(bookshelfId));
    }

    @Override
    public void onBookClicked(int adapterPosition)
    {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BookDetailActivity.BOOK, mBookItemList.get(adapterPosition));
        intent.putExtra(BookDetailActivity.BOOK_BUNDLE, bundle);
        getActivity().startActivity(intent);

    }

    @OnClick(R.id.button_bookshelf_detail_clearAll)
    @Override
    public void clearBooks()
    {
        mPresenter.clearBookshelf(bookshelfId);
        mBookshelfDetailAdapter.setItems(null);
    }

    @Override
    public void removeBook(String id)
    {
        mPresenter.removeBook(String.valueOf(bookshelfId), id);
    }

    @Override
    public void onThereAreNoBooks()
    {
        progressBar.setVisibility(View.GONE);
        empty_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBookshelfCleared()
    {
        Toast.makeText(getActivity(), R.string.bookshelf_cleared, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
        Timber.d("onDestroy has been called");
    }

}
