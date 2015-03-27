package com.it.deveyes.feedreader;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper {

   private FeedDatabase mDatabaseHelper;


    public DatabaseHelper(Context context) {
        mDatabaseHelper = new FeedDatabase(context);
    }


    public int clearTable(String table){
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        return db.delete(table, "1",null);
    }


    public boolean isEmpty(String table){
        final SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        boolean isEmpty=false;
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+table, null);
        if (cur != null){
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {
                isEmpty=true;
            }

        }
        if(cur!=null && !cur.isClosed()){
            cur.close();
        }
        return isEmpty;
    }


    public int delete(String table, String selection, String selectionArgs[]) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        return db.delete(table,selection,selectionArgs);
    }


    public int deleteById(String table, long id) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String whereClause = "_id" + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        return db.delete(table,whereClause,whereArgs);
    }





}
