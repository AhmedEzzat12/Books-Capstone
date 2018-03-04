package com.ntl.udacity.capstoneproject.data;


import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.data.model.BookShelfItem;

import java.util.List;

public interface BooksDataSource
{

    void getAccesstoken(GetAccesstokenCallback callback, String code, String clientId, String redirectUri, String grantType);

    void searchSpecificBook(GetSearchBookCallback callback, String query);

    void getUserbookshelves(UserBookshelvesCallback callback);

    interface ErrorHandling
    {
        void onThereIsError();
    }

    interface UserBookshelvesCallback extends ErrorHandling
    {
        void onBookshelvesLoaded(List<BookShelfItem> bookShelfItems);
    }

    interface GetAccesstokenCallback extends ErrorHandling
    {

        void onTokenLoaded(String acesstoken, String refreshAccesstoken);

    }

    interface GetSearchBookCallback extends ErrorHandling
    {

        void onBooksListLoaded(List<BookItem> bookItemsList);

    }


}
