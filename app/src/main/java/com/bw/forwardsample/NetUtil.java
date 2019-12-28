package com.bw.forwardsample;

import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetUtil {
    //成员变量
    private static NetUtil netUtil;

    private Handler handler;
    private OkHttpClient okHttpClient;

    //私有构造器
    private NetUtil() {
        handler = new Handler();
        //1、构造一个  OkHttpClient 对象
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    //geti 双重校验锁
    public static NetUtil getInstance() {
        if (netUtil == null) {
            synchronized (NetUtil.class) {
                if (netUtil == null) {
                    netUtil = new NetUtil();
                }
            }

        }
        return netUtil;
    }

    public void getJsonGet(String httpUrl, MyCallBack myCallBack) {
        //2、构造一个  Request 请求对象
        Request request = new Request.Builder()
                .get()
                .url(httpUrl)
                .build();

        //3、构造一个可执行的 Call 对象
        Call call = okHttpClient.newCall(request);


        //同步执行，必须配合Thread
        // Response response = call.execute();

        //4、异步执行  enqueue  (这个 enqueue 方法会自动帮我切换到子线程)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    String string = response.body().string();

                    // TODO: 2019/12/27 切换到主线程
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallBack.onGetJson(string);
                        }
                    });

                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallBack.onError(new Exception("请求失败"));
                        }
                    });

                }
            }
        });
    }


    public interface MyCallBack {
        void onGetJson(String json);

        void onError(Throwable throwable);
    }
}
