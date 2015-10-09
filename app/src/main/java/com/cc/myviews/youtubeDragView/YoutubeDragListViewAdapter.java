package com.cc.myviews.youtubeDragView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/9.
 */
public class YoutubeDragListViewAdapter extends BaseAdapter{
    private Context mContext;
    private ViewHolder holder;

    public YoutubeDragListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_youtube, null);
            holder = new ViewHolder();
            holder.tv = (TextView)convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv;
    }
}
