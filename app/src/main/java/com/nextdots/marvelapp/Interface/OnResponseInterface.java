package com.nextdots.marvelapp.Interface;

import com.nextdots.marvelapp.API.Models.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxzan on 17/02/2017.
 */
public interface OnResponseInterface {
    void onResponseMethod(ArrayList<Result> responseComic);

}
