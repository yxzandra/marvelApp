package com.nextdots.marvelapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.nextdots.marvelapp.API.Models.Result;
import com.nextdots.marvelapp.Fragments.ComicFragment;
import com.nextdots.marvelapp.Interface.OnResponseInterface;
import com.nextdots.marvelapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by yxzan on 11/02/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    abstract public int getLayout();
    abstract public void onCreateView(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        onCreateView(savedInstanceState);
    }

    public void putFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.comicContainer, fragment);
        ft.commit();

    }

    public void newActivity(Activity activity) {
        Intent intent = new Intent(this,activity.getClass());
        startActivity(intent);
        finish();
    }

    public void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){
            FragmentTransaction fragTansaction = manager.beginTransaction();
            fragTansaction.add(R.id.comicContainer, fragment, backStateName);
            fragTansaction.addToBackStack(backStateName);
            fragTansaction.commit();
        }
    }



}
