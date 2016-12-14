package com.example.w0143446.movietrailerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by w0143446 on 12/8/2016.
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    /*
       Method to load the youtube video images from URL
       */
    ImageView bmImage;
    private Context mContext;

    public ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

        }
        return mIcon11;
    }
    protected void onPostExecute(Bitmap result) {
        //pDlg.dismiss();
        if(result != null){
            bmImage.setImageBitmap(result);
        }
    }
}