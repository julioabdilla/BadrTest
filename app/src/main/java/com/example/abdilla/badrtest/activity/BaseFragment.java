package com.example.abdilla.badrtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdilla.badrtest.App;
import com.example.abdilla.badrtest.R;

import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * Created by abdilla on 31/08/17.
 */

public abstract class BaseFragment extends Fragment {

    private ObjectGraph mFragmentGraph;
    BaseActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getModules() != null) {
            App application = (App) activity.getApplication();
            mFragmentGraph = application.createScopedGraph(getModules().toArray());
            mFragmentGraph.inject(this);
        }
        this.activity = (BaseActivity)activity;
    }

    protected abstract @LayoutRes
    int setView();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(setView(), container, false);
        ButterKnife.bind(this, view);
        begin();
        return view;
    }

    protected abstract void begin();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public BaseActivity getBaseActivity(){
        return activity;
    }

    public void inject(Object object) {
        mFragmentGraph.inject(object);
    }

    protected List<Object> getModules() {
        return null;
    }
}
