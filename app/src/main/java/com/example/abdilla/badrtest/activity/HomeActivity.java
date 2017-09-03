package com.example.abdilla.badrtest.activity;

import android.support.annotation.LayoutRes;

import com.example.abdilla.badrtest.R;
import com.example.abdilla.badrtest.SessionManager;
import com.example.abdilla.badrtest.helper.Response;

import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abdilla on 02/09/17.
 */

public class HomeActivity extends BaseActivity {

    protected @LayoutRes
    int setView(){
        return R.layout.activity_home;
    }

    protected void begin(){}

    @OnClick(R.id.logout)
    void logout(){
        showLoadingDialog();
        addSubscription(application.getAuthApi().logout()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                        showSnackBar(R.string.notif_home_logout_failed);
                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {
                        SessionManager.clearSession(HomeActivity.this);
                        dismissLoadingDialog();
                        startActivity(HomeActivity.this, MainActivity.class);
                        finish();
                    }
                })
        );
    }
}
