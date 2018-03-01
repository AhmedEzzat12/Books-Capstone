package com.ntl.udacity.capstoneproject.home;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.home.plus.HomeAdapter;
import com.ntl.udacity.capstoneproject.home.plus.RecyclerViewWithEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    public static HomeFragment getInstance()
    {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter)
    {
        mHomePresenter = presenter;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.my_library:

                Toast.makeText(getActivity(), "my_library touched", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.logout:
                Toast.makeText(getActivity(), "my_library touched", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
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
        builder.setTitle("Invalid book name");
        builder.setMessage("please try a valid bookname");
        builder.show();
    }

    public void getBooks(String query)
    {

        mHomePresenter.getBooks(query);
    }
}
