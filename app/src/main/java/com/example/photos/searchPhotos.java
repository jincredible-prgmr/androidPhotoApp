package com.example.photos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class searchPhotos extends AppCompatActivity {

    public static final String PHOTO_INDEX = "photoIndex";
    public static final String PHOTO_NAME = "photoName";
    public static final int fileRequest = 69;


    private EditText searchField;
    private EditText searchField2;
    private boolean personSearch;
    private boolean placeSearch;
    private boolean personSearch2;
    private boolean placeSearch2;
    private boolean andSearch;
    private boolean orSearch;
    CheckBox person1;
    CheckBox person2;
    CheckBox place1;
    CheckBox place2;
    CheckBox andBox;
    CheckBox orBox;
    public static Album searchResults = new Album("Search Results:");;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_photos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Photos");
        setSupportActionBar(toolbar);

        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the fields
        searchField = findViewById(R.id.search_field);
        searchField2 = findViewById(R.id.search_field2);

        searchResults = new Album("Search Results:");

        personSearch = false;
        placeSearch = false;
        personSearch2 = false;
        placeSearch2 = false;
        andSearch = false;
        orSearch = false;

        person1 = findViewById(R.id.checked_person);
        person2 = findViewById(R.id.checked_person2);
        place1 = findViewById(R.id.checked_place);
        place2 = findViewById(R.id.checked_place2);
        andBox = findViewById(R.id.checked_and);
        orBox = findViewById(R.id.checked_or);








    }



    public void search(View view){


        Tag newTag1;
        Tag newTag2 = null;
        String strTag;
        String strTag2 = null;
        String tagType;
        String tagType2 = null;

        Boolean twoTags = false;
        ArrayList<Photo> searchPhotos = new ArrayList<>();

        strTag = searchField.getText().toString();
        strTag = strTag.trim();
        if (strTag.matches("") ) {
            Toast.makeText(this, "Must enter Tag Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( (personSearch || placeSearch) == false ) {
            Toast.makeText(this, "Must check Tag Type", Toast.LENGTH_SHORT).show();
            return;
        }
        strTag2 = searchField2.getText().toString();
        strTag2 = strTag2.trim();
        if (! strTag2.matches("") ) {
            if ( (personSearch2 || placeSearch2) == false ) {
                Toast.makeText(this, "Must add second Tag Type", Toast.LENGTH_SHORT).show();
                return;
            }
            if ( (andSearch || orSearch) == false ) {
                Toast.makeText(this, "Must check And/Or", Toast.LENGTH_SHORT).show();
                return;
            }
            twoTags = true;
        }
        if( personSearch) tagType = "Person";
        else tagType = "Place";

        if( twoTags ){
            if( personSearch2 ) tagType2 = "Person";
            else tagType2 = "Place";

        }

        newTag1 = new Tag(tagType, strTag);
        if( twoTags ){
            newTag2 = new Tag(tagType2, strTag2);

        }

        boolean containsTag1;
        boolean containsTag2;


        for( Album a : Photos.albumsArrList) {

            for( Photo p : a.photos ) {

                containsTag1 = false;
                containsTag2 = false;

                for( Tag t : p.tag) {
                    //if searching with a single tag
                    if( !twoTags ) {
                        if ( t.compareTag(newTag1) ) {
                            searchPhotos.add(p);
                        }
                    }
                    //if 2 tags, check if they exist in tags
                    else {
                        if(t.compareTag(newTag1)) containsTag1 = true;
                        if(t.compareTag(newTag2)) containsTag2 = true;
                    }
                }

                if( containsTag1 && containsTag2 && andSearch) searchPhotos.add(p);
                else if( (containsTag1 || containsTag2) && orSearch) searchPhotos.add(p);
            }

        }

        searchResults.photos = searchPhotos;
        Intent intent = new Intent(this, SearchResults.class);
        startActivityForResult(intent, 420);

    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        searchField.setText("");
        searchField2.setText("");
        personSearch = false;
        placeSearch = false;
        personSearch2 = false;
        placeSearch2 = false;
        andSearch = false;
        orSearch = false;
        person1.setChecked(false);
        person2.setChecked(false);
        place1.setChecked(false);
        place2.setChecked(false);
        andBox.setChecked(false);
        orBox.setChecked(false);

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checked_person:
                if (checked){
                    personSearch = true;
                    placeSearch = false;
                    place1.setChecked(false);
                }
            else{
                    personSearch = false;
                }
                break;
            case R.id.checked_place:
                if (checked){
                    placeSearch = true;
                    personSearch = false;
                    person1.setChecked(false);

                }
                else{
                    placeSearch = false;
                }
                break;
            case R.id.checked_person2:
                if (checked){
                    personSearch2 = true;
                    placeSearch2 = false;
                    place2.setChecked(false);

                }
                else{
                    personSearch2 = false;
                }
                break;
            case R.id.checked_place2:
                if (checked){
                    placeSearch2 = true;
                    personSearch2 = false;
                    person2.setChecked(false);

                }
                else{
                    placeSearch2 = false;
                }
                break;
            case R.id.checked_and:
                if (checked){
                    andSearch = true;
                    orSearch = false;
                    orBox.setChecked(false);

                }
                else{
                    andSearch = false;
                }
                break;
            case R.id.checked_or:
                if (checked){
                    orSearch = true;
                    andSearch = false;
                    andBox.setChecked(false);
                }
                else{
                    orSearch = false;
                }
                break;
        }
    }

}
