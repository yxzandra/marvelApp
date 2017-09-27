package com.nextdots.marvelapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.nextdots.marvelapp.API.Models.Result;
import com.nextdots.marvelapp.API.Retrofit.RetrofitMagnament;
import com.nextdots.marvelapp.Activities.MainActivity;
import com.nextdots.marvelapp.Adapters.ComicFavouriteAdapter;
import com.nextdots.marvelapp.Adapters.ComicListAdapter;
import com.nextdots.marvelapp.DataBase.DataBaseTransaction;
import com.nextdots.marvelapp.Interface.OnClickRecyclerViewInterface;
import com.nextdots.marvelapp.Interface.OnResponseInterface;
import com.nextdots.marvelapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ComicFragment extends BaseFragment implements OnClickRecyclerViewInterface, OnResponseInterface {

    public ArrayList<Result> comicList;
    DataBaseTransaction dataBaseTransaction;
    RetrofitMagnament controller;
    public static final String TAG = ComicFragment.class.getSimpleName();

    @BindView(R.id.recyclerComic)
    RecyclerView recyclerView;
    @BindView(R.id.genericContainer)
    CoordinatorLayout genericContainer;
    @BindView(R.id.textLoading)
    TextView textLoading;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.genericLoading)
    LinearLayout genericLoading;
    @BindView(R.id.recyclerFavourite)
    RecyclerView recyclerFavourite;
    @BindView(R.id.btnReadMore)
    Button btnReadMore;
    @BindView(R.id.spinnerCantComic)
    Spinner spinnerCantComic;
    @BindView(R.id.btnShowMore)
    Button btnShowMore;
    @BindView(R.id.faouriteHeader)
    TextView faouriteHeader;

    @Override
    public int getLayout() {
        return R.layout.fragment_comic;
    }


    @Override
    public void onViewReady() {
        dataBaseTransaction = new DataBaseTransaction();
        dataBaseTransaction.InitializeDatabase(getContext());
        controller = new RetrofitMagnament(this);

        if (isOnline()) {
            genericContainer.setVisibility(View.INVISIBLE);
            controller.start(getContext(),Integer.parseInt(getResources().getStringArray(R.array.array_comic_amount)[1]));
            loadSpinner();
        } else {
            recyclerView.setVisibility(View.GONE);
            genericLoading.setVisibility(View.GONE);
            spinnerCantComic.setVisibility(View.GONE);
            btnShowMore.setVisibility(View.GONE);
        }

        loadRecyclerFavourite();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (comicList != null) {
            loadRecycler();
            loadRecyclerFavourite();
        }
    }

    private void loadSpinner() {
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(getContext(), R.array.array_comic_amount, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCantComic.setAdapter(spinner_adapter);
        spinnerCantComic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    genericContainer.setVisibility(View.INVISIBLE);
                    controller.start(getContext(),
                            Integer.parseInt(getResources().getStringArray(R.array.array_comic_amount)[position]));
                    genericLoading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void loadRecycler() {
        recyclerFavourite.removeAllViews();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ComicListAdapter(comicList, this, getContext()));
    }

    public void loadRecyclerFavourite() {
        recyclerFavourite.removeAllViews();
        recyclerFavourite.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerFavourite.setLayoutManager(linearLayoutManager);
        recyclerFavourite.setAdapter(new ComicFavouriteAdapter(this, getContext()));

        if (dataBaseTransaction.sizeComic() == 0) {
            faouriteHeader.setText(R.string.notFavoritesHeader);
            btnReadMore.setVisibility(View.GONE);
        }else {
            faouriteHeader.setText(R.string.favoriteHeader);
            btnReadMore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void clickedComic(int position, String type) {

        if (type.equals(getString(R.string.FlagTypeComicDetailsDataBase))) {
            mListener.onFragmentInteraction(type, null, String.valueOf(position));
        } else {
            if (isOnline()) {
                mListener.onFragmentInteraction(getString(R.string.FlagComicDetails), comicList.get(position), TAG);
            }else {
                mListener.onFragmentInteraction(getString(R.string.FlagTypeComicDetailsDataBase), null, String.valueOf(position));
            }
        }
    }

    @Override
    public void onItemClicked(int position, String type) {
        if (mListener != null) {

            if (type.equals(getString(R.string.FlagTypeComicDetailsDataBase))) {
                mListener.onFragmentInteraction(type, null, String.valueOf(position));
            } else {
                if (isOnline()) {
                    mListener.onFragmentInteraction(getString(R.string.FlagComicDetails), comicList.get(position), TAG);
                }else {
                    mListener.onFragmentInteraction(getString(R.string.FlagTypeComicDetailsDataBase), null, String.valueOf(position));
                }
            }
        }
    }

    @Override
    public void updateFavourite(boolean update) {
        loadRecyclerFavourite();
    }
    



    @OnClick({R.id.btnShowMore, R.id.btnReadMore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShowMore:
                mListener.onFragmentInteraction(getString(R.string.FlagAllComic), null, TAG);
                break;
            case R.id.btnReadMore:
                mListener.onFragmentInteraction(getString(R.string.FlagAllComicDataBase), null, TAG);
                break;
        }
    }


    @Override
    public void onResponseMethod(ArrayList<Result> responseComic) {
        comicList = responseComic;
        loadRecycler();
        genericContainer.setVisibility(View.VISIBLE);
        genericLoading.setVisibility(View.GONE);
    }
}

