package com.cc.myviews.youtubeDragView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/9.
 */
public class YoutubeDragFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView lv;
    private YoutubeDragView youtubeLayout;

    @Override
    protected View loadView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_youtube_drag, null);
    }

    @Override
    protected void findView(View parentView) {
        lv = (ListView)parentView.findViewById(R.id.lv);
        youtubeLayout = (YoutubeDragView)parentView.findViewById(R.id.youtubeLayout);
    }

    @Override
    protected void init() {
        YoutubeDragListViewAdapter adapter = new YoutubeDragListViewAdapter(mParentContext);
        lv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        youtubeLayout.maximize();
    }
}
