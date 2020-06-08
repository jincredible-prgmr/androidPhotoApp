package com.example.photos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class photoAdapter extends ArrayAdapter<Photo> {
    int resourceLayout;
    Context context;

    public photoAdapter(Context context, int r, ArrayList<Photo> l){
        super(context,r,l);
        this.resourceLayout=r;
        this.context=context;

    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView=convertView;
        LayoutInflater t= LayoutInflater.from(context);
        newView=t.inflate(resourceLayout,null);
        Photo p = getItem(position);
        TextView tv=(TextView)newView.findViewById(R.id.name);


        Uri test=Uri.parse(p.path);
        File file=new File(test.getPath());
        ImageView iv=(ImageView)newView.findViewById(R.id.imageView);


        if(!(p.path.equals("stock1")|| p.path.equals("stock2") ||p.path.equals("stock3")||p.path.equals("stock4")||p.path.equals("stock5"))){
            iv=(ImageView)newView.findViewById(R.id.imageView);
            iv.setImageURI(test);
            tv.setText(file.getName());
        }else
            if(p.path.equals("stock1")) {
                iv = (ImageView) newView.findViewById(R.id.imageView);
                Drawable x = context.getResources().getDrawable(R.drawable.stock1);
                iv.setImageDrawable(x);
                tv.setText(p.path);
        }else
            if(p.path.equals("stock2")) {
                iv = (ImageView) newView.findViewById(R.id.imageView);
                Drawable x = context.getResources().getDrawable(R.drawable.stock2);
                iv.setImageDrawable(x);
                tv.setText(p.path);
            }else
            if(p.path.equals("stock3")) {
                iv = (ImageView) newView.findViewById(R.id.imageView);
                Drawable x = context.getResources().getDrawable(R.drawable.stock3);
                iv.setImageDrawable(x);

            }else
            if(p.path.equals("stock4")) {
                iv = (ImageView) newView.findViewById(R.id.imageView);
                Drawable x = context.getResources().getDrawable(R.drawable.stock4);
                iv.setImageDrawable(x);
                tv.setText(p.path);
            }else
            if(p.path.equals("stock5")) {
                iv = (ImageView) newView.findViewById(R.id.imageView);
                Drawable x = context.getResources().getDrawable(R.drawable.stock5);
                iv.setImageDrawable(x);
                tv.setText(p.path);
            }




        return newView;
    }
}
