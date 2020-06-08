package com.example.photos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class albumView extends AppCompatActivity {

    public static final String ALBUM_INDEX = "albumIndex";
    public static final String ALBUM_NAME = "albumName";
    public static final String PHOTO_LIST = "photoList";
    public static final int PHOTOVIEW = 10;

    private ArrayList<Photo> photoList;
    private int albumIndex;
    public static ListView photolist;
    private String albumName;

    public static Photo selectedphoto=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_view);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            albumIndex = bundle.getInt(ALBUM_INDEX);
            photoList = bundle.getParcelableArrayList(PHOTO_LIST);
            toolbar.setTitle( bundle.getString(ALBUM_NAME) );

        }

        photolist = findViewById(R.id.Album);
        photolist.setAdapter(
                new photoAdapter(this, R.layout.customview,Photos.selected.photos));

        // show movie for possible edit when tapped
        //listView.setOnItemClickListener((p, V, pos, id) ->
                //showAlbum(pos));
        photolist.setOnItemClickListener((p, V, pos, id) ->
                showPhoto(pos));

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addPhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showPhoto(int pos) {
        selectedphoto=Photos.selected.photos.get(pos);
        Intent intent = new Intent(this, photoView.class);
        //  intent.putExtras(bundle);
        startActivityForResult(intent, PHOTOVIEW);
    }

    private void addPhoto() {
        Intent intent = new Intent(this, AddEditPhoto.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }



        // redo the adapter to reflect change^K
        photolist.setAdapter(
                new photoAdapter(this, R.layout.customview,Photos.selected.photos));

    }

}

