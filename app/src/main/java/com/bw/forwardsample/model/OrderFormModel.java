package com.bw.forwardsample.model;

import com.bw.forwardsample.contract.IOrderFormContract;
import com.bw.forwardsample.model.bean.OrderFormBean;
import com.bw.forwardsample.util.NetUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderFormModel implements IOrderFormContract.IModel {


    @Override
    public void getOrderFormData(int page, int count, int status, IModelCallback iModelCallback) {
        NetUtil.getInstance().getApi().getOrderFormData("703",
                "1578624791267703", page, count, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderFormBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderFormBean orderFormBean) {
                        iModelCallback.onOrderFormSuccess(orderFormBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModelCallback.onOrderFormFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
