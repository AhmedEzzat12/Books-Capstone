package com.ntl.udacity.capstoneproject.myLibrary.plus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.data.model.BookShelfItem;
import com.ntl.udacity.capstoneproject.myLibrary.MylibraryFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLibraryAdapter extends RecyclerView.Adapter<MyLibraryAdapter.ViewHolder>
{

    private List<BookShelfItem> mBookShelfItemList;
    private MylibraryFragment mMylibraryFragment;

    public MyLibraryAdapter(List<BookShelfItem> mBookShelfItemList, MylibraryFragment mMylibraryFragment)
    {
        this.mBookShelfItemList = mBookShelfItemList;
        this.mMylibraryFragment = mMylibraryFragment;
    }

    public void setItems(List<BookShelfItem> bookShelfItems)
    {
        this.mBookShelfItemList = bookShelfItems;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookshelf_mylibrary, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.title_tv.setText(mBookShelfItemList.get(position).getTitle());
        holder.numberOfVolumes_tv.setText(mBookShelfItemList.get(position).getVolumeCount());
    }

    @Override
    public int getItemCount()
    {
        if (mBookShelfItemList != null)
            return mBookShelfItemList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.textView_mylibrary_bookshelf_title)
        TextView title_tv;
        @BindView(R.id.textView_mylibrary_bookshelf_numofvol)
        TextView numberOfVolumes_tv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mMylibraryFragment.onBookshelfClicked(getAdapterPosition());
                }
            });
        }
    }
}
