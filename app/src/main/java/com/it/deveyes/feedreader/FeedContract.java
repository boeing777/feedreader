package com.it.deveyes.feedreader;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class FeedContract {

    private static final String PATH_ITEMS = "items";


    public static final String CONTENT_AUTHORITY = "com.it.deveyes.affari";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);




    public static class Channel implements BaseColumns {
        public static final String TITLE = "title";
        public static final String LINK = "link";
        public static final String DESCRIPTION = "description";
        public static final String LANGUAGE = "lanaguage";
    }



    public static class Image implements BaseColumns {

        public static final String URL = "url";
        public static final String LINK = "link";
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";
        public static final String CHANNEL_ID = "idChannel";
    }



    public static class Item implements BaseColumns {

        public static final String TITLE = "title";
        public static final String LINK = "link";
        public static final String DESCRIPTION = "description";
        public static final String PUB_DATE = "pubDate";
        public static final String ENCLOSURE = "enclosure";
        public static final String CHANNEL_ID = "idChannel";

        /**
         * MIME type for lists of entries.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.it.deveyes.affari.items";
        /**
         * MIME type for individual entries.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.it.deveyes.affari.item";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEMS).build();
    }



}
