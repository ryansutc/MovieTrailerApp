package com.example.w0143446.movietrailerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddEditForm extends AppCompatActivity {
    private Integer videoID;
    private String videoName;
    private String videoDesc;
    private float videoRating;
    private String videoUrl;
    private String videoCat;
    private String videoThumb;

    EditText etName;
    EditText etDesc;
    RatingBar videoRatingBar;
    EditText etURL;
    EditText etCategory;
    EditText etThumb;

    DBAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.etName);
        etDesc = (EditText) findViewById(R.id.etDesc);
        videoRatingBar = (RatingBar) findViewById(R.id.videoRatingBar);
        etURL = (EditText) findViewById(R.id.etURL);
        etCategory = (EditText) findViewById(R.id.etCategory);
        etThumb = (EditText) findViewById(R.id.etThumb);

        //handle intent
        Intent myintent = getIntent();
        videoID = myintent.getIntExtra("videoID", 0);
        adapter = new DBAdapter(this);
        adapter.open();
            Cursor cursor = adapter.getVideo(videoID);
            etName.setText(cursor.getString(1));
            etDesc.setText(cursor.getString(2));
            videoRatingBar.setStepSize(0.5f);
            videoRatingBar.setRating(cursor.getFloat(3));
            etURL.setText(cursor.getString(4));
            etCategory.setText(cursor.getString(5));
            etThumb.setText(cursor.getString(6));
        adapter.close();
    }

}
