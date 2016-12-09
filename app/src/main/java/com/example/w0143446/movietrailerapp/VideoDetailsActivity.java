package com.example.w0143446.movietrailerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class VideoDetailsActivity extends AppCompatActivity {
    private Integer videoID;
    private String videoName;
    private String videoDesc;
    private float videoRating;
    private String videoUrl;
    private String videoCat;
    private String videoThumb;
    private DBAdapter adapter;
    ImageView imageView;
    TextView tvName;
    TextView tvDescription;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myintent = getIntent();
        videoID = myintent.getIntExtra("videoID", 0);
        adapter = new DBAdapter(VideoDetailsActivity.this);
        adapter.open();
        Cursor videoCursor = adapter.getVideo(videoID);
        adapter.close();
        videoName = videoCursor.getString(1);
        videoDesc = videoCursor.getString(2);
        videoRating = videoCursor.getFloat(3);
        videoUrl = videoCursor.getString(4);
        videoCat = videoCursor.getString(5);
        videoThumb = videoCursor.getString(6);

        String videoThumbURL = videoCursor.getString(6);
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(videoName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setText(videoDesc);
        imageView = (ImageView) findViewById(R.id.imageView);
        new ImageDownloader((ImageView)  imageView).execute(videoThumbURL);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setStepSize(0.5f);
        ratingBar.setRating(videoRating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                adapter.open();
                videoRating = ratingBar.getRating();
                adapter.updateVideo(videoID,videoName, videoDesc, videoRating, videoUrl, videoCat, videoThumb);
            }
        });
    }

}
