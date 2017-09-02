package com.example.abdilla.badrtest.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.abdilla.badrtest.BuildConfig;
import com.example.abdilla.badrtest.R;
import com.example.abdilla.badrtest.activity.adapter.PagerAdapter;
import com.example.abdilla.badrtest.model.Page;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;

    public static void startActivity(Context context){
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    protected @LayoutRes
    int setView(){
        return R.layout.activity_main;
    }

    protected void begin(){
        Log.i("Url", BuildConfig.BASE_URL);
        Page loginPage = new Page();
        loginPage.title = getString(R.string.title_auth_login);
        loginPage.fragment = LoginFragment.newInstance();
        Page registerPage = new Page();
        registerPage.title = getString(R.string.title_auth_register);
        registerPage.fragment = RegisterFragment.newInstance();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(loginPage);
        adapter.addFragment(registerPage);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }
}
