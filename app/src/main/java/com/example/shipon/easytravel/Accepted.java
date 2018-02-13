package com.example.shipon.easytravel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shipon on 12/11/2017.
 */

public class Accepted extends ArrayAdapter<String> {

   public static ArrayList<String> Namee;
    Context mContext;
    private Accepted.ViewHold mViewHold;
    public Accepted(@NonNull Context context, ArrayList<String> name) {
        super(context, R.layout.view_accept);
        this.Namee=name;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return Namee.size();
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        mViewHold=new Accepted.ViewHold();

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.view_accept, parent, false);

            mViewHold.textView = (TextView) convertView.findViewById(R.id.lname);






            convertView.setTag(mViewHold);

        } else {
            mViewHold = (Accepted.ViewHold)convertView.getTag();
        }


        mViewHold.textView.setText(Namee.get(position));

        return convertView;

    }




    static  class ViewHold{

        TextView textView;


    }

}

