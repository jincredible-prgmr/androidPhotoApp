package com.example.photos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AddEditPhoto extends AppCompatActivity {


    public static final String PHOTO_INDEX = "photoIndex";
    public static final String PHOTO_NAME = "photoName";
    public static final int fileRequest = 69;

    private int photoIndex;
    private EditText photoName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_photo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Photo");
        setSupportActionBar(toolbar);

        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the fields
        photoName = findViewById(R.id.photo_name);




    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if( requestCode == fileRequest ) {

            if(data != null) {

                Uri uri = data.getData();

                //Toast.makeText( this, "Uri :"+uri, Toast.LENGTH_LONG).show();
                //Toast.makeText( this, "Path :"+uri.getPath(), Toast.LENGTH_LONG).show();


                photoName.setText(uri.toString());


            }

        }

    }



    public void chooseFile(View view){

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType( "*/*"  );
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult( intent, fileRequest);

    }



    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void add(View view) {
        // gather all data from text fields
        String name = photoName.getText().toString();


        // pop up dialog if errors in input, and return
        // name and year are mandatory
        if (name == null || name.length() == 0 ) {
            Bundle bundle = new Bundle();
            bundle.putString(PhotoFragment.MESSAGE_KEY,
                    "Path is required");
            DialogFragment newFragment = new PhotoFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return; // does not quit activity, just returns from method
        }


        Photo p=new Photo(name);

        Photos.selected.photos.add(p);
        ((photoAdapter)albumView.photolist.getAdapter()).notifyDataSetChanged();

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


