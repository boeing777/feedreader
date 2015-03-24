package com.it.deveyes.affariitaliani;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.it.deveyes.feedreader.FeedContract;
import com.it.deveyes.feedreader.FeedProvider;
import com.it.deveyes.feedreader.FeedPullService;
import com.it.deveyes.feedreader.FeedReader;
import com.it.deveyes.feedreader.models.Channel;
import com.it.deveyes.feedreader.models.Item;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       ///  new DownloadFeedTask(1).execute("http://www.affaritaliani.it/static/rss/rssAPP2.aspx?idchannel=1");
        // new DownloadFeedTask(227).execute("http://www.affaritaliani.it/static/rss/rssAPP2.aspx?idchannel=227");

        Intent msgIntent = new Intent(this, FeedPullService.class);
        msgIntent.setAction(FeedPullService.ACTION_DOWNLOAD_FEED);
        msgIntent.putExtra(FeedPullService., strInputMsg);
        startService(msgIntent);


       /* Uri uri =  Uri.parse(FeedContract.Channel.CONTENT_URI+"/"+227+"/items");

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(uri,
                null, null, null, null);

        if (cur.getCount() > 0) {
            Log.i(TAG, "Showing values.....");
            while (cur.moveToNext()) {
                String Id = cur.getString(cur.getColumnIndex(FeedContract.Item._ID));
                String title = cur.getString(cur
                        .getColumnIndex(FeedContract.Item.TITLE));
                Log.d(TAG,"Id = " + Id + ", Note Title : " + title);
            }
        } else {
            Log.i(TAG, "No Notes added");
        }*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class DownloadFeedTask extends AsyncTask<String, Void, String> {

        int channelId;

        DownloadFeedTask(int channelId){
            this.channelId = channelId;
        }

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {

                FeedReader feedReader = new FeedReader();
                List<Channel> channels = feedReader.parse(downloadUrl(urls[0]));
                ContentResolver cr = getContentResolver();


                for(Channel channel: channels){

                    ContentValues channelValue = new ContentValues();
                    channelValue.put(FeedContract.Channel.TITLE,channel.getTitle());

                        cr.insert(FeedContract.Channel.CONTENT_URI,channelValue);

                        List<Item> items =channel.getItems();
                        Log.i(MainActivity.class.getName(),"size:"+items.size());

                        for(Item item : items){

                            ContentValues itemValue = new ContentValues();
                            itemValue.put(FeedContract.Item.TITLE,item.getTitle());
                            itemValue.put(FeedContract.Item.CHANNEL_ID,channelId);
                            itemValue.put(FeedContract.Item.DESCRIPTION,item.getDescription());
                            itemValue.put(FeedContract.Item.LINK,item.getLink());
                            itemValue.put(FeedContract.Item.PUB_DATE,item.getDate());
                            itemValue.put(FeedContract.Item.ENCLOSURE,item.getEnclosure());
                            cr.insert(FeedContract.Item.CONTENT_URI, itemValue);

                            Log.i(MainActivity.class.getName(),item.toString());
                        }
                }

                return "" ;
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return "Unable to retrieve feed. XmlPullParserException";

            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        }
    }


    private InputStream downloadUrl(String myurl) throws IOException {
        InputStream is = null;

            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(MainActivity.class.getName(), "The response is: " + response);
            return conn.getInputStream();


    }

}
