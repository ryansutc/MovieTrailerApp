package com.example.w0143446.movietrailerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.database.*;
import android.util.Log;

/**
 * Created by w0143446 on 11/30/2016.
 */
public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC ="desc";
    public static final String KEY_LINK ="link";
    public static final String KEY_RATING ="rating";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_THUMBNAIL = "thumbnail";

    public static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE = "contacts";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
            "create table videos(_id integer primary key autoincrement,"
                    + "name text not null," +
                    "desc text not null," +
                    "link text," +
                    "rating int," +
                    "category text," +
                    "thumbnail int);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    //inner class
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//end method onCreate

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrade database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }//end onUpgrade
    }

    //open the database
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //close the database
    public void close()
    {
        DBHelper.close();
    }

    //insert a contact into the database
    public long insertVideo(String name, String desc, int rating, String link, String category, int thumbnail)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DESC, desc);
        initialValues.put(KEY_RATING, rating);
        initialValues.put(KEY_LINK, link);
        initialValues.put(KEY_CATEGORY, category);
        initialValues.put(KEY_THUMBNAIL, thumbnail);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //delete a particular contact
    public boolean deleteVideo(long rowId)
    {
        //if 0 result failed
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId,null) >0;
    }

    //retrieve all the contacts
    public Cursor getAllVideo()
    {
            //Query the given table, returning a Cursor over the result set
        return db.query(DATABASE_TABLE,
                new String[]{KEY_ROWID,KEY_NAME,KEY_DESC, KEY_RATING, KEY_LINK, KEY_CATEGORY, KEY_THUMBNAIL} //columns
                ,null,null,null,null,null);
    }

    public String[] getVideoTitles(){
        //Return an array string of all video titles
        Cursor c = getAllVideo();
        String[] videoTitles = new String[c.getCount()];
        int x = 0;
        if(c.moveToFirst())
        {
            do{
                videoTitles[x] = c.getString(1);
                x += 1;
            }while(c.moveToNext());
        }
        db.close();
        return null;
    }

    public Integer[] getVideoThumbnails(){
        //return an array of video thumbnail ids
        Cursor c = getAllVideo();
        Integer[] videoThumbnails = new Integer[c.getCount()];
        int x = 0;
        if(c.moveToFirst())
        {
            do{
                videoThumbnails[x] = c.getInt(6);
                x += 1;
            }while(c.moveToNext());
        }
        db.close();
        return null;
    }


    //retrieve a single contact
    public Cursor getVideo(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE,
                new String[]{KEY_ROWID,KEY_NAME,KEY_DESC, KEY_RATING, KEY_LINK, KEY_CATEGORY, KEY_THUMBNAIL} //columns
               ,KEY_ROWID + "=" + rowId,null,null,null,null,null);

        if(mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //updates a contact
    public boolean updateVideo(long rowId,String name, String desc, String rating, String link, String category, int thumbnail)
    {
        ContentValues cval = new ContentValues();
        cval.put(KEY_NAME, name);
        cval.put(KEY_DESC, desc);
        cval.put(KEY_RATING, rating);
        cval.put(KEY_LINK, link);
        cval.put(KEY_CATEGORY, category);
        cval.put(KEY_THUMBNAIL, thumbnail);
        return db.update(DATABASE_TABLE, cval, KEY_ROWID + "=" + rowId,null) >0;
    }

}//end class DBAdapter
