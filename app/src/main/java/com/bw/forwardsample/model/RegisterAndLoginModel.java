package com.bw.forwardsample.model;

import com.bw.forwardsample.contract.IRegisterAndLoginContract;
import com.bw.forwardsample.model.bean.LoginBean;
import com.bw.forwardsample.model.bean.RegisterBean;
import com.bw.forwardsample.util.NetUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterAndLoginModel implements IRegisterAndLoginContract.IModel {
    @Override
    public void register(String phone, String pwd, IModelCallback iModelCallback) {
        NetUtil.getInstance().getApi().register(phone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        iModelCallback.onRegisterSuccess(registerBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModelCallback.onRegisterFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void login(String phone, String pwd, IModelCallback iModelCallback) {
        NetUtil.getInstance().getApi().login(phone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        iModelCallback.onLoginSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModelCallback.onLoginFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
