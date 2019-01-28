/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.pets.data.PetDBhelper;
import com.example.android.pets.data.PetsContract.PetsEntry;

import java.net.URI;
import java.util.List;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private PetDBhelper mDbHelper ;
    ListView petsList;
    PetsAdapter petsAdapter;
    private final int PETS_LOADER = 22;
    private static final String TAG = EditorActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);

            }
        });
        mDbHelper = new PetDBhelper(this);
        petsList = (ListView) findViewById(R.id.pets_list);
        petsAdapter = new PetsAdapter(this,null);
        petsList.setAdapter(petsAdapter);

        getLoaderManager().initLoader(PETS_LOADER,null,this);

        petsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Uri uri = Uri.parse(PetsEntry.CONTENT_URI+ "/" + id);
                Log.i(TAG, "onItemClick: uri" + uri + "parsed");
                Intent intent = new Intent(CatalogActivity.this,EditorActivity.class);
                intent.setData(uri);

                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    private void insertPetData(){
        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_PET_NAME,"Toto");
        values.put(PetsEntry.COLUMN_PET_BREED,"Terrier");
        values.put(PetsEntry.COLOUMN_PET_GENDER,PetsEntry.GENDER_MALE);
        values.put(PetsEntry.COLUMN_PET_WEIGHT,7);

        Uri uri = getContentResolver().insert(PetsEntry.CONTENT_URI,values);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertPetData();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] projection = {
                PetsEntry._ID,
                PetsEntry.COLUMN_PET_NAME,
                PetsEntry.COLUMN_PET_BREED,
                PetsEntry.COLOUMN_PET_GENDER,
                PetsEntry.COLUMN_PET_WEIGHT
        };

        return new CursorLoader(this,PetsEntry.CONTENT_URI,
                projection,
                null,
                null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        /*swap adapter cursor with {@link cursor} **/
        petsAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        petsAdapter.swapCursor(null);
    }
}
