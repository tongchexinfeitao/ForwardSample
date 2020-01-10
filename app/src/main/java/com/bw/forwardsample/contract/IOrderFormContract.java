package com.bw.forwardsample.contract;

import com.bw.forwardsample.model.bean.CartBean;
import com.bw.forwardsample.model.bean.OrderFormBean;

public interface IOrderFormContract {
    interface IView {

        void onOrderFormSuccess(OrderFormBean orderFormBean);

        void onOrderFormFailure(Throwable throwable);

    }

    interface IPresenter {
        void getOrderFormData(int page, int count, int status);
    }

    interface IModel {
        void getOrderFormData(int page, int count, int status, IModelCallback iModelCallback);

        interface IModelCallback {
            void onOrderFormSuccess(OrderFormBean orderFormBean);

            void onOrderFormFailure(Throwable throwable);

        }
    }
}
