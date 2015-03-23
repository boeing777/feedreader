package com.it.deveyes.feedreader;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedDatabase  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "affari_italiani.db";

    interface Tables {
         static final String CHANNEL = "channel";
         static final String CHANNEL_IMAGE = "channel_image";
         static final String CHANNEL_ITEM = "channel_item";

    }

    private static final String SQL_CREATE_TABLE_CHANNEL = "CREATE TABLE " + Tables.CHANNEL +
            "(" +
            FeedContract.Channel._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FeedContract.Channel.TITLE + " TEXT," +
            FeedContract.Channel.DESCRIPTION + " TEXT," +
            FeedContract.Channel.LINK + " TEXT," +
            FeedContract.Channel.LANGUAGE + " TEXT" +
    ")";
    private static final String SQL_DROP_TABLE_CHANNEL = "DROP TABLE IF EXISTS " + Tables.CHANNEL;



    private static final String SQL_CREATE_TABLE_CHANNEL_IMAGE = "CREATE TABLE " + Tables.CHANNEL_IMAGE +
            "(" +
            FeedContract.Image._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FeedContract.Image.URL + " TEXT," +
            FeedContract.Image.LINK + " TEXT," +
            FeedContract.Image.WIDTH + " TEXT," +
            FeedContract.Image.HEIGHT + " TEXT," +
            FeedContract.Image.CHANNEL_ID  +" INTEGER,"+
            " FOREIGN KEY("+ FeedContract.Image.CHANNEL_ID +")" +
            " REFERENCES "+Tables.CHANNEL+"("+ FeedContract.Channel._ID+")"+
            ")";
    private static final String SQL_DROP_TABLE_CHANNEL_IMAGE = "DROP TABLE IF EXISTS " + Tables.CHANNEL_IMAGE;



    private static final String SQL_CREATE_TABLE_CHANNEL_ITEM    = "CREATE TABLE " + Tables.CHANNEL_ITEM +
            "(" +
            FeedContract.Item._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FeedContract.Item.TITLE + " TEXT," +
            FeedContract.Item.LINK + " TEXT," +
            FeedContract.Item.DESCRIPTION + " TEXT," +
            FeedContract.Item.PUB_DATE + " DATETIME," +
            FeedContract.Item.ENCLOSURE + " TEXT," +
            FeedContract.Item.CHANNEL_ID + " INTEGER," +
            " FOREIGN KEY("+ FeedContract.Image.CHANNEL_ID +")" +
            " REFERENCES "+Tables.CHANNEL+"("+ FeedContract.Channel._ID+")"+
            ")";
    private static final String SQL_DROP_TABLE_CHANNEL_ITEM = "DROP TABLE IF EXISTS " + Tables.CHANNEL_ITEM;



    public FeedDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CHANNEL);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CHANNEL_IMAGE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CHANNEL_ITEM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_CHANNEL_ITEM);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_CHANNEL_IMAGE);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_CHANNEL);
        onCreate(sqLiteDatabase);
    }
}
