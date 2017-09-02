package com.example.abdilla.badrtest.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.abdilla.badrtest.App;
import com.example.abdilla.badrtest.R;
import com.example.abdilla.badrtest.helper.ForActivity;
import com.example.abdilla.badrtest.helper.ForApplication;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import dagger.ObjectGraph;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by abdilla on 31/08/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public App application;

    private ObjectGraph activityGraph;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private ProgressDialog loading;

    protected void startActivity(Context context, Class activity){
        Intent i = new Intent(context, activity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setView());
        ButterKnife.bind(this);
        application = (App) getApplication();

        if (getModules() != null) {
            activityGraph = application.createScopedGraph(getModules().toArray());
            activityGraph.inject(this);
        }

        begin();
    }

    protected abstract @LayoutRes
    int setView();

    protected abstract void begin();
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected List<Object> getModules() {
        return null;
    }

    public void inject(Object object) {
        activityGraph.inject(object);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        destroySubscription();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroySubscription();
    }

    @Optional
    @OnClick(R.id.back)
    protected void back(){
        super.onBackPressed();
    }

    public void showSnackBar(@StringRes int message){
        showSnackBar(getString(message));
    }

    public void showSnackBar(String message){
        try {
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }catch (Exception ignored){}
    }


    public void showToast(@StringRes int message){
        showToast(getString(message));
    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLoadingDialog(){
        showLoadingDialog(getString(R.string.txt_loading));
    }

    public void showLoadingDialog(@StringRes int message){
        showLoadingDialog(getString(message));
    }

    public void showLoadingDialog(String message){
        loading = ProgressDialog.show(this, "", message, true);
        loading.setCancelable(false);
    }

    public void dismissLoadingDialog(){
        if(loading!=null){
            loading.dismiss();
        }
    }

    public void showErrorDialog(@StringRes int message){
        showErrorDialog(getString(message));
    }

    public void showErrorDialog(@StringRes int title, @StringRes int message){
        showErrorDialog(title==0?null:getString(title), getString(message));
    }

    public void showErrorDialog(String message) {
        showErrorDialog(null, message);
    }

    public void showErrorDialog(String title, String message) {
        if(title==null){
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton(R.string.txt_ok, null)
                    .show();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(R.string.txt_ok, null)
                    .show();
        }
    }

    public void showChooseDialog(String message, DialogInterface.OnClickListener yesListener){
        showChooseDialog(message, yesListener, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public void showChooseDialog(String message, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.txt_ok, yesListener)
                .setNegativeButton(R.string.txt_cancel, noListener)
                .show();
    }

    public void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    private void destroySubscription(){
        mSubscriptions.clear();
    }
}
