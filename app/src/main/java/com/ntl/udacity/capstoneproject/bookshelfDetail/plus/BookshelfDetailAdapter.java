package com.ntl.udacity.capstoneproject.bookshelfDetail.plus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.bookshelfDetail.BookshelfDetailContract;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class BookshelfDetailAdapter extends RecyclerView.Adapter<BookshelfDetailAdapter.ViewHolder>
{
    private List<BookItem> mBookItemList;
    private BookshelfDetailContract.View mBookshelfDetailFragment;
    private Context mContext;

    public BookshelfDetailAdapter(List<BookItem> mBookItemList, BookshelfDetailContract.View bookshelfDetailFragment, Context mContext)
    {
        this.mBookItemList = mBookItemList;
        this.mBookshelfDetailFragment = bookshelfDetailFragment;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookshelf_book, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        try
        {
            Picasso.with(mContext).load(mBookItemList.get(holder.getAdapterPosition())
                    .getVolumeInfo().getImageLinks().getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.book_iv);
        } catch (NullPointerException e)
        {
            Timber.e(e);
            //TODO get place holder for error images
            Picasso.with(mContext).load(R.mipmap.ic_launcher)
                    .into(holder.book_iv);
        }
        holder.book_iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mBookshelfDetailFragment.onBookClicked(holder.getAdapterPosition());
            }
        });
        holder.remove_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mBookshelfDetailFragment.removeBook(mBookItemList.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if (mBookItemList != null)
            return mBookItemList.size();
        return 0;
    }

    public void setItems(List<BookItem> bookItemList)
    {
        this.mBookItemList = bookItemList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.button_item_book_remove)
        Button remove_btn;
        @BindView(R.id.imageview_item_thumbnail)
        ImageView book_iv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
