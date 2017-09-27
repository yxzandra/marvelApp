package com.nextdots.marvelapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nextdots.marvelapp.API.Models.Result;
import com.nextdots.marvelapp.DataBase.DataBaseTransaction;
import com.nextdots.marvelapp.DataBase.FavoriteComicTable;
import com.nextdots.marvelapp.Interface.OnClickRecyclerViewInterface;
import com.nextdots.marvelapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by yxzan on 07/02/2017.
 */

public class ComicAllAdapter extends RecyclerView.Adapter<ComicAllAdapter.ViewHolder> {
    DataBaseTransaction dataBaseTransaction;


    private List<Result> arrayList;
    private OnClickRecyclerViewInterface onClickRecyclerViewInterface;
    private Context context;

    public ComicAllAdapter(List<Result> arrayList, OnClickRecyclerViewInterface onClickRecyclerViewInterface, Context context) {
        this.arrayList = arrayList;
        this.onClickRecyclerViewInterface = onClickRecyclerViewInterface;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item_all_comic, parent, false);

        return new ViewHolder(v, new ViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClick(View caller, int position) {
                if (arrayList !=  null) {
                    onClickRecyclerViewInterface.onItemClicked(position, context.getString(R.string.FlagComicDetailsNotDataBase));
                }else {
                    onClickRecyclerViewInterface.onItemClicked(position, context.getString(R.string.FlagTypeComicDetailsDataBase));
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (arrayList != null) {

            holder.textNameFile.setText(arrayList.get(position).getTitle());
            if (arrayList.get(position).getImages().size() != 0) {
                String image = arrayList.get(position).getImages().get(0).getPath() + "." + arrayList.get(position).getImages().get(0).getExtension();
                Glide.with(context).load(image).into(holder.fondoimage);
                onClickFavouriteImage(holder, position, image);
            }else {
                onClickFavouriteImage(holder, position, null);
            }


        }else {
            dataBaseTransaction = new DataBaseTransaction();
            dataBaseTransaction.InitializeDatabase(context);
            RealmResults<FavoriteComicTable> favoriteComicTable = dataBaseTransaction.getAll();
            holder.textNameFile.setText(favoriteComicTable.get(position).getNameComic());
            Glide.with(context).load(favoriteComicTable.get(position).getImageComic()).into(holder.fondoimage);
            holder.imageFavourite.setVisibility(View.GONE);
        }

    }

    private void onClickFavouriteImage(final ComicAllAdapter.ViewHolder holder, final int position, final String image) {
        final DataBaseTransaction dataBaseTransaction = new DataBaseTransaction();
        dataBaseTransaction.InitializeDatabase(context);

        if (dataBaseTransaction.searchComic(arrayList.get(position).getTitle())){
            holder.imageFavourite.setBackgroundResource(R.drawable.ic_star_on);
        }else {
            holder.imageFavourite.setBackgroundResource(R.drawable.ic_star_off);
        }

        holder.imageFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.imageFavourite.getBackground().getConstantState().equals(context.getResources().getDrawable(R.drawable.ic_star_off).getConstantState())) {
                    holder.imageFavourite.setBackgroundResource(R.drawable.ic_star_on);
                    if (!dataBaseTransaction.searchComic(arrayList.get(position).getTitle()))
                        dataBaseTransaction.addComic(arrayList.get(position).getTitle(),arrayList.get(position).getDescription(),image);

                } else {
                    holder.imageFavourite.setBackgroundResource(R.drawable.ic_star_off);
                    dataBaseTransaction.deleteFavourite(arrayList.get(position).getTitle());
                }

                //onClickRecyclerViewInterface.updateFavourite(true);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (arrayList != null) {
            return arrayList.size();
        }else {
            dataBaseTransaction = new DataBaseTransaction();
            dataBaseTransaction.InitializeDatabase(context);
            return dataBaseTransaction.sizeComic();
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.fondoimage)
        ImageView fondoimage;
        @BindView(R.id.imageFavourite)
        ImageView imageFavourite;
        @BindView(R.id.btnShareMore)
        Button btnShareMore;
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
            mListener.onItemClick(view, getPosition());
        }
    }

}