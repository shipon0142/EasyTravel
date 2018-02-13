package com.example.shipon.easytravel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shipon on 12/6/2017.
 */

public class myAdopter extends ArrayAdapter<String> {
    ArrayList<String> s_date;
    ArrayList<String> e_date;
    ArrayList<String> e_name;
    ArrayList<String> e_year;
    String ss,ee;
    Context mContext;
    private ViewHolder mViewHolder;
    public myAdopter(@NonNull Context context, ArrayList<String> date,ArrayList<String> e_date,ArrayList<String> e_Name,ArrayList<String> e_Year) {
        super(context, R.layout.list_view_layout);
        this.s_date = date;
        this.e_date=e_date;
        this.e_name=e_Name;
        this.e_year=e_Year;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return s_date.size();
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      mViewHolder=new ViewHolder();

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_view_layout, parent, false);

            mViewHolder.mStartDate = (TextView) convertView.findViewById(R.id.idDat);
            mViewHolder.mStartEnd = (TextView) convertView.findViewById(R.id.idSE);
            mViewHolder.mEventName = (TextView) convertView.findViewById(R.id.idEventNam);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        mViewHolder.mStartDate.setText(s_date.get(position)+", "+e_year.get(position));
        ss=s_date.get(position);
        ee=e_date.get(position);
        mViewHolder.mStartEnd.setText(ss+"-"+ee);
        mViewHolder.mEventName.setText(e_name.get(position));

        return convertView;

    }




    static  class ViewHolder{

        TextView mStartDate;
        TextView mEventName;
        TextView mStartEnd;
    }
}

