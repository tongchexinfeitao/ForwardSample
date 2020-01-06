package com.bw.forwardsample.view.activity;

import android.widget.Button;
import android.widget.Toast;

import com.bw.forwardsample.R;
import com.bw.forwardsample.base.BaseActivity;
import com.bw.forwardsample.base.BasePresenter;
import com.bw.forwardsample.model.bean.Bean;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发送一行代码直接发
 *
 *
 *
 * <p>
 * 接受四个必须：
 * 1、必须注册  register
 * 2、必须订阅  @Subscribe
 * 3、接受的方法必须是 public
 * 4、接受的方法的  参数类型 必须是 发送的类型
 */
public class SecondActivity extends BaseActivity {

    @BindView(R.id.btn_send)
    Button mBtnSend;
    @BindView(R.id.btn_send_event_bean)
    Button mBtnSendEventBean;

    @Override
    protected void initData() {
        CodeUtils.init(this);
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
        return R.layout.activity_second;
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        EventBus.getDefault().post(new Bean("wang", 28));
    }

    @OnClick(R.id.btn_send_event_bean)
    public void onViewClicked1() {
        EventBus.getDefault().post("ddddddd");
    }


    @Override
    protected void onStart() {
        super.onStart();
        // TODO: 2019/12/28 注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //todo 解绑
        EventBus.getDefault().unregister(this);
    }


    /**
     * 这是方法是用来接受的
     * 注册、订阅、公共、类型统一
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetXxxBean(Bean bean) {
        Toast.makeText(SecondActivity.this, "接受成功 " + bean.getName(), Toast.LENGTH_SHORT).show();
    }


    @Subscribe
    public void onGetString(String string) {
        Toast.makeText(SecondActivity.this, "接受成功 " + string, Toast.LENGTH_SHORT).show();

    }

}
