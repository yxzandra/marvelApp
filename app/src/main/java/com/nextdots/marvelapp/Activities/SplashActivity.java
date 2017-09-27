package com.nextdots.marvelapp.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.transition.TransitionInflater;

import com.nextdots.marvelapp.R;
import com.splunk.mint.Mint;

public class SplashActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_spash;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            Mint.initAndStartSession(this.getApplication(), "35856482");
            initAplication();
        }
    }


    public void initAplication(){
        Handler myHandler = new Handler();
        myHandler.postDelayed(mMyRunnable, 1500);

    }

    private Runnable mMyRunnable = new Runnable(){
        @Override
        public void run(){
            newActivity(new MainActivity());
        }
    };

}