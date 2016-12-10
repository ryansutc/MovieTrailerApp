package com.example.w0143446.movietrailerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

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
    Button btnSave;
    Button btnCancel;

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
        loadRecord();

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do some validation here
                videoName = etName.getText().toString().trim();
                videoDesc = etDesc.getText().toString().trim();
                videoRating = videoRatingBar.getRating();
                videoUrl = etURL.getText().toString().trim();
                videoCat = etCategory.getText().toString().trim();
                videoThumb = etThumb.getText().toString().trim();
                adapter.open();
                try {
                    adapter.updateVideo(videoID, videoName, videoDesc, videoRating, videoUrl, videoCat, videoThumb);
                }
                catch( Exception e) {
                    Toast.makeText(AddEditForm.this, "Error: Could not update the record in database. Details \n" + e, Toast.LENGTH_LONG).show();
                    System.out.println("Error: " + e);
                }
                adapter.close();
                //return to details screen
                Intent myIntent = new Intent(AddEditForm.this, VideoDetailsActivity.class);
                myIntent.putExtra("videoID", videoID);
                startActivity(myIntent); //no result expected back
                Toast.makeText(AddEditForm.this,"Record Updated",Toast.LENGTH_SHORT).show();
            }
        }); //end btnSave envent

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AddEditForm.this, VideoDetailsActivity.class);
                myIntent.putExtra("videoID", videoID);
                startActivity(myIntent); //no result expected back
            }
        });
    }

    private void loadRecord(){
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
