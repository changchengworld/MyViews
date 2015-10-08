package com.cc.myviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by silvercc on 15/9/24.
 */
public abstract class BaseFragment extends Fragment{

    protected Context mParentContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentContext = getActivity();
        View view = loadView(inflater);
        findView(view);
        init();
        return view;
    }

    protected abstract View loadView(LayoutInflater inflater);

    protected abstract void findView(View parentView);

    protected abstract void init();


}
