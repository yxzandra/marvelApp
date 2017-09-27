package com.nextdots.marvelapp.Fragments;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nextdots.marvelapp.API.Models.Result;
import com.nextdots.marvelapp.DataBase.DataBaseTransaction;
import com.nextdots.marvelapp.DataBase.FavoriteComicTable;
import com.nextdots.marvelapp.R;


import butterknife.BindView;
import io.realm.RealmResults;

public class DetailsItemFragment extends BaseFragment {
    static Result comic;
    static String position = "null";
    static String type;

    @BindView(R.id.imageComic)
    ImageView imageComic;
    @BindView(R.id.textTitleComic)
    TextView textTitleComic;
    @BindView(R.id.textBodyComic)
    TextView textBodyComic;

    public DetailsItemFragment() {
    }

    public static DetailsItemFragment newInstance(Result comic, String type) {

        DetailsItemFragment.type = type;
        DetailsItemFragment fragment = new DetailsItemFragment();
        DetailsItemFragment.comic = comic;
        return fragment;
    }

    public static DetailsItemFragment newInstance(String position, String title) {

        DetailsItemFragment fragment = new DetailsItemFragment();
        DetailsItemFragment.position = position;
        DetailsItemFragment.type = title;
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_details;
    }


    @Override
    public void clickedComic(int position, String type) {

    }


    @Override
    public void onViewReady() {
        loadDetailsComic();
    }

    private void loadDetailsComic() {

        if (type.equals(getString(R.string.FlagComicDetails)) || type.equals(getString(R.string.FlagComicDetailsAll))) {

            if (comic.getImages().size() != 0) {
                String image = comic.getImages().get(0).getPath() + "." + comic.getImages().get(0).getExtension();
                Glide.with(imageComic.getContext()).load(image).centerCrop().into(imageComic);
            }
            textTitleComic.setText(comic.getTitle());
            textBodyComic.setText(comic.getDescription());


        }if (type.equals(getString(R.string.FlagDataBase)) || type.equals(getString(R.string.FlagDataBaseAll))){

            final DataBaseTransaction dataBaseTransaction = new DataBaseTransaction();
            dataBaseTransaction.InitializeDatabase(getContext());

            RealmResults<FavoriteComicTable> result = dataBaseTransaction.getAll();
            FavoriteComicTable favourite = result.get(Integer.parseInt(position));
            textTitleComic.setText(favourite.getNameComic());
            Glide.with(imageComic.getContext()).load(favourite.getImageComic()).centerCrop().into(imageComic);
            textBodyComic.setText(favourite.getBodyComic());
        }
    }

    @Override
    public void updateFavourite(boolean update) {

    }
}

