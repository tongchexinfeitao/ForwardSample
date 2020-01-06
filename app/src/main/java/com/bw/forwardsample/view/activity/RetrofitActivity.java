package com.bw.forwardsample.view.activity;

import android.widget.Button;
import android.widget.Toast;

import com.bw.forwardsample.R;
import com.bw.forwardsample.base.BaseActivity;
import com.bw.forwardsample.base.BasePresenter;
import com.bw.forwardsample.model.bean.TestBean;
import com.bw.forwardsample.util.NetUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RetrofitActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_retrofit;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

//        //查询商品
//        NetUtil.getInstance().getApi().searchProduct(1, 10, "手机")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ProductBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(ProductBean productBean) {
//                        Toast.makeText(RetrofitActivity.this, "成功" + productBean.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(RetrofitActivity.this, "成功" + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//
//
//        //去登录
//        NetUtil.getInstance().getApi().login("15501186623", "123456")
//                //子线程联网
//                .subscribeOn(Schedulers.io())
//                //主线程更新UI
//                .observeOn(AndroidSchedulers.mainThread())
//                //传递一个 观察者对象，接受结果
//                .subscribe(new Observer<LoginBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(LoginBean loginBean) {
//                        Toast.makeText(RetrofitActivity.this, "成功" + loginBean.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(RetrofitActivity.this, "成功" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


        NetUtil.getInstance().getApi().testGet()
                //子线程联网
                .subscribeOn(Schedulers.io())
                //主线程更新UI
                .observeOn(AndroidSchedulers.mainThread())
                //传递一个 观察者对象，接受结果
                .subscribe(new Observer<TestBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TestBean testBean) {
                        Toast.makeText(RetrofitActivity.this, "成功" + testBean.getClassinfo().getClasstitle(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RetrofitActivity.this, "成功" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
