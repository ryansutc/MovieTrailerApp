package com.example.w0143446.movietrailerapp;

/**
 * Created by w0143446 on 11/30/2016.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;


import java.util.HashSet;
import java.util.Set;

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    protected final String[] videoTitle;
    protected final String[] thumbnail;
    protected final Integer[] videoID;
    private Set<String> viewedImgSet;
   //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    //StrictMode.setThreadPolicy(policy);

    public CustomList(Activity context, String[] videoTitle, Integer[] videoID, String[] thumbnail) {
        super(context, R.layout.list_single, videoTitle);
        this.context = context;
        this.videoTitle = videoTitle;
        this.videoID = videoID;
        this.thumbnail = thumbnail;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(videoTitle[position]);

        //http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
        new ImageDownloader((ImageView)  imageView).execute(thumbnail[position]);
        if (!hasImage(imageView)){
            BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.novideo);
        }

        //URL newurl = new URL("http://www.heyyou.com/image.png");   //thumbnail[position]);
        //Bitmap icon = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        //imageView.setImageBitmap(icon);
        //imageView.setImageResource(thumbnail[position]);
        return rowView;
    }

    public void setItemEnabled(int position, boolean enabled) {
        /*a custom function to toggle a particular
        list item to be enabled or disabled
        */
        String strPosition = String.valueOf(position);
        if (!enabled){
            //set should take care of duplicates
            this.viewedImgSet.add(strPosition);
        }
        else {
            this.viewedImgSet.remove(strPosition);
        }
    }
    public Set<String> getImageSet(){
        /* Getter: Return the set of the images as a hashset
         used to put the values back in the settings
         */

        System.out.println("Returned the Set");
        return this.viewedImgSet;
    }

    //this shamelessly copied from here: http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android
    private boolean hasImage(@NonNull ImageView view) {
        //check if image set for imageView
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }
}

