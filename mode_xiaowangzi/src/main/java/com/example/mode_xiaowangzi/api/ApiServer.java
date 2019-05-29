package com.example.mode_xiaowangzi.api;

import com.example.mode_xiaowangzi.bean.Bean;
import com.example.mode_xiaowangzi.bean.GrilBean;
import com.example.mode_xiaowangzi.bean.WebBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServer {



    public static final String loginUrl = "http://yun918.cn/";

    @FormUrlEncoded
    @POST("study/public/index.php/login")
    Observable<ResponseBody> getLogin(@Field("username") String username, @Field("password") String password);

    //    http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1
    String getGrilUrl = "http://gank.io/";
    @GET("api/data/%E7%A6%8F%E5%88%A9/20/2")

    Observable<GrilBean> getGrilData();



    //http://yun918.cn/study/public/file_upload.php
    public String Url = "http://yun918.cn/study/";

    @Multipart   //文件上传请求头
    @POST("public/file_upload.php")
    Observable<Bean> upload(@Part("key") RequestBody key, @Part MultipartBody.Part file);


//    http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1


    /*public static final String loginUrl = "http://yun918.cn/";

    @FormUrlEncoded
    @POST("study/public/index.php/login")
    Observable<ResponseBody> getLogin(@Field("username") String username, @Field("password") String password);
*/

    String Urli = "http://www.wanandroid.com/project/";
    @GET("list/2/json?cid=402")
    Observable<WebBean> getData();


}
