package com.example.abdilla.badrtest.helper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by abdilla on 31/08/17.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    public interface ErrorListener{
        Throwable onError(RetrofitException exception);
    }

    private final RxJavaCallAdapterFactory original;
    private static ErrorListener _listener;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJavaCallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    public static CallAdapter.Factory create(ErrorListener listener){
        _listener = listener;
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit retrofit;
        private final CallAdapter<?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Observable<?> adapt(Call<R> call) {
            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(new Func1<Throwable, Observable>() {
                @Override
                public Observable call(Throwable throwable) {
                    return Observable.error(asRetrofitException(throwable));
                }
            });
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                if(_listener!=null) _listener.onError(RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit));
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
            }
            // A network error happened
            else if (throwable instanceof IOException) {
                IOException ioException = (IOException) throwable;
                if(_listener!=null) _listener.onError(RetrofitException.networkError(ioException));
                return RetrofitException.networkError(ioException);
            }
            // We don't know what happened. We need to simply convert to an unknown error
            else {
                if(_listener!=null) _listener.onError(RetrofitException.unexpectedError(throwable));
                return RetrofitException.unexpectedError(throwable);
            }
        }
    }
}