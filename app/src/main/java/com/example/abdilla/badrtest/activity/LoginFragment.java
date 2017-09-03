package com.example.abdilla.badrtest.activity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.abdilla.badrtest.R;
import com.example.abdilla.badrtest.SessionManager;
import com.example.abdilla.badrtest.api.body.Login;
import com.example.abdilla.badrtest.api.body.Register;
import com.example.abdilla.badrtest.api.body.User;
import com.example.abdilla.badrtest.helper.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abdilla on 31/08/17.
 */

public class LoginFragment extends BaseFragment {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    protected @LayoutRes
    int setView(){
        return R.layout.fragment_login;
    }

    protected void begin(){
    }

    @OnClick(R.id.google)
    void google(){

    }

    @OnClick(R.id.fb)
    void fb(){

    }

    @OnClick(R.id.login)
    void login() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        activity.showLoadingDialog();
        activity.addSubscription(activity.application.getAuthApi().login(new Login(email.getText().toString(), password.getText().toString()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.dismissLoadingDialog();
                        activity.showSnackBar(R.string.notif_auth_login_failed);
                    }

                    @Override
                    public void onNext(Response<User> voidResponse) {
                        com.example.abdilla.badrtest.model.User user = voidResponse.getResult();
                        String token = voidResponse.getResult().getAccess_token();
                        SessionManager.setAccessToken(activity, token);
                        SessionManager.setUser(activity, user);
                        activity.dismissLoadingDialog();
                        activity.startActivity(activity, HomeActivity.class);
                        activity.finish();
                    }
                })
        );
        //test();
    }

    void test(){
        activity.addSubscription(activity.application.getAuthApi().test(new Login(email.getText().toString(), password.getText().toString()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {

                    }
                })
        );
    }
}
