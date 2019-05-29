package com.example.mode_xiaowangzi.fragment;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.activity.MainActivity;
import com.example.mode_xiaowangzi.api.ApiServer;
import com.example.mode_xiaowangzi.bean.Bean;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpDataFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * okhttp
     */
    private Button mOkhttp;
    /**
     * retrofit
     */
    private Button mRetrofit;
    /**
     * TextView
     */
    private TextView mOkhttpTv;
    /**
     * TextView
     */
    private TextView mRetrofitTv;
    private ImageView mOkhttpImage;
    private ImageView mRetrofitImage;
    private File file;
    /**
     * 测试广播
     */
    private Button mGuangbo;
    private NotificationManager manager;

    public UpDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_up_data, container, false);
        manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mOkhttp = (Button) inflate.findViewById(R.id.okhttp);
        mOkhttp.setOnClickListener(this);
        mRetrofit = (Button) inflate.findViewById(R.id.retrofit);
        mRetrofit.setOnClickListener(this);
        mOkhttpTv = (TextView) inflate.findViewById(R.id.okhttp_tv);
        mRetrofitTv = (TextView) inflate.findViewById(R.id.retrofit_tv);
        mOkhttpImage = (ImageView) inflate.findViewById(R.id.okhttp_image);
        mRetrofitImage = (ImageView) inflate.findViewById(R.id.retrofit_image);
        mGuangbo = (Button) inflate.findViewById(R.id.guangbo);
        mGuangbo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.okhttp:
                okhttpData();
                break;
            case R.id.retrofit:
                retrofitData();
                break;
            case R.id.guangbo:
                inittongzhi();
                break;
        }
    }

    private void inittongzhi() {
        //最上边写
        //                manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        //哪里用，那里写
        String channelId = "1";
        String channelName = "default";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(getActivity(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification build = new NotificationCompat.Builder(getActivity(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher)//设置小图
                .setContentText("你还想干啥？敲你的代码去~~~")//设置内容
                .setContentTitle("我是标题")//设置标题
                .setAutoCancel(true)//设置点击自动消失
                .build();
        manager.notify(100, build);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            okhttpData();
            retrofitData();
        }
    }


    private void initDataOkHttp() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            okhttpData();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void initDataRetrofit() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            retrofitData();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }


    private void retrofitData() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File env = Environment.getExternalStorageDirectory();
            file = new File(env, "fanxi.jpg");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.get("application/octer-stream"), "1810A");
        RequestBody requestBodyfile = RequestBody.create(MediaType.get("image/jpg"), file);
        MultipartBody.Part multi = MultipartBody
                .Part
                .createFormData("file", this.file.getName(), requestBodyfile);
        retrofit.create(ApiServer.class)
                .upload(requestBody, multi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(Bean bean) {
                        if (bean != null) {
                            if (bean.getCode() == 200) {
                                mRetrofitTv.setText(bean.getRes());
                                RoundedCorners roundedCorners = new RoundedCorners(20);
                                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                                        .override(300, 300);// 指定图片大小 Target.SIZE_ORIGINAL原始尺寸大小

                                Glide.with(getActivity())
                                        .load(bean.getData().getUrl())
                                        .apply(options)
                                        .into(mRetrofitImage);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void okhttpData() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File env = Environment.getExternalStorageDirectory();
            file = new File(env, "fanxi.jpg");
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.get("image/jpg"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "1810A")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(multipartBody)
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        final Bean bean = new Gson().fromJson(string, Bean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bean != null) {
                                    if (bean.getCode() == 200) {
                                        mOkhttpTv.setText(bean.getRes());
                                        RoundedCorners roundedCorners = new RoundedCorners(20);
                                        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                                                .override(300, 300);// 指定图片大小 Target.SIZE_ORIGINAL原始尺寸大小

                                        Glide.with(getActivity())
                                                .load(bean.getData().getUrl())
                                                .apply(options)
                                                .into(mOkhttpImage);
                                    }
                                }
                            }
                        });
                    }
                });

    }
}
