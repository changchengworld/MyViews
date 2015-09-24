package com.cc.myviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by silvercc on 15/9/24.
 */
public class MainAdapter extends BaseAdapter{

    private Context mContext;
    private String[] mNames;
    private ViewHolder mViewHolder;

    public MainAdapter(Context context, String[] names) {
        mContext = context;
        mNames = names;
    }

    @Override
    public int getCount() {
        return mNames.length;
    }

    @Override
    public Object getItem(int position) {
        return mNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            mViewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_main, null);
            mViewHolder.tv = (TextView)convertView.findViewById(R.id.tv_main);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        mViewHolder.tv.setText(mNames[position]);
        return convertView;
    }

    class ViewHolder{
        TextView tv;
    }
}
