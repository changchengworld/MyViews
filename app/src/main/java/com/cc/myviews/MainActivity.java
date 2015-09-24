package com.cc.myviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cc.myviews.galleryview.GalleryActivity;

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
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    private void startActivityByAcitvityName(ActivityName NAME){

    }
}
