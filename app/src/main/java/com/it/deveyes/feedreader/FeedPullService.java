package com.it.deveyes.feedreader;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.it.deveyes.feedreader.models.Channel;
import com.it.deveyes.feedreader.models.Item;

import org.apache.http.HttpStatus;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class FeedPullService extends IntentService {

    private static final String TAG = FeedPullService.class.getName();


    public static final String ACTION_DOWNLOAD_FEED = "com.it.deveyes.feedreader.action.DOWNLOAD_FEED";
    public static final String URL = "com.it.deveyes.feedreader.feed.url";
    public static final String CHANNEL_ID = "com.it.deveyes.feedreader.feed.url.channelId";

    private  int channelId;

    public FeedPullService() {
        super("FeedPullService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD_FEED.equals(action)) {
                if(!intent.hasExtra(URL) && !intent.hasExtra(CHANNEL_ID)){
                    throw new UnsupportedOperationException("URL ANd CHANNEL_ID extra must be provided");
                }
                final String url = intent.getStringExtra(URL);
                if(null==url || url.length()==0){
                    throw new UnsupportedOperationException(URL + "must not be null");
                }

                 channelId = intent.getIntExtra(CHANNEL_ID,0);
                if(channelId ==0){
                    throw new UnsupportedOperationException(CHANNEL_ID + "must be a valid channel id");
                }

                handleActionDownload(url);
            }
        }
    }



    private void handleActionDownload(String url) {
        InputStream  inputStream = downloadUrl(url);
        if(inputStream!=null){
            FeedReader feedReader = new FeedReader();
            try {
                List<Channel> channels = feedReader.parse(inputStream);
                saveToDatabase(channels);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void saveToDatabase(List<Channel> channels) {
        ContentResolver cr = getContentResolver();
        for(Channel channel: channels){
            ContentValues channelValue = new ContentValues();
            channelValue.put(FeedContract.Channel.CHANNEL_ID,channelId);
            channelValue.put(FeedContract.Channel.TITLE,channel.getTitle());
            cr.insert(FeedContract.Channel.CONTENT_URI,channelValue);
            List<Item> items =channel.getItems();
            for(Item item : items){
                ContentValues itemValue = new ContentValues();
                itemValue.put(FeedContract.Item.TITLE,item.getTitle());
                itemValue.put(FeedContract.Item.CHANNEL_ID,channelId);
                itemValue.put(FeedContract.Item.DESCRIPTION,item.getDescription());
                itemValue.put(FeedContract.Item.LINK,item.getLink());
                itemValue.put(FeedContract.Item.PUB_DATE,item.getDate());
                itemValue.put(FeedContract.Item.ENCLOSURE,item.getEnclosure());
                cr.insert(FeedContract.Item.CONTENT_URI, itemValue);
            }
        }
    }


    private InputStream downloadUrl(String myUrl) {
        java.net.URL url;
        try {
            url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if ((conn instanceof HttpURLConnection)) {
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int responseCode = conn.getResponseCode();
                if( HttpStatus.SC_OK==responseCode){
                    return conn.getInputStream();
                }
                else{
                    Log.e(TAG,"Http 200 not returned. We got:"+responseCode);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }




}
