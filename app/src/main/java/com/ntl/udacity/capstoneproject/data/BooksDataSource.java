package com.ntl.udacity.capstoneproject.data;


import com.ntl.udacity.capstoneproject.data.model.BookItem;

import java.util.List;

public interface BooksDataSource
{

    interface getAccesstokenCallback {

        void onTokenLoaded(String acesstoken,String refreshAccesstoken);

        void onThereIsError();
    }

    interface getSearchBookCallback {

        void onBooksListLoaded(List<BookItem> bookItemsList);

        void onThereIsError();
    }

    void getAccesstoken(getAccesstokenCallback callback,String code,String clientId,String redirectUri,String grantType);
    void searchSpecificBook(getSearchBookCallback callback ,String query);
}
