package com.bw.forwardsample.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bw.forwardsample.R;
import com.bw.forwardsample.base.BaseActivity;
import com.bw.forwardsample.base.BasePresenter;
import com.bw.forwardsample.view.fragment.OrderFormFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFormActivity extends BaseActivity {


    @BindView(R.id.vp)
    ViewPager mVp;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void initData() {
        OrderFormFragment all = OrderFormFragment.getInstance(0);
        OrderFormFragment waitPay = OrderFormFragment.getInstance(1);
        OrderFormFragment waitReceive = OrderFormFragment.getInstance(2);
        OrderFormFragment waitEvaluate = OrderFormFragment.getInstance(3);
        OrderFormFragment complete = OrderFormFragment.getInstance(9);
        fragmentList.add(all);
        fragmentList.add(waitPay);
        fragmentList.add(waitReceive);
        fragmentList.add(waitEvaluate);
        fragmentList.add(complete);

        //给viewpager设置适配器
        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_order_form;
    }
}
