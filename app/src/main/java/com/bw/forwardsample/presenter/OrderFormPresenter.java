package com.bw.forwardsample.presenter;

import android.support.v7.widget.LinearLayoutCompat;

import com.bw.forwardsample.base.BasePresenter;
import com.bw.forwardsample.contract.IOrderFormContract;
import com.bw.forwardsample.model.OrderFormModel;
import com.bw.forwardsample.model.bean.OrderFormBean;

public class OrderFormPresenter extends BasePresenter<IOrderFormContract.IView> implements IOrderFormContract.IPresenter {

    private OrderFormModel orderFormModel;

    @Override
    protected void initModel() {
        orderFormModel = new OrderFormModel();
    }


    @Override
    public void getOrderFormData(int page, int count, int status) {
        orderFormModel.getOrderFormData(page, count, status, new IOrderFormContract.IModel.IModelCallback() {
            @Override
            public void onOrderFormSuccess(OrderFormBean orderFormBean) {
                view.onOrderFormSuccess(orderFormBean);
            }

            @Override
            public void onOrderFormFailure(Throwable throwable) {
                view.onOrderFormFailure(throwable);
            }
        });
    }
}
