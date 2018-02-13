package com.example.shipon.easytravel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shipon on 11/27/2017.
 */

public class historyTab extends Fragment {

    private ImageView im;
    public   String txt;

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setTitle("Co")


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        txt= getArguments().getString("placename");

        View view= inflater.inflate(R.layout.history,container,false);
        ImageView imageView = view.findViewById(R.id.idHistoryImageView);
        if(txt.equals("Cox,s Bazar")){

            imageView.setImageResource(R.drawable.cox);
        }
        if(txt.equals("St. Martins Island")) {
            imageView.setImageResource(R.drawable.saint);
        }
        return view;

    }

}
