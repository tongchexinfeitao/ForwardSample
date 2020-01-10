package com.bw.forwardsample;

import com.bw.forwardsample.model.bean.CartBean;
import com.bw.forwardsample.model.bean.LoginBean;
import com.bw.forwardsample.model.bean.OrderFormBean;
import com.bw.forwardsample.model.bean.ProductBean;
import com.bw.forwardsample.model.bean.RegisterBean;
import com.bw.forwardsample.model.bean.TestBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("small/user/v1/login")
    Observable<LoginBean> login(@Field("phone") String phone, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("small/user/v1/register")
    Observable<RegisterBean> register(@Field("phone") String phone, @Field("pwd") String pwd);


    @GET("small/commodity/v1/findCommodityByKeyword")
    Observable<ProductBean> searchProduct(@Query("page") int page, @Query("count") int count, @Query("keyword") String keyword);

    @GET("http://blog.zhaoliang5156.cn/api/student/clazzstudent.json")
    Observable<TestBean> testGet();

    //查询购物车
    @GET("small/order/verify/v1/findShoppingCart")
    Observable<CartBean> getCartData(@Header("userId") String userId, @Header("sessionId") String sessionId);

    //查询购物车
    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<OrderFormBean> getOrderFormData(@Header("userId") String userId,
                                               @Header("sessionId") String sessionId,
                                               @Query("page") int page,
                                               @Query("count") int count,
                                               @Query("status") int status);
}
