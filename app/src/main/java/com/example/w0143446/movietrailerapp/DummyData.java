package com.example.w0143446.movietrailerapp;

/**
 * Created by w0143446 on 11/30/2016.
 */
public class DummyData {
    DBAdapter db;
    public void addData(DBAdapter db){
        //a function to add some dummy data to the application
       this.db = db;
        //add 7 Videos
        db.open();
        //http://stackoverflow.com/questions/2068344/how-do-i-get-a-youtube-video-thumbnail-from-the-youtube-api
        //(String name, String desc, int rating, String link, String category, int thumbnail)
        long id = db.insertVideo("Star Wars", "Some stupid movie about people a big hairy chewbaca",
                2, "https://www.youtube.com/watch?v=frdj1zb9sMY",
                "Adventure",R.drawable.StarWars);

        id = db.insertVideo("Star Wars", "Some stupid movie about people a big hairy chewbaca",
                2, "https://www.youtube.com/watch?v=pkK23-g-r7A",
                "Adventure",R.drawable.StarWars);


        db.close();
    }
}
