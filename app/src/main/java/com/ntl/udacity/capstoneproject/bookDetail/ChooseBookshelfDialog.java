package com.ntl.udacity.capstoneproject.bookDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.data.model.BookShelfItem;
import com.ntl.udacity.capstoneproject.data.model.BookShelfResponse;
import com.ntl.udacity.capstoneproject.data.remote.BooksClient;
import com.ntl.udacity.capstoneproject.data.remote.BooksInterface;
import com.ntl.udacity.capstoneproject.data.remote.EmptyResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class ChooseBookshelfDialog extends DialogFragment
{
    @BindView(R.id.spinner_bookshelf_dialog)
    Spinner bookshelves_spn;
    @BindView(R.id.progressbar_bookshelf_dialog)
    ProgressBar progressBar;
    @BindView(R.id.button_bookDetail_dialog)
    Button addBook_btn;
    @BindView(R.id.button_bookDetail_dialog_dismiss)
    Button dismiss_btn;

    private BookItem mBookItem;
    private BookShelfItem bookShelfItem;
    private BooksInterface mBooksInterface;
    private Unbinder unbinder;

    public static ChooseBookshelfDialog getInstance(BookItem mBookItem)
    {
        ChooseBookshelfDialog chooseBookshelfDialog = new ChooseBookshelfDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BookDetailActivity.BOOK, mBookItem);
        chooseBookshelfDialog.setArguments(bundle);
        return chooseBookshelfDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_book_to_bookshelf, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null)
        {
            mBookItem = getArguments().getParcelable(BookDetailActivity.BOOK);
            mBooksInterface = BooksClient.getClient(getActivity());
            getBookshelVes();
        }
        return view;
    }

    private void getBookshelVes()
    {
        Call<BookShelfResponse> bookShelfResponseCall = mBooksInterface.getUserBookshelves();
        bookShelfResponseCall.enqueue(new Callback<BookShelfResponse>()
        {
            @Override
            public void onResponse(@NonNull Call<BookShelfResponse> call, @NonNull Response<BookShelfResponse> response)
            {
                if (response.body() != null)
                {
                    isLoading(true);
                    loadDataToSpinner(response.body().getItems());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookShelfResponse> call, @NonNull Throwable t)
            {
                Timber.e(t);
                dismiss();
            }
        });
    }

    public void isLoading(boolean b)
    {
        if (b)
        {
            progressBar.setVisibility(View.VISIBLE);
            bookshelves_spn.setVisibility(View.GONE);
            addBook_btn.setVisibility(View.GONE);
            dismiss_btn.setVisibility(View.GONE);

        }
        progressBar.setVisibility(View.GONE);
        bookshelves_spn.setVisibility(View.VISIBLE);
        addBook_btn.setVisibility(View.VISIBLE);
        dismiss_btn.setVisibility(View.VISIBLE);

    }

    private void loadDataToSpinner(final List<BookShelfItem> items)
    {
        List<String> stringList = new ArrayList<>();
        for (BookShelfItem item : items)
        {
            stringList.add(item.getTitle());
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity()
                , android.R.layout.simple_list_item_1, android.R.id.text1, stringList);

        bookshelves_spn.setAdapter(stringArrayAdapter);
        bookShelfItem = items.get(0);
        bookshelves_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                bookShelfItem = items.get(parent.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    @Override
    public void onResume()
    {
        try
        {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            getDialog().getWindow().setLayout((getDialog().getWindow().getAttributes().width * 6) / 7, displayMetrics.heightPixels / 2);
        } catch (Exception e)
        {
            Timber.e(e);
        }
        super.onResume();
    }

    @OnClick(R.id.button_bookDetail_dialog)
    public void addToBookshelf()
    {
        isLoading(true);
        Call<EmptyResponse> addVolRequest = mBooksInterface.addVolumeToBookshelf(bookShelfItem.getId()
                , mBookItem.getId());
        addVolRequest.enqueue(new Callback<EmptyResponse>()
        {
            @Override
            public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response)
            {
                isLoading(false);
                if (response.code() == 204)
                {
                    Timber.d(response.message());
                    Toast.makeText(getContext(), R.string.success_book_added, Toast.LENGTH_SHORT).show();
                    dismiss();
                } else if (response.code() == 403)
                {
                    Toast.makeText(getContext(), R.string.fail_add_to_bookshelf, Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t)
            {
                isLoading(false);
                Timber.e(t);
                dismiss();
            }
        });
    }

    @OnClick(R.id.button_bookDetail_dialog_dismiss)
    public void dismissDialoge()
    {
        dismiss();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }


}
