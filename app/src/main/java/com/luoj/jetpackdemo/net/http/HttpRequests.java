package com.luoj.jetpackdemo.net.http;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class HttpRequests {

    public static void getData() {
        HttpApi httpApi = createService();
        Observable<Object> data = httpApi.getData("", "");

    }

    public static void upload(File file) {
        HttpApi httpApi = createService();

        // 添加描述
        String descriptionString = "hello, 这是文件描述";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        httpApi.upload(description, body);
    }

    public static HttpApi createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(createOkHttp())
                .baseUrl("server_url")//必须斜线结尾，不然异常
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(HttpApi.class);
    }

    public static OkHttpClient createOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //例如心跳请求，过期的请求不应当重试
//        builder.retryOnConnectionFailure(false);

        return builder.build();
    }

}
