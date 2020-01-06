package com.bw.forwardsample.contract;

import com.bw.forwardsample.model.bean.CartBean;

public interface ICartContract {
    interface IView {

        void onCartSuccess(CartBean cartBean);

        void onCartFailure(Throwable throwable);

    }

    interface IPresenter {
        void getCartData();
    }

    interface IModel {
        void getCartData(IModelCallback iModelCallback);

        interface IModelCallback {
            void onCartSuccess(CartBean cartBean);

            void onCartFailure(Throwable throwable);

        }
    }
}
