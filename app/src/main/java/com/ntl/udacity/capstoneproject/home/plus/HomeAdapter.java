package com.ntl.udacity.capstoneproject.home.plus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>
{
    private List<BookItem> mBookItemList;
    private Context mContext;
    private HomeFragment mHomeFragment;

    public HomeAdapter(List<BookItem> bookItemList, Context context)
    {
        this.mBookItemList = bookItemList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_book_search, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        try
        {
            Picasso.with(mContext).load(mBookItemList.get(position)
                    .getVolumeInfo().getImageLinks().getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.imageView);
        } catch (NullPointerException e)
        {
            Timber.e(e);
            //TODO get place holder for error images
            Picasso.with(mContext).load(R.mipmap.ic_launcher)
                    .into(holder.imageView);
        }

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

    public void setHandleClick(HomeFragment homeFragment)
    {
        this.mHomeFragment = homeFragment;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.imageview_book_thumbnail)
        ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            mHomeFragment.onBookClick(getAdapterPosition());

        }
    }
}
