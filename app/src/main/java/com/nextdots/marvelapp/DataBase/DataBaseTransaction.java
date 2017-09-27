package com.nextdots.marvelapp.DataBase;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yxzan on 09/02/2017.
 */

public class DataBaseTransaction {
    Realm myRealm;

    public DataBaseTransaction() {
    }

    public Realm InitializeDatabase(Context context){
        myRealm = Realm.getInstance(context);
        return myRealm;
    }

    public void addComic(String nameComic, String bodyComic, String imageComic){
        myRealm.beginTransaction();

        // Create an object
        FavoriteComicTable favoriteComicTable1 = myRealm.createObject(FavoriteComicTable.class);

        // Set its fields
        favoriteComicTable1.setNameComic(nameComic);
        favoriteComicTable1.setBodyComic(bodyComic);
        favoriteComicTable1.setImageComic(imageComic);

        myRealm.commitTransaction();
    }

    public RealmResults<FavoriteComicTable> getAll(){

        return myRealm.where(FavoriteComicTable.class).findAll();
    }

    public RealmResults<FavoriteComicTable> searchResult(String nameComic){
        return myRealm.where(FavoriteComicTable.class).equalTo("nameComic", nameComic).findAll();
    }

    public boolean searchComic (String nameComic){
        FavoriteComicTable result = myRealm.where(FavoriteComicTable.class).equalTo("nameComic", nameComic).findFirst();
        return result != null;
    }

    public FavoriteComicTable searchFirtsComic (String nameComic){
        FavoriteComicTable result = myRealm.where(FavoriteComicTable.class).equalTo("nameComic", nameComic).findFirst();
        return result;
    }

    public int sizeComic (){

        RealmResults<FavoriteComicTable> result = myRealm.where(FavoriteComicTable.class).findAll();
        return result.size();

    }

    public void deleteFavourite(String nameComic){

        myRealm.beginTransaction();
        RealmResults<FavoriteComicTable> result =  searchResult(nameComic);
        result.clear();
        myRealm.commitTransaction();

    }
}
