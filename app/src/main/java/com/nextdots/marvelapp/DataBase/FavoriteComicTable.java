package com.nextdots.marvelapp.DataBase;

import io.realm.RealmObject;

/**
 * Created by yxzan on 09/02/2017.
 */

public class FavoriteComicTable extends RealmObject {

    private String nameComic,bodyComic, imageComic;

    public FavoriteComicTable() { }

    public String getNameComic() {
        return nameComic;
    }

    public void setNameComic(String name) {
        this.nameComic = name;
    }

    public String getBodyComic() {
        return bodyComic;
    }

    public void setBodyComic(String bodyComic) {
        this.bodyComic = bodyComic;
    }

    public String getImageComic() {
        return imageComic;
    }

    public void setImageComic(String imageComic) {
        this.imageComic = imageComic;
    }
}