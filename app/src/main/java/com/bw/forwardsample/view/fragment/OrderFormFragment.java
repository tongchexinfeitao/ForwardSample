package com.bw.forwardsample.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.forwardsample.R;
import com.bw.forwardsample.base.BaseFragment;
import com.bw.forwardsample.contract.IOrderFormContract;
import com.bw.forwardsample.model.bean.OrderFormBean;
import com.bw.forwardsample.presenter.OrderFormPresenter;
import com.bw.forwardsample.view.adapter.OrderFormAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFormFragment extends BaseFragment<OrderFormPresenter> implements IOrderFormContract.IView {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    Unbinder unbinder;
    private int page = 1;
    private int count = 10;
    private int status = 0;

    @Override
    protected void initView(View inflate) {

    }

    @Override
    protected OrderFormPresenter providePresenter() {
        return new OrderFormPresenter();
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_order_form;
    }

    @Override
    protected void initData() {
        //必须写到initData中
        Bundle bundle = getArguments();
        if (bundle != null) {
            // TODO: 2020/1/9 获取到状态值
            status = bundle.getInt("key");
        }

        // TODO: 2020/1/9 根据状态值，请求接口
        mPresenter.getOrderFormData(page, count, status);
    }


    public static OrderFormFragment getInstance(int status) {
        OrderFormFragment orderFormFragment = new OrderFormFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key", status);
        orderFormFragment.setArguments(bundle);
        return orderFormFragment;
    }

    @Override
    public void onOrderFormSuccess(OrderFormBean orderFormBean) {

        //当前页面，所有的订单集合
        List<OrderFormBean.OrderListBean> orderList = orderFormBean.getOrderList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(linearLayoutManager);

        OrderFormAdapter orderFormAdapter = new OrderFormAdapter(orderList);
        mRecycler.setAdapter(orderFormAdapter);
    }

    @Override
    public void onOrderFormFailure(Throwable throwable) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
