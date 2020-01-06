package com.bw.forwardsample.view.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.bw.forwardsample.R;
import com.bw.forwardsample.base.BaseActivity;
import com.bw.forwardsample.contract.ICartContract;
import com.bw.forwardsample.model.bean.CartBean;
import com.bw.forwardsample.presenter.CartPresenter;
import com.bw.forwardsample.view.adapter.CartAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends BaseActivity<CartPresenter> implements ICartContract.IView {


    @BindView(R.id.lv)
    ExpandableListView mLv;

    @Override
    protected void initData() {
        // TODO: 2020/1/6 请求数据的
        mPresenter.getCartData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected CartPresenter providePresenter() {
        return new CartPresenter();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public void onCartSuccess(CartBean cartBean) {

        //购物车所有的数据  商家的集合
        List<CartBean.ResultBean> sellerList = cartBean.getResult();

        CartAdapter cartAdapter = new CartAdapter(sellerList);
        mLv.setAdapter(cartAdapter);

        // TODO: 2020/1/6  遍历所有商家，全部展开
        for (int i = 0; i < sellerList.size(); i++) {
            mLv.expandGroup(i);
        }
    }

    @Override
    public void onCartFailure(Throwable throwable) {

    }
}
