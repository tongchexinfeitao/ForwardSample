package com.bw.forwardsample;


import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetUtilssss {
    private static NetUtilssss netUtilssss;
    private final Handler handler;
    private final OkHttpClient okHttpClient;

    private NetUtilssss() {
        handler = new Handler();
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public static NetUtilssss getInstance() {
        if (netUtilssss == null) {
            synchronized (NetUtilssss.class) {
                if (netUtilssss == null) {
                    netUtilssss = new NetUtilssss();
                }
            }
        }
        return netUtilssss;
    }

    public void getJsonGet(String httpUrl, MyCallback myCallback) {
        Request request = new Request.Builder()
                .get()
                .url(httpUrl)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallback.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    String string = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallback.onGetJson(string);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallback.onError(new Exception("请求失败"));
                        }
                    });
                }
            }
        });
    }


    public interface MyCallback {
        void onGetJson(String json);

        void onError(Throwable throwable);
    }

}



