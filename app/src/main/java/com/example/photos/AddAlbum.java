package com.example.photos;


import com.example.photos.Photos;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AddAlbum extends AppCompatActivity {

    public static final String ALBUM_INDEX = "albumIndex";
    public static final String ALBUM_NAME = "albumName";


    private int albumIndex;
    private EditText albumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_album);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setTitle("Add Album");


        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the fields
        albumName = findViewById(R.id.album_name);


        // see if info was passed in to populate fields
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            albumIndex = bundle.getInt(ALBUM_INDEX);
            albumName.setText(bundle.getString(ALBUM_NAME));

        }

    }

    public void save(View view) {
        // gather all data from text fields
        String name = albumName.getText().toString();


        // pop up dialog if errors in input, and return
        // name and year are mandatory
        if (name == null || name.length() == 0 ) {
            Bundle bundle = new Bundle();
            bundle.putString(PhotoFragment.MESSAGE_KEY,
                    "Name is required");
            DialogFragment newFragment = new PhotoFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return; // does not quit activity, just returns from method
        }
        for(int i=0;i<Photos.albumsArrList.size();i++){
            if(Photos.albumsArrList.get(i).name.equals(name)){
                Bundle bundle = new Bundle();
                bundle.putString(PhotoFragment.MESSAGE_KEY,
                        "Name cannot be duplicate");
                DialogFragment newFragment = new PhotoFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "badfields");
                return; // does not quit activity, just returns from method



            }



        }




        Album newalbum=new Album(name);
        Photos.albumsArrList.add(newalbum);
        try{

            FileOutputStream fos= new FileOutputStream(Photos.data);
            Log.d("test","attempting to write");
            ObjectOutputStream os=new ObjectOutputStream(fos);
            os.writeObject(Photos.albumsArrList);
            Log.d("test","written");
            os.close();

        }catch (IOException e){

        }

        finish(); // pops activity from the call stack, returns to parent
    }
}
