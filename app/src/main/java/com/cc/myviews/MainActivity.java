package com.cc.myviews;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cc.myviews.galleryview.galleryActivity;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView gv;
    private String[] names;
    private String[] packageNames;

    @Override
    protected void loadView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setListener() {
        gv.setOnItemClickListener(this);
    }

    @Override
    protected void initView() {
        MainAdapter adapter = new MainAdapter(this, names);
        gv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        names = new String[]{"galleryActivity", "YoutubeDragActivity"};
        packageNames = new String[]{"galleryview", "youtubeDragView"};
    }

    @Override
    protected void findView() {
        gv = (GridView) findViewById(R.id.gv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Intent intent = new Intent(this, Class.forName("com.cc.myviews."+packageNames[position]+"."+names[position]));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
