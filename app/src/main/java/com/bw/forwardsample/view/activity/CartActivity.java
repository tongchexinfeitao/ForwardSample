package com.bw.forwardsample.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
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
import butterknife.OnClick;

public class CartActivity extends BaseActivity<CartPresenter> implements ICartContract.IView {


    @BindView(R.id.lv)
    ExpandableListView mLv;
    @BindView(R.id.cb_cart_all_select)
    CheckBox mCbCartAllSelect;
    @BindView(R.id.tv_cart_total_price)
    TextView mTvCartTotalPrice;
    @BindView(R.id.btn_cart_pay)
    Button mBtnCartPay;
    private CartAdapter cartAdapter;

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

        cartAdapter = new CartAdapter(sellerList);
        cartAdapter.setOnCartClickListener(new CartAdapter.OnCartClickListener() {
            @Override
            public void onCartClick() {
                mTvCartTotalPrice.setText("总价:￥" + cartAdapter.calculateTotalPrice());
                mBtnCartPay.setText("去结算(" + cartAdapter.calculateTotalNum() + ")");
                mCbCartAllSelect.setChecked(cartAdapter.calculateIsAllChecked());
            }
        });

        mLv.setAdapter(cartAdapter);

        // TODO: 2020/1/6  遍历所有商家，全部展开
        for (int i = 0; i < sellerList.size(); i++) {
            mLv.expandGroup(i);
        }
    }

    @Override
    public void onCartFailure(Throwable throwable) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cb_cart_all_select, R.id.btn_cart_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_cart_all_select:
                // TODO: 2020/1/7 点击全选按钮 1、旧状态 2、置反 3、设置所有的商品状态为置反后的状态、刷新适配器
                if (cartAdapter != null) {
                    boolean b = cartAdapter.calculateIsAllChecked();
                    b = !b;
                    cartAdapter.changeAllCommodityStatus(b);
                }
                break;
            case R.id.btn_cart_pay:

                break;
        }
    }
}
