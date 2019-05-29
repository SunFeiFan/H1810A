package com.example.mode_xiaowangzi.model;

import android.util.Log;

import com.example.mode_xiaowangzi.api.ApiServer;
import com.example.mode_xiaowangzi.bean.GrilBean;
import com.example.mode_xiaowangzi.callback.GrilCallBack;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GrilModel implements GrilIModel{


    private static final String TAG = "GrilModel";

    @Override
    public void getIModelData(final GrilCallBack grilCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.getGrilUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(ApiServer.class)
                .getGrilData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GrilBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(GrilBean grilBean) {
                        List<GrilBean.ResultsBean> results = grilBean.getResults();
                        grilCallBack.getGrilCallBackYes(results);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
