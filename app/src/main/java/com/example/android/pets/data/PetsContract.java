package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class PetsContract {

    private PetsContract(){

    }
    public static final class PetsEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.parse("content://com.example.android.pets/pets");
//        constants for the table
        public static final String TABLE_NAME = "pets";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_WEIGHT = "weight";
        public static final String COLOUMN_PET_GENDER ="gender";

//                constants for gender (1) for male ,(2) for female and (0) for
//        unknown
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
