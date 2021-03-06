package com.example.w0143446.movietrailerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;

/*
Note: backed up on Git hub. If in D313 try
https://www.londonappdeveloper.com/how-to-use-git-hub-with-android-studio/
 */
public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    DBAdapter db;
    ListView listview;
    DummyData dummyData;
    private CustomList adapter;

    //shared preferences stuff
    public SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);

       /*
       Use this only on first load of Emulator!!!
        */
        settings = this.getPreferences(Context.MODE_PRIVATE); //0 = private
        boolean popdatabase = settings.getBoolean("PopulateDatabase", true); //if there isn't already a property set, make true
        if(popdatabase) {
            dummyData = new DummyData();
            dummyData.addData(db); //add my data to dbAdapter
            System.out.println("database populated");
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("PopulateDatabase", false);
        editor.apply(); //switched to apply as per recommendations

        //db.getAllVideo();
        listview = (ListView) findViewById(R.id.listView);

        db.open();
        adapter = new CustomList(MainActivity.this, db.getVideoTitles(), db.getVideoIDs(), db.getVideoThumbnails()); //convert string set to hashset
        db.close();

        listview.setAdapter(adapter);
        try {
            String destPath = "/data/data/" + getPackageName() + "/database/MyDB";
            //String destPath = Environment.getExternalStorageDirectory().getPath() +
            //getPackageName() + "/database/MyDB";
            File f = new File(destPath);
            if(!f.exists()) {
                CopyDB(getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath));
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

         btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddEditForm.class);
                myIntent.putExtra("Type", "Create");
                startActivity(myIntent); //no result expected back
            }
        });//end btnAdd click Event
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "You Clicked at " + i, Toast.LENGTH_SHORT).show();

                //create a new intent, pass image, send intent
                Intent myIntent = new Intent(MainActivity.this, VideoDetailsActivity.class);

                myIntent.putExtra("videoID", adapter.videoID[i]);
                Toast.makeText(MainActivity.this, adapter.videoTitle[i], Toast.LENGTH_SHORT).show();
                startActivity(myIntent); //no result expected back
            }
        });
    }//end method onCreate

    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException{
        //copy 1k bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer,0,length);
        }
        inputStream.close();
        outputStream.close();

    }//end method CopyDB

    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Name: " + c.getString(1) + "\n" +
                        "Email: " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }

    public long addVideoClip(String name, String desc, int rating, String link, String category, String thumbnail){
        //add a contact- CREATE NEW RECORD
        db.open();
        long id = db.insertVideo(name, desc, rating, link, category, thumbnail);
        //id = db.insertContact("Sue Me","Sue@Me.ca");
        db.close();
        return id;
    }

    public void getAllVideoClips(){
        //get all videos - READ
        //right now this just prints the list to a toast
        db.open();
        Cursor c = db.getAllVideo();
        if(c.moveToFirst())
        {
            do{
                DisplayContact(c);
            }while(c.moveToNext());
        }
        db.close();
    }

    public void getVideo(int id){
        //get a single contact - READ
        db.open();
        Cursor c = db.getAllVideo();
        c = db.getVideo(id);
        if(c.moveToFirst())
            DisplayContact(c);
        else
            Toast.makeText(this,"No contact found",Toast.LENGTH_LONG).show();

        db.close();
    }
}//end class