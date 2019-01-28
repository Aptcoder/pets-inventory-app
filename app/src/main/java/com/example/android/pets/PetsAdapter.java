package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetsContract.PetsEntry;

public class PetsAdapter extends CursorAdapter {
    public PetsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_pet,viewGroup,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        Get textview references from the view passed as argument to this method

        //textview for the petname
        TextView petName = (TextView) view.findViewById(R.id.pet_name);
        //textView fpr the pet breed
        TextView petBreed = (TextView) view.findViewById(R.id.pet_breed);

//        get string values from the cursor
        String name = cursor.getString(cursor.getColumnIndex(PetsEntry.COLUMN_PET_NAME));
        String breed = cursor.getString(cursor.getColumnIndex(PetsEntry.COLUMN_PET_BREED));

//        set text values to the textViews
        petBreed.setText(breed);
        petName.setText(name);
    }
}
