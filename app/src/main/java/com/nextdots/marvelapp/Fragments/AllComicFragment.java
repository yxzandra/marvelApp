package com.nextdots.marvelapp.Fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nextdots.marvelapp.API.Models.Result;
import com.nextdots.marvelapp.API.Retrofit.RetrofitMagnament;
import com.nextdots.marvelapp.Adapters.ComicAllAdapter;
import com.nextdots.marvelapp.Interface.OnResponseInterface;
import com.nextdots.marvelapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

public class AllComicFragment extends BaseFragment implements OnResponseInterface {
    public List<Result> comicList;
    static boolean flagDatabase = false;
    RetrofitMagnament controller;

    @BindView(R.id.recyclerComicAll)
    RecyclerView recyclerComicAll;
    @BindView(R.id.textLoading)
    TextView textLoading;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.genericLoading)
    LinearLayout genericLoading;

    public AllComicFragment() {
    }

    public static AllComicFragment newInstance(boolean database) {

        AllComicFragment fragment = new AllComicFragment();
        AllComicFragment.flagDatabase = database;
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_all_comic;
    }


    @Override
    public void onViewReady() {
        if (isOnline() && !flagDatabase) {
            controller = new RetrofitMagnament(this);
            controller.start(getContext(),100);
        }else {
            loadRecycler();
            genericLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void clickedComic(int position, String type) {
        if (type.equals(getString(R.string.FlagDataBaseAll))) {
            mListener.onFragmentInteraction(type, null, String.valueOf(position));
        }else {
            if (flagDatabase) {
                mListener.onFragmentInteraction(getString(R.string.FlagDataBaseAll), null, String.valueOf(position));
            } else {
                if (isOnline()) {
                    mListener.onFragmentInteraction(getString(R.string.FlagComicDetailsAll), comicList.get(position), TAG);
                } else {
                    mListener.onFragmentInteraction(getString(R.string.FlagDataBaseAll), null, String.valueOf(position));
                }
            }
        }

    }



    public void loadRecycler() {
        recyclerComicAll.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerComicAll.setLayoutManager(gridLayoutManager);
        recyclerComicAll.setAdapter(new ComicAllAdapter(comicList, this, getContext()));
    }

    @Override
    public void onResponseMethod(ArrayList<Result> responseComic) {
        comicList = responseComic;
        loadRecycler();
        recyclerComicAll.setVisibility(View.VISIBLE);
        genericLoading.setVisibility(View.GONE);
    }

    @Override
    public void updateFavourite(boolean update) {

    }


}