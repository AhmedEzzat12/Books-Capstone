package com.ntl.udacity.capstoneproject.home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.bookDetail.BookDetailActivity;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.home.plus.HomeAdapter;
import com.ntl.udacity.capstoneproject.home.plus.RecyclerViewWithEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class HomeFragment extends Fragment implements HomeContract.View
{

    @BindView(R.id.recylcerview_home)
    RecyclerViewWithEmpty mRecyclerView;
    @BindView(R.id.textview_home_emptyview)
    TextView mEmptyView;

    private List<BookItem> mBookItemList;
    private HomeContract.Presenter mHomePresenter;
    private HomeAdapter mHomeAdapter;
    private Unbinder unbinder;
    private boolean twoPane;
    private OnBookIsSelected mOnBookIsSelected;

    public static HomeFragment getInstance(boolean twoPane)
    {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(HomeActivity.TWOPANE, twoPane);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    public void setmOnBookIsSelected(OnBookIsSelected mOnBookIsSelected)
    {
        this.mOnBookIsSelected = mOnBookIsSelected;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        Timber.d("ondCreate has been called");
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            this.twoPane = arguments.getBoolean(HomeActivity.TWOPANE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = inflater.getContext();
        unbinder = ButterKnife.bind(this, v);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mHomeAdapter = new HomeAdapter(mBookItemList, context);
        mRecyclerView.setAdapter(mHomeAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
        mHomeAdapter.setHandleClick(this);

        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
        Timber.d("onDestroy has been called");
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter)
    {
        mHomePresenter = presenter;
    }


    @Override
    public void booksAreReady(List<BookItem> bookItemList)
    {
        this.mBookItemList = bookItemList;
        mHomeAdapter.setItems(mBookItemList);
    }

    @Override
    public void cantLoadBooks()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.invalid_book_name);
        builder.setMessage(R.string.please_try_valid_book_name);
        builder.show();
    }

    @Override
    public void onBookClick(int adapterPosition)
    {
        if (twoPane)
        {
            mOnBookIsSelected.bookSelected(mBookItemList.get(adapterPosition));
        } else
        {
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(BookDetailActivity.BOOK, mBookItemList.get(adapterPosition));
            intent.putExtra(BookDetailActivity.BOOK_BUNDLE, bundle);
            getActivity().startActivity(intent);
        }
    }

    public void getBooks(String query)
    {
        mHomePresenter.getBooks(query);
    }
}
