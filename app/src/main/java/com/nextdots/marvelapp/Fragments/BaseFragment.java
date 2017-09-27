package com.nextdots.marvelapp.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextdots.marvelapp.Interface.OnClickRecyclerViewInterface;
import com.nextdots.marvelapp.Interface.OnFragmentInteractionListener;

import butterknife.ButterKnife;

/**
 * Created by yxzan on 11/02/2017.
 */

public abstract class BaseFragment extends Fragment implements OnClickRecyclerViewInterface {
    OnFragmentInteractionListener mListener;
    abstract public int getLayout();
    abstract public void clickedComic(int position, String type);
    abstract public void onViewReady();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        onViewReady();
        return view;
    }

    @Override
    public void onItemClicked(int position, String type) {
        if (mListener != null) {
            clickedComic(position, type);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
