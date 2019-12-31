package com.bw.forwardsample.util;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetUtil {
    //成员变量
    private static NetUtil netUtil;

    private Handler handler;
    private OkHttpClient okHttpClient;

    //私有构造器
    private NetUtil() {
        handler = new Handler();

        // TODO: 2019/12/30  1、创建http日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // TODO: 2019/12/30  2、设置拦截器打印的级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //1、构造一个  OkHttpClient 对象
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                // TODO: 2019/12/30   3、将拦截器添加到 OkHttpClient.Builder中
                .addInterceptor(httpLoggingInterceptor)
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

    public void getJsonPost(String httpUrl, Map<String, String> paramsMap, MyCallBack myCallback) {
        // TODO: 2019/12/28 1、构造一个 FormBody.Builder 对象
        FormBody.Builder builder = new FormBody.Builder();

        // TODO: 2019/12/28  2、先遍历map，然后在循环中给 builder 添加参数
        //增强for循环的格式     for(key的类型  key的名字 冒号  key的集合){}
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }

        // TODO: 2019/12/28  3、在循环外边，build 出 FormBody对象
        FormBody formBody = builder.build();

        //构造Request
        Request request = new Request.Builder()
                // TODO: 2019/12/28  4、请求方式变成 post，将 FormBody 对象传入
                .post(formBody)
                .url(httpUrl)
                .build();

        //构造一个可执行的Call请求
        Call call = okHttpClient.newCall(request);

        //异步执行  enqueue， 成功和失败是在子线程,子线程不能更新UI,所有要切换到主线程 handler.post
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

    public interface MyCallBack {
        void onGetJson(String json);

        void onError(Throwable throwable);
    }
}
