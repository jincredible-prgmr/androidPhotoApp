package com.example.photos;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class photoView extends AppCompatActivity {

    public static final String PHOTO_INDEX = "photoIndex";
    public static final String PHOTO_NAME = "photoName";
    public static final String PHOTO_PATH = "photoPath";
    public static final String TAG_LIST = "tagList";
    public static final int EDIT_REQUEST = 0;
    public static final int DELETE_REQUEST = 1;
    public static final int ADDTAG_REQUEST = 2;
    public static final int DELTAG_REQUEST = 3;


    private ImageView photoDisplay;
    //private EditText moveToAlbum;
    private EditText tagField;
    private ListView tagList;
    private CheckBox checkedPerson;
    private CheckBox checkedPlace;
    private int photoIndex;
    public ArrayList<Tag> tagArrList;
    private String path;
    private File file = null;
    private boolean personTag = false;
    private boolean placeTag = false;
    private Tag delTag=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        // activates tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tagArrList = albumView.selectedphoto.tag;
        checkedPerson = findViewById(R.id.checked_person);
        checkedPlace = findViewById(R.id.checked_place);

        tagField = findViewById(R.id.add_tag_field);
        tagList =  findViewById(R.id.tags_list);

        tagList.setAdapter(
                new ArrayAdapter<Tag>(this, R.layout.albums, tagArrList));
        tagList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        tagList.setOnItemClickListener((p, V, pos, id) ->
                selectTag(pos));



        //
        photoDisplay = findViewById(R.id.photoDisplay);
        TextView nameField = findViewById(R.id.name);
        //tagField = findViewById(R.id.add_tag_field);
        //tagList = findViewById(R.id.tags_list);




        Uri test=Uri.parse(albumView.selectedphoto.path);
        File file=new File(test.getPath());



        if(!(albumView.selectedphoto.path.equals("stock1")|| albumView.selectedphoto.path.equals("stock2") ||albumView.selectedphoto.path.equals("stock3")||albumView.selectedphoto.path.equals("stock4")||albumView.selectedphoto.path.equals("stock5"))){
            photoDisplay.setImageURI(test);

            nameField.setText(file.getName());
        }else
        if( albumView.selectedphoto.path.equals("stock1")) {
            photoDisplay.setImageResource(R.drawable.stock1);
            nameField.setText("stock1");

        }else
        if( albumView.selectedphoto.path.equals("stock2")) {
            photoDisplay.setImageResource(R.drawable.stock2);
            nameField.setText("stock2");

        }else
        if( albumView.selectedphoto.path.equals("stock3")) {
            photoDisplay.setImageResource(R.drawable.stock3);
            nameField.setText("stock3");

        }else
        if( albumView.selectedphoto.path.equals("stock4")) {
            photoDisplay.setImageResource(R.drawable.stock4);
            nameField.setText("stock4");

        }else
        if( albumView.selectedphoto.path.equals("stock5")) {
            photoDisplay.setImageResource(R.drawable.stock5);
            nameField.setText("stock5");

        }



    }
    public void selectTag(int pos){

        delTag = tagArrList.get(pos);
        Toast.makeText( this, "selected: "+ tagArrList.get(pos).toString(), Toast.LENGTH_SHORT).show();
    }
    public void addTag(View view){

        Photo photo = albumView.selectedphoto;
        String tagType = null;
        String tagValue;

        tagValue = tagField.getText().toString();
        tagValue = tagValue.trim();
        if (tagValue.matches("") ) {
            Toast.makeText(this, "Must enter Tag Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( (personTag || placeTag) == false ) {
            Toast.makeText(this, "Must check Tag Type", Toast.LENGTH_SHORT).show();
            return;
        }

        if( personTag) tagType = "Person";
        else tagType = "Place";

        tagArrList.add(new Tag( tagType, tagValue));
        ((BaseAdapter)tagList.getAdapter()).notifyDataSetChanged();


        try{

            FileOutputStream fos= new FileOutputStream(Photos.data);
            Log.d("test","attempting to write");
            ObjectOutputStream os=new ObjectOutputStream(fos);
            os.writeObject(Photos.albumsArrList);
            Log.d("test","written");
            os.close();

        }catch (IOException e){

        }








    }



    public void moveRight(View view){



        int i=Photos.selected.photos.indexOf(albumView.selectedphoto);
        i=i+1;
        if(i>=Photos.selected.photos.size()){
            i=0;
        }
        albumView.selectedphoto=Photos.selected.photos.get(i);
        tagArrList=albumView.selectedphoto.tag;
        tagList.setAdapter(
                new ArrayAdapter<Tag>(this, R.layout.albums, tagArrList));
        tagList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);



        photoDisplay = findViewById(R.id.photoDisplay);
        TextView nameField = findViewById(R.id.name);
        //tagField = findViewById(R.id.add_tag_field);
        //tagList = findViewById(R.id.tags_list);




        Uri test=Uri.parse(albumView.selectedphoto.path);
        File file=new File(test.getPath());



        if(!(albumView.selectedphoto.path.equals("stock1")|| albumView.selectedphoto.path.equals("stock2") ||albumView.selectedphoto.path.equals("stock3")||albumView.selectedphoto.path.equals("stock4")||albumView.selectedphoto.path.equals("stock5"))){
            photoDisplay.setImageURI(test);

            nameField.setText(file.getName());
        }else
        if( albumView.selectedphoto.path.equals("stock1")) {
            photoDisplay.setImageResource(R.drawable.stock1);
            nameField.setText("stock1");

        }else
        if( albumView.selectedphoto.path.equals("stock2")) {
            photoDisplay.setImageResource(R.drawable.stock2);
            nameField.setText("stock2");

        }else
        if( albumView.selectedphoto.path.equals("stock3")) {
            photoDisplay.setImageResource(R.drawable.stock3);
            nameField.setText("stock3");

        }else
        if( albumView.selectedphoto.path.equals("stock4")) {
            photoDisplay.setImageResource(R.drawable.stock4);
            nameField.setText("stock4");

        }else
        if( albumView.selectedphoto.path.equals("stock5")) {
            photoDisplay.setImageResource(R.drawable.stock5);
            nameField.setText("stock5");

        }



    }
    public void moveLeft(View view){

        int i=Photos.selected.photos.indexOf(albumView.selectedphoto);
        i=i-1;
        if(i<0){
            i=Photos.selected.photos.size()-1;
        }
        if(i<0){
            i=0;
        }

        albumView.selectedphoto=Photos.selected.photos.get(i);
        tagArrList=albumView.selectedphoto.tag;
        tagList.setAdapter(
                new ArrayAdapter<Tag>(this, R.layout.albums, tagArrList));
        tagList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);



        photoDisplay = findViewById(R.id.photoDisplay);
        TextView nameField = findViewById(R.id.name);
        //tagField = findViewById(R.id.add_tag_field);
        //tagList = findViewById(R.id.tags_list);




        Uri test=Uri.parse(albumView.selectedphoto.path);
        File file=new File(test.getPath());



        if(!(albumView.selectedphoto.path.equals("stock1")|| albumView.selectedphoto.path.equals("stock2") ||albumView.selectedphoto.path.equals("stock3")||albumView.selectedphoto.path.equals("stock4")||albumView.selectedphoto.path.equals("stock5"))){
            photoDisplay.setImageURI(test);

            nameField.setText(file.getName());
        }else
        if( albumView.selectedphoto.path.equals("stock1")) {
            photoDisplay.setImageResource(R.drawable.stock1);
            nameField.setText("stock1");

        }else
        if( albumView.selectedphoto.path.equals("stock2")) {
            photoDisplay.setImageResource(R.drawable.stock2);
            nameField.setText("stock2");

        }else
        if( albumView.selectedphoto.path.equals("stock3")) {
            photoDisplay.setImageResource(R.drawable.stock3);
            nameField.setText("stock3");

        }else
        if( albumView.selectedphoto.path.equals("stock4")) {
            photoDisplay.setImageResource(R.drawable.stock4);
            nameField.setText("stock4");

        }else
        if( albumView.selectedphoto.path.equals("stock5")) {
            photoDisplay.setImageResource(R.drawable.stock5);
            nameField.setText("stock5");

        }



    }
    public void delete(View view) {
        if(delTag==null){
            return;
        }



        tagArrList.remove(delTag);

        delTag=null;


        ((ArrayAdapter)tagList.getAdapter()).notifyDataSetChanged();
        try {

            FileOutputStream fos = new FileOutputStream(Photos.data);
            Log.d("test", "attempting to write");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(Photos.albumsArrList);
            Log.d("test", "written");
            os.close();

        } catch (IOException e) {

        }

        return;
    }
    public void deletePhoto(View view) {




        Photos.selected.photos.remove(albumView.selectedphoto);




        ((photoAdapter)albumView.photolist.getAdapter()).notifyDataSetChanged();
        try {

            FileOutputStream fos = new FileOutputStream(Photos.data);
            Log.d("test", "attempting to write");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(Photos.albumsArrList);
            Log.d("test", "written");
            os.close();

        } catch (IOException e) {

        }

        finish();
    }



    public void move(View view){

        EditText moveToAlbum=findViewById(R.id.moveToAlbum);
        String find=moveToAlbum.getText().toString();

        int j=-1;
        for(int i=0;i<Photos.albumsArrList.size();i++){
            if(Photos.albumsArrList.get(i).name.equals(find)){
                j=i;
                break;
            }

        }
        if(j<0){

                Bundle bundle = new Bundle();
                bundle.putString(PhotoFragment.MESSAGE_KEY,
                        "Album not found");
                DialogFragment newFragment = new PhotoFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "badfields");
                return; // does not quit activity, just returns from method


        }
        Photos.albumsArrList.get(j).photos.add(albumView.selectedphoto);
        Photos.selected.photos.remove(albumView.selectedphoto);


        ((photoAdapter)albumView.photolist.getAdapter()).notifyDataSetChanged();
        try {

            FileOutputStream fos = new FileOutputStream(Photos.data);
            Log.d("test", "attempting to write");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(Photos.albumsArrList);
            Log.d("test", "written");
            os.close();

        } catch (IOException e) {

        }

        finish();




    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checked_person:
                if (checked) {
                    personTag = true;
                    placeTag = false;
                    checkedPlace.setChecked(false);
                } else {
                    personTag = false;
                }
                break;
            case R.id.checked_place:
                if (checked) {
                    placeTag = true;
                    personTag = false;
                    checkedPerson.setChecked(false);

                } else {
                    placeTag = false;
                }
                break;
        }
    }


}
