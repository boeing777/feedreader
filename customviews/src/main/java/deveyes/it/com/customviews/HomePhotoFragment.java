package deveyes.it.com.customviews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import mobi.parchment.widget.adapterview.listview.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePhotoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePhotoFragment newInstance(String param1, String param2) {
        HomePhotoFragment fragment = new HomePhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomePhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_four, container, false);

        String[] values = new String[] { "Android",
                "Adapter",
                "Simple",
                "Android",
                "Android",
                "Android"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.media_list_item_view, R.id.mediaThumbTextView, values);

        ListView listView = (ListView) view.findViewById(R.id.horizontal_list_view_video);
        listView.setAdapter(adapter);

        return view;
    }


}
