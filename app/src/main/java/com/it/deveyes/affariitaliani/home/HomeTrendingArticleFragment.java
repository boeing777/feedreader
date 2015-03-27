package com.it.deveyes.affariitaliani.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.it.deveyes.affariitaliani.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link HomeTrendingArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeTrendingArticleFragment extends Fragment {

    public static HomeTrendingArticleFragment newInstance() {
        HomeTrendingArticleFragment fragment = new HomeTrendingArticleFragment();
        return fragment;
    }

    public HomeTrendingArticleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_trending_article, container, false);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Android",
                "Adapter",
                "Simple",

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.home_trending_article_item_layout, R.id.textView, values);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 200, 0);

        for(int i=0;i<adapter.getCount();i++) {
            View v = adapter.getView(i, null, null);
            v.setLayoutParams(layoutParams);
            view.addView(v);
        }
        return view;
    }


}
