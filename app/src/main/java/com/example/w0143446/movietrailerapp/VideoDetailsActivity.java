package com.example.w0143446.movietrailerapp;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.*;


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
    TextView tvCategory;
    RatingBar ratingBar;
    Button btnEdit;
    Button btnWatch;
    Button btnDelete;

    DialogInterface.OnClickListener dialogClickListener;

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

        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCategory.setText(videoCat);
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
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a new intent, pass image, send intent
                Intent myIntent = new Intent(VideoDetailsActivity.this, AddEditForm.class);

                myIntent.putExtra("videoID", videoID);
                startActivity(myIntent); //no result expected back
            }
        }); //end btnEdit on click listener class
        btnWatch = (Button) findViewById(R.id.btnWatch);
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a new intent, pass image, send intent
                // notes here: http://stackoverflow.com/questions/11767172/activitynotfoundexception-when-loading-youtube-video
                try {
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent(VideoDetailsActivity.this,
                            "AIzaSyAVZk_GvGz06jzKx1X2SGP0MHATBFt16eU", videoUrl);
                    startActivity(intent);
                }
                catch(ActivityNotFoundException e){
                        Toast.makeText(VideoDetailsActivity.this, "Plase install Youtube app to see this video", Toast.LENGTH_LONG).show();
                }
            }
        }); //end btnEdit on click listener class

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder; //dialog builder
                builder = new AlertDialog.Builder(VideoDetailsActivity.this);

                builder.setMessage("Woah! Are you sure you want to delete this video record completely?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();//handle the person clicking no to the proceed dialog.
            }
        });

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        adapter.open();
                            adapter.deleteVideo(videoID);
                        adapter.close();
                        Toast.makeText(VideoDetailsActivity.this,"Record Deleted",Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(VideoDetailsActivity.this, MainActivity.class);
                        startActivity(myIntent); //no result expected back
                        //call Next Page
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };//end dialogClickListener
    }

    @Override
    public void onBackPressed() {
        // Overriding the back button push on the device. Go home!
        Intent myIntent = new Intent(VideoDetailsActivity.this, MainActivity.class);
        startActivity(myIntent); //no result expected back
        return;
    }
}
