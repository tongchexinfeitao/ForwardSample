package com.bw.forwardsample.presenter;

import com.bw.forwardsample.base.BasePresenter;
import com.bw.forwardsample.contract.ICartContract;
import com.bw.forwardsample.model.CartModel;
import com.bw.forwardsample.model.bean.CartBean;

public class CartPresenter extends BasePresenter<ICartContract.IView> implements ICartContract.IPresenter {

    private CartModel cartModel;

    @Override
    protected void initModel() {
        cartModel = new CartModel();
    }

    @Override
    public void getCartData() {
        cartModel.getCartData(new ICartContract.IModel.IModelCallback() {
            @Override
            public void onCartSuccess(CartBean cartBean) {
                view.onCartSuccess(cartBean);
            }

            @Override
            public void onCartFailure(Throwable throwable) {
                view.onCartFailure(throwable);
            }
        });
    }
}
