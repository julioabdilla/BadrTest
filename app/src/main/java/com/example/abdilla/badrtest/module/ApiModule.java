package com.example.abdilla.badrtest.module;

import android.content.Context;
import android.content.Intent;

import com.example.abdilla.badrtest.BuildConfig;
import com.example.abdilla.badrtest.SessionManager;
import com.example.abdilla.badrtest.api.Auth;
import com.example.abdilla.badrtest.helper.AnnotationStrategy;
import com.example.abdilla.badrtest.helper.ConnectionErrorEvent;
import com.example.abdilla.badrtest.helper.CookieManager;
import com.example.abdilla.badrtest.helper.ForApplication;
import com.example.abdilla.badrtest.helper.RetrofitException;
import com.example.abdilla.badrtest.helper.RxErrorHandlingCallAdapterFactory;
import com.example.abdilla.badrtest.helper.UnauthorizedErrorEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by abdilla on 31/08/17.
 */

@Module(library = true, complete = false)
public class ApiModule {

    public static String ON_CONNECTION_ERROR = "ON_CONNECTION_ERROR";
    public static String ON_UNAUTHORIZED = "ON_UNAUTHORIZED";

    @Provides
    public Retrofit.Builder provideRestAdapterBuilder(
            @ForApplication final Context context,
            final Bus bus) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setExclusionStrategies(new AnnotationStrategy())
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .build();
                        if (SessionManager.getAccessToken(context) != null) {
                            request = request.newBuilder()
                                    .addHeader("Authorization", SessionManager.getAccessToken(context))
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(logging)
                .cookieJar(new CookieManager(context))
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(new RxErrorHandlingCallAdapterFactory.ErrorListener() {
                    @Override
                    public Throwable onError(RetrofitException cause) {
                        cause.printStackTrace();
                        if (cause.getKind() == RetrofitException.Kind.NETWORK) {
                            bus.post(new ConnectionErrorEvent(cause));
                            context.sendBroadcast(new Intent(ApiModule.ON_CONNECTION_ERROR));
                            return new Exception("No internet connection", cause);
                        } else if (cause.getKind() == RetrofitException.Kind.HTTP) {
                            if (cause.getResponse().code() == 401) {
                                bus.post(new UnauthorizedErrorEvent(cause));
                                context.sendBroadcast(new Intent(ApiModule.ON_UNAUTHORIZED));
                                return new Exception("Unauthorized", cause);
                            } else {
                                return new Exception("Error reading response from server", cause);
                            }
                        }
                        return new Exception(cause.getMessage(), cause);
                    }
                }));
    }

    @Provides
    public Auth provideAuth(Provider<Retrofit.Builder> adapterBuilder) {
        return adapterBuilder.get()
                .baseUrl(getApiEndpoint())
                .build()
                .create(Auth.class);
    }

    private String getApiEndpoint(){
        return BuildConfig.BASE_URL;
    }
}