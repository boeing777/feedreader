package com.it.deveyes.feedreader;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/*
* Steps
* Create a class that extends ContentProvider
* Create a contract class
* Create the UriMatcher definition
* Implement the onCreate() method
* Implement the getType() method
* Implement the CRUD methods
* Add the content provider to your AndroidManifest.xml
*
* */

public class FeedProvider extends ContentProvider {

    FeedDatabase mDatabaseHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static final int CHANNEL = 1;
    public static final int CHANNEL_ID = 2;
    public static final int CHANNEL_ITEMS = 3;
    public static final int CHANNEL_IMAGES = 4;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FeedContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "channels",CHANNEL);
        matcher.addURI(authority, "channels/*/items",CHANNEL_ID);
        matcher.addURI(authority, "items",CHANNEL_ITEMS);
        matcher.addURI(authority, "images",CHANNEL_IMAGES);
        matcher.addURI(authority, "channels/*/image",CHANNEL_ID);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mDatabaseHelper = new FeedDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        assert db != null;
        return null;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {

            case CHANNEL:
                return FeedContract.Channel.CONTENT_TYPE;

            case CHANNEL_ITEMS:
                return FeedContract.Item.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        assert db != null;

        final int match = sUriMatcher.match(uri);
        Uri result;

        switch (match) {
            case CHANNEL:
                long idChannel = db.insertOrThrow(FeedDatabase.Tables.CHANNEL, null, contentValues);
                result = Uri.parse(FeedContract.Channel.CONTENT_URI + "/" + idChannel);
                break;
            case CHANNEL_ITEMS:
                long idChannelItem = db.insertOrThrow(FeedDatabase.Tables.CHANNEL_ITEM, null, contentValues);
                result = Uri.parse(FeedContract.Item.CONTENT_URI + "/" + idChannelItem);
                break;
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return result;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
