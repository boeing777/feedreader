package deveyes.it.com.customviews;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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


    public static class PlaceholderFragment extends Fragment {


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            LinearLayout rootViewContainer = (LinearLayout) rootView.findViewById(R.id.rootViewContainer);
            rootViewContainer.removeAllViews();

            FragmentManager fragMan = getFragmentManager();
            FragmentTransaction fragTransaction;


            fragTransaction = fragMan.beginTransaction();
            fragTransaction.add(rootViewContainer.getId(), HomePrimeArticleFragment.newInstance("Fragment", "One") , "fragment1");
            fragTransaction.add(rootViewContainer.getId(), HomeTrendingArticleFragment.newInstance("Fragment", "One") , "fragment2");
            fragTransaction.add(rootViewContainer.getId(), HomeVideoFragment.newInstance("Fragment", "Three") , "fragment3");
            fragTransaction.add(rootViewContainer.getId(), HomePhotoFragment.newInstance("Fragment", "Four") , "fragment4");
            fragTransaction.commit();


            return rootView;
        }
    }
}
