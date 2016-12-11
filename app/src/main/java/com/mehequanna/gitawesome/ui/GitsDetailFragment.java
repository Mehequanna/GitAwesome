package com.mehequanna.gitawesome.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehequanna.gitawesome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GitsDetailFragment extends Fragment {


    public GitsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gits_detail, container, false);
    }

}
