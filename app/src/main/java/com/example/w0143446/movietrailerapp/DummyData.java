package com.example.w0143446.movietrailerapp;

/**
 * Created by w0143446 on 11/30/2016.
 */
public class DummyData {

    public void addData(DBAdapter db){
        //a function to add some dummy data to the application

        //add 7 Videos
        db.open();
        //http://stackoverflow.com/questions/2068344/how-do-i-get-a-youtube-video-thumbnail-from-the-youtube-api
        //(String name, String desc, int rating, String link, String category, int thumbnail)
        long id = db.insertVideo("Star Wars", "Some stupid movie about people a big hairy chewbaca",
                1.5f, "frdj1zb9sMY",
                "Adventure","https://img.youtube.com/vi/frdj1zb9sMY/sddefault.jpg");

        id = db.insertVideo("Peep Show - Ergonomic Keyboard", "Jez and Mark try to impress a girl in the elevator",
                4f, "pkK23-g-r7A",
                "Adventure","https://img.youtube.com/vi/pkK23-g-r7A/sddefault.jpg");

        id = db.insertVideo("It's Always Sunny - The D.E.N.N.I.S. System", "Dennis explains his system of attracting girls",
                3f, "95Fx2aYQbQs",
                "Adventure","https://img.youtube.com/vi/95Fx2aYQbQs/sddefault.jpg");

        id = db.insertVideo("Officespace - The Photocopier Beatdown (CLEAN)", "The nerds enact revenge on photocopier mafia style",
                3f, "fjsSr3z5nVk",
                "Adventure","https://img.youtube.com/vi/v=fjsSr3z5nVk/sddefault.jpg");

        id = db.insertVideo("Kids in the Hall - Bad Doctor", "The I'm a bad doctor skit with Dave Foley",
                5f, "Pbjypn9JtKE",
                "Adventure","https://img.youtube.com/vi/v=Pbjypn9JtKE/sddefault.jpg");

        id = db.insertVideo("Dimitri Martin - Some Jokes", "Dimitri Martin's classic one liners",
                3.5f, "fAkN5j54eEA", "Jokes", "https://img.youtube.com/vi/v=fAkN5j54eEA/sddefault.jpg");

        id = db.insertVideo("The Whitest Kids You Know- Clean Wet", "The best toilet jokes from the defunct Kids in the hall ripoff skit comedy",
                2.5f, "cL8Yors-C2A", "Jokes", "https://img.youtube.com/vi/v=cL8Yors-C2A/sddefault.jpg");

        id = db.insertVideo("Homer gets a Gun", "The best from season 9 of the simpsons",
                4f, "ZuG9kUiRC_I", "TV", "https://img.youtube.com/vi/v=ZuG9kUiRC_I/sddefault.jpg");


        db.close();
    }
}
