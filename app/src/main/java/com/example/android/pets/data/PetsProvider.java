package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * {@link ContentProvider for pets app}
 */
public class PetsProvider extends ContentProvider {
    //    Database helper object
    private PetDBhelper mDbhelper;
    static final int PET = 100;
    static final int PET_ID = 101;

    static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //        add uri for the getting data of all the rows in the database and returns the code (101)
        uriMatcher.addURI("com.example.android.pets",PetsContract.PetsEntry.TABLE_NAME,PET);
        //        add uri for the getting data of all the rows in the database and returns the code (101)
        uriMatcher.addURI("com.example.android.pets",PetsContract.PetsEntry.TABLE_NAME + "/#",PET_ID);

    }

    @Override
    public boolean onCreate() {

        mDbhelper = new PetDBhelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase db = mDbhelper.getWritableDatabase();
        int match  = uriMatcher.match(uri);
        Cursor cursor;
        switch(match){
            case PET :
               cursor  = db.query(PetsContract.PetsEntry.TABLE_NAME,
                        strings,null
                ,null
                ,null
                ,null
                ,null
                ,null);
               break;
            case PET_ID :
                String selection = PetsContract.PetsEntry._ID + "=?";
                String[] selectionArgs = {String.valueOf(ContentUris.parseId(uri))};
                cursor  = db.query(PetsContract.PetsEntry.TABLE_NAME,
                        strings,selection
                        ,selectionArgs
                        ,null
                        ,null
                        ,null
                        ,null);
                break;
                default:
                    throw new IllegalArgumentException("can not read uri : "+ uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        int match = uriMatcher.match(uri);

        switch(match){
            case PET:
                return insertUri(uri,contentValues);
            default:
                throw new IllegalArgumentException("can not read uri : "+ uri);
        }

    }

    private Uri insertUri(Uri uri,ContentValues values){
        SQLiteDatabase db = mDbhelper.getWritableDatabase();
        long ID = db.insert(PetsContract.PetsEntry.TABLE_NAME,null,values);
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,ID);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] strings) {
        SQLiteDatabase db = mDbhelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowCount;
        switch (match) {
            case PET:
                rowCount = db.delete(PetsContract.PetsEntry.TABLE_NAME, selection, strings);
                getContext().getContentResolver().notifyChange(uri,null);
                return rowCount;
            case PET_ID:
                String selectionUsed = PetsContract.PetsEntry._ID + "=?";
                String[] selectionArgs = {String.valueOf(ContentUris.parseId(uri))};
                rowCount = db.delete(PetsContract.PetsEntry.TABLE_NAME, selectionUsed, selectionArgs);
                getContext().getContentResolver().notifyChange(uri,null);
                return rowCount;
            default:
                throw new IllegalArgumentException("can not find/delete uri: " + uri);


        }

    }

        @Override
        public int update (@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String
        s, @Nullable String[]strings){
        SQLiteDatabase db = mDbhelper.getWritableDatabase();
            int match = uriMatcher.match(uri);
            int rowCount;
            switch (match) {
                case PET:
                    rowCount = db.update(PetsContract.PetsEntry.TABLE_NAME,contentValues,s,strings);
                    getContext().getContentResolver().notifyChange(uri,null);
                    return rowCount;
                case PET_ID:
                    String selectionUsed = PetsContract.PetsEntry._ID + "=?";
                    String[] selectionArgs = {String.valueOf(ContentUris.parseId(uri))};
                    rowCount = db.update(PetsContract.PetsEntry.TABLE_NAME,contentValues, selectionUsed, selectionArgs);
                    getContext().getContentResolver().notifyChange(uri,null);
                    return rowCount;
                default:
                    throw new IllegalArgumentException("can not find/delete uri: " + uri);


            }

        }
    }
