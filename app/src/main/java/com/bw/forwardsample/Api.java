package com.bw.forwardsample;

import com.bw.forwardsample.bean.Bean;
import com.bw.forwardsample.bean.LoginBean;
import com.bw.forwardsample.bean.ProductBean;
import com.bw.forwardsample.bean.TestBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("small/user/v1/login")
    Observable<LoginBean> login(@Field("phone") String phone, @Field("pwd") String pwd);


    @GET("small/commodity/v1/findCommodityByKeyword")
    Observable<ProductBean> searchProduct(@Query("page") int page, @Query("count") int count, @Query("keyword") String keyword);

    @GET("http://blog.zhaoliang5156.cn/api/student/clazzstudent.json")
    Observable<TestBean> testGet();
}
