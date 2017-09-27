package com.nextdots.marvelapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nextdots.marvelapp.DataBase.DataBaseTransaction;
import com.nextdots.marvelapp.DataBase.FavoriteComicTable;
import com.nextdots.marvelapp.Interface.OnClickRecyclerViewInterface;
import com.nextdots.marvelapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by yxzan on 07/02/2017.
 */

public class ComicFavouriteAdapter extends RecyclerView.Adapter<ComicFavouriteAdapter.ViewHolder> {
    DataBaseTransaction dataBaseTransaction;

    private OnClickRecyclerViewInterface onClickRecyclerViewInterface;
    private Context context;

    public ComicFavouriteAdapter(OnClickRecyclerViewInterface onClickRecyclerViewInterface, Context context) {
        this.onClickRecyclerViewInterface = onClickRecyclerViewInterface;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item_comic_favourite, parent, false);

        return new ViewHolder(v, new ViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClick(View caller, int position) {
                onClickRecyclerViewInterface.onItemClicked(position, context.getString(R.string.FlagTypeComicDetailsDataBase));
            }
        });
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        dataBaseTransaction = new DataBaseTransaction();
        dataBaseTransaction.InitializeDatabase(context);

        RealmResults<FavoriteComicTable> favoriteComicTable = dataBaseTransaction.getAll();
        holder.textNameFile.setText(favoriteComicTable.get(position).getNameComic());
        Glide.with(context).load(favoriteComicTable.get(position).getImageComic()).into(holder.fondoimage);
    }


    @Override
    public int getItemCount() {
        dataBaseTransaction = new DataBaseTransaction();
        dataBaseTransaction.InitializeDatabase(context);
        return dataBaseTransaction.sizeComic();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.fondoimage)
        ImageView fondoimage;
        @BindView(R.id.textNameFile)
        TextView textNameFile;

        interface ViewHolderClickListener {
            void onItemClick(View caller, int position);
        }

        ViewHolderClickListener mListener;

        ViewHolder(View itemView, ViewHolderClickListener listener) {
            super(itemView);
            mListener = listener;
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("Clicked");
            mListener.onItemClick(view, getPosition());
        }
    }

}