package com.example.photos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class Album implements Serializable , Parcelable {
    String name;
    ArrayList<Photo> photos;
    Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();

    }

    protected Album(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String toString() {   // used by ListView
        return name;
    }
    public String getString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}

public class Photos extends AppCompatActivity {
    public static File data;
    public static ListView listView;
    public static ArrayList<Album> albumsArrList = new ArrayList<>();
    public static Album selected=null;
    public static final int EDIT_ALBUM_CODE = 1;
    public static final int ADD_ALBUM_CODE = 2;
    public static final int SEARCH_PHOTO_CODE = 3;

    public static final int ALBUMVIEW = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_list);

        //read the userlist file
        //if the app runs for the first time errors are ignored.
        try {
            FileInputStream r=new FileInputStream(Photos.this.getFilesDir()+"/data.dat");
            data=new File(Photos.this.getFilesDir()+"/data.dat");
            ObjectInputStream ro=new ObjectInputStream(r);
            albumsArrList=(ArrayList<Album>)ro.readObject();
            ro.close();
            r.close();

        }catch (IOException i) {


            File newdatafile=new File(Photos.this.getFilesDir(),"data.dat");
            data=newdatafile;
            Album stock=new Album("stock");
            albumsArrList.add(stock);
            Photo p1=new Photo("stock1");
            Photo p2=new Photo("stock2");
            Photo p3=new Photo("stock3");
            Photo p4=new Photo("stock4");
            Photo p5=new Photo("stock5");
            albumsArrList.get(0).photos.add(p1);
            albumsArrList.get(0).photos.add(p2);
            albumsArrList.get(0).photos.add(p3);
            albumsArrList.get(0).photos.add(p4);
            albumsArrList.get(0).photos.add(p5);
            try{

                FileOutputStream fos= new FileOutputStream(newdatafile);
                Log.d("test","attempting to write");
                ObjectOutputStream os=new ObjectOutputStream(fos);
                os.writeObject(Photos.albumsArrList);
                Log.d("test","written");
                os.close();

            }catch (IOException e){

            }


            //i.printStackTrace();
        }
        catch(ClassNotFoundException c) {
            //c.printStackTrace();
        }
        //if the userlist file is empty then no admin or stock users exist. Create them and serialize them
      /* if(albumsArrList.isEmpty()) {

            for(int i = 0 ; i < 11; i++) {
                Album temp = new Album("Album " + i );
                temp.photos.add(new Photo("/butts", "Photo 1"));
                temp.photos.add(new Photo("/butts", "Photo 2"));
                temp.photos.add(new Photo("/butts", "Photo 3"));
                temp.photos.add(new Photo("/butts", "Photo 4"));
                temp.photos.add(new Photo("/butts", "Photo 5"));
                temp.photos.add(new Photo("/butts", "Photo 6"));
                albumsArrList.add(temp);
                //System.out.println("problem");
            }


            try {
                FileOutputStream fileOut =
                        new FileOutputStream("application/albumsArrList");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(albumsArrList);
                out.close();
                fileOut.close();

            } catch (IOException i) {
                i.printStackTrace();
            }
        }*/


        /*
        try {
            FileInputStream fis = openFileInput("test.txt");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis));
            String movieInfo = null;
            albumsArrList = new ArrayList<>();
            while ((movieInfo = br.readLine()) != null) {

                albumsArrList.add(new Album(movieInfo));

            }
        } catch (IOException e) {

            String[] moviesList = getResources().getStringArray(R.array.albums_array);
            albumsArrList = new ArrayList<>(moviesList.length);
            for (int i = 0; i < moviesList.length; i++) {
                Album temp = new Album( moviesList[i] );
                temp.photos.add(new Photo("/butts" , "Photo 1"));
                temp.photos.add(new Photo("/butts" , "Photo 2"));
                temp.photos.add(new Photo("/butts" , "Photo 3"));
                temp.photos.add(new Photo("/butts" , "Photo 4"));
                temp.photos.add(new Photo("/butts" , "Photo 5"));
                temp.photos.add(new Photo("/butts" , "Photo 6"));
                albumsArrList.add(new Album(moviesList[i]));
            }
        }
         */

        listView = findViewById(R.id.movies_list);
        listView.setAdapter(
                new ArrayAdapter<Album>(this, R.layout.albums, albumsArrList));

        // show movie for possible edit when tapped
        listView.setOnItemClickListener((p, V, pos, id) ->
                showAlbum(pos));

    }

    private void showAlbum(int pos) {
        //Bundle bundle = new Bundle();
       // Album album = albumsArrList.get(pos);
      //  bundle.putInt(albumView.ALBUM_INDEX, pos);
       // bundle.putString(albumView.ALBUM_NAME, album.name);
       // bundle.putParcelableArrayList(albumView.PHOTO_LIST , album.photos );

        selected=albumsArrList.get(pos);
        Intent intent = new Intent(this, EditAlbum.class);
      //  intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_ALBUM_CODE);
    }

    private void addAlbum() {
        Intent intent = new Intent(this, AddAlbum.class);
        startActivityForResult(intent, ADD_ALBUM_CODE);
    }
    private void searchPhotos() {
        Intent intent = new Intent(this, searchPhotos.class);
        startActivityForResult(intent, SEARCH_PHOTO_CODE);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
/*
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        // gather all info passed back by launched activity
        String name = bundle.getString(AddAlbum.ALBUM_NAME);
        int index = bundle.getInt(AddAlbum.ALBUM_INDEX);

        if (requestCode == EDIT_ALBUM_CODE) {
            Album movie = albumsArrList.get(index);
            movie.name = name;
        } else {
            albumsArrList.add(new Album(name));
        }
*/
        // redo the adapter to reflect change^K

        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
        //listView.setAdapter(
               // new ArrayAdapter<Album>(this, R.layout.albums, albumsArrList));


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addAlbum();
                return true;
            case R.id.action_search:
                searchPhotos();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}