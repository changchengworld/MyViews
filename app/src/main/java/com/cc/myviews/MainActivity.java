package com.cc.myviews;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cc.myviews.galleryview.galleryActivity;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView gv;
    private String[] names;

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
        names = new String[]{"ViewGallery"};
    }
    @Override
    protected void findView() {
        gv = (GridView)findViewById(R.id.gv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, galleryActivity.class);
        startActivity(intent);
    }

    private void startActivityByAcitvityName(ActivityName NAME){

    }
}
