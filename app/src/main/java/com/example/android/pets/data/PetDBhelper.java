package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.pets.data.PetsContract.PetsEntry;


public class PetDBhelper extends SQLiteOpenHelper {

    public static final String name = "pets.db";
    public static final  int  version = 1;
    public PetDBhelper(Context context) {
        super(context, name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCommnads = "CREATE TABLE " + PetsEntry.TABLE_NAME + " ( " + PetsEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT"
                +","+ PetsEntry.COLUMN_PET_NAME + " TEXT " + "," + PetsEntry.COLUMN_PET_BREED + " TEXT "
                +","+ PetsEntry.COLOUMN_PET_GENDER + " INTEGER NOT NULL DEFAULT 0 " + ","
                +PetsEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0 " + " ); ";

        sqLiteDatabase.execSQL(sqlCommnads);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
