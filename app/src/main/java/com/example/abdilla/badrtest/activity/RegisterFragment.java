package com.example.abdilla.badrtest.activity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.util.ArrayMap;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.abdilla.badrtest.R;
import com.example.abdilla.badrtest.api.body.Register;
import com.example.abdilla.badrtest.api_volley.Request;
import com.example.abdilla.badrtest.helper.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abdilla on 31/08/17.
 */

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.conf)
    EditText conf;

    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }

    protected @LayoutRes
    int setView(){
        return R.layout.fragment_register;
    }

    protected void begin(){
    }

    @OnClick(R.id.google)
    void google(){

    }

    @OnClick(R.id.fb)
    void fb(){

    }

    @OnClick(R.id.register)
    void register() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        activity.showLoadingDialog();
        //try {
            //JSONObject paramObject = new JSONObject();
            //paramObject.put("email", "test2@test.com");
            //paramObject.put("password", "test1");
            //paramObject.put("confirmation_password", "test1");

            activity.addSubscription(activity.application.getAuthApi().register(new Register(email.getText().toString(), password.getText().toString(), conf.getText().toString()))
            //activity.addSubscription(activity.application.getAuthApi().register(paramObject.toString())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<Void>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            activity.dismissLoadingDialog();
                            activity.showSnackBar(R.string.notif_auth_register_failed);
                        }

                        @Override
                        public void onNext(Response<Void> voidResponse) {
                            clearField();
                            activity.dismissLoadingDialog();
                            activity.showSnackBar(R.string.notif_auth_register_success);
                        }
                    })
            );
        //}catch (JSONException e){
//
        //}
        /*activity.application.getQueue().add(Request.Post("freedom/auth/register", new Register(email.getText().toString(), password.getText().toString(), conf.getText().toString()),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", error.getMessage());
                    }
                })
        );*/
    }

    private void clearField(){
        name.setText("");
        email.setText("");
        password.setText("");
        conf.setText("");
    }
}
