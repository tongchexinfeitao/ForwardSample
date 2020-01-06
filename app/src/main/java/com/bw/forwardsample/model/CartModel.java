package com.bw.forwardsample.model;

import com.bw.forwardsample.contract.ICartContract;
import com.bw.forwardsample.model.bean.CartBean;
import com.bw.forwardsample.util.NetUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartModel implements ICartContract.IModel {
    @Override
    public void getCartData(IModelCallback iModelCallback) {
        NetUtil.getInstance().getApi().getCartData("703", "1578294128973703")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CartBean cartBean) {
                        iModelCallback.onCartSuccess(cartBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModelCallback.onCartFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
