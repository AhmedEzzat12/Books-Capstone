package com.ntl.udacity.capstoneproject.myLibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.bookshelfDetail.BookshelfDetailActivity;
import com.ntl.udacity.capstoneproject.data.model.BookShelfItem;
import com.ntl.udacity.capstoneproject.home.plus.RecyclerViewWithEmpty;
import com.ntl.udacity.capstoneproject.myLibrary.plus.MyLibraryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MylibraryFragment extends Fragment implements MyLibraryContract.View
{


    @BindView(R.id.recylcerview_mylibrary)
    RecyclerViewWithEmpty mRecyclerView;
    @BindView(R.id.textview_mylibrary_emptyview)
    TextView empty_tv;
    @BindView(R.id.progressbar_mylibrary)
    ProgressBar progressBar;

    private MyLibraryContract.Presenter mPresenter;
    private Unbinder unbinder;
    private MyLibraryAdapter mMyLibraryAdapter;
    private List<BookShelfItem> mBookshelfItems;

    public static MylibraryFragment getInstance()
    {
        return new MylibraryFragment();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Timber.d("ondCreate has been called");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_mylibrary, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter.loodBookshelves();
        mMyLibraryAdapter = new MyLibraryAdapter(mBookshelfItems, this);
        mRecyclerView.setAdapter(mMyLibraryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setEmptyView(empty_tv);
        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
        Timber.d("onDestroy has been called");

    }

    @Override
    public void setPresenter(MyLibraryContract.Presenter presenter)
    {
        this.mPresenter = presenter;
    }


    @Override
    public void BookshelvesLoaded(List<BookShelfItem> bookShelfItems)
    {
        progressBar.setVisibility(View.GONE);
        this.mBookshelfItems = bookShelfItems;
        mMyLibraryAdapter.setItems(bookShelfItems);
    }

    @Override
    public void onBookshelfClicked(int adapterPosition)
    {
        Intent intent = new Intent(getActivity(), BookshelfDetailActivity.class);
        intent.putExtra(BookshelfDetailActivity.BOOKSHELF_ID, mBookshelfItems.get(adapterPosition).getId());
        getActivity().startActivity(intent);
    }
}
