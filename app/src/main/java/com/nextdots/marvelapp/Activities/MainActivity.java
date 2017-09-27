package com.nextdots.marvelapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.nextdots.marvelapp.API.Models.Result;
import com.nextdots.marvelapp.Fragments.AllComicFragment;
import com.nextdots.marvelapp.Fragments.ComicFragment;
import com.nextdots.marvelapp.Fragments.DetailsItemFragment;
import com.nextdots.marvelapp.Interface.OnFragmentInteractionListener;
import com.nextdots.marvelapp.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener{
    String nameFragmentMain;
    @BindView(R.id.comicContainer)
    FrameLayout contenedorComic;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            putFragment(new ComicFragment());
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        nameFragmentMain = onBackOption(nameFragmentMain);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public String onBackOption(String nameFragmentMain) {

        if (nameFragmentMain != null) {
            if (nameFragmentMain.equals(getString(R.string.FlagAllComic))
                    || nameFragmentMain.equals(getString(R.string.FlagComicDetails))
                    || nameFragmentMain.equals(getString(R.string.FlagDataBase))
                    || nameFragmentMain.equals(getString(R.string.FlagAllComicDataBase))) {

                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setTitle(R.string.app_name);
                Log.e("onBackOption", "aqui");
                getSupportFragmentManager().getFragments().get(0).onResume();
                }


            }
            if (nameFragmentMain.equals(getString(R.string.FlagComicDetailsAll))
                    || nameFragmentMain.equals(getString(R.string.FlagDataBaseAll))) {

                getSupportActionBar().setTitle(R.string.ToolbarTitleAllComic);
                nameFragmentMain = getString(R.string.FlagAllComic);

        }
        invalidateOptionsMenu();
        return nameFragmentMain;
    }

    @Override
    public void onFragmentInteraction(String nameFragment, Result itemClick, String tag) {

        if (nameFragment.equals(getString(R.string.FlagComicDetails))
                || nameFragment.equals(getString(R.string.FlagComicDetailsAll))) {

            getSupportActionBar().setTitle(R.string.ToolbarTitleComicDetails);
            replaceFragment(DetailsItemFragment.newInstance(itemClick, nameFragment));

        }if (nameFragment.equals(getString(R.string.FlagDataBase))
                || nameFragment.equals(getString(R.string.FlagDataBaseAll))) {

            getSupportActionBar().setTitle(R.string.ToolbarTitleComicDetails);
            replaceFragment(DetailsItemFragment.newInstance(tag, nameFragment));

        }if (nameFragment.equals(getString(R.string.FlagAllComic))){
            getSupportActionBar().setTitle(R.string.ToolbarTitleAllComic);
            replaceFragment(AllComicFragment.newInstance(false));

        }if (nameFragment.equals(getString(R.string.FlagAllComicDataBase))){
            getSupportActionBar().setTitle(R.string.ToolbarTitleAllFavoriteComic);
            replaceFragment(AllComicFragment.newInstance(true));

        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateOptionsMenu();
        nameFragmentMain = nameFragment;

    }

}
