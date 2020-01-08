package com.bw.forwardsample.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.forwardsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddSubView extends LinearLayout {
    @BindView(R.id.tv_sub)
    TextView mTvSub;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_add)
    TextView mTvAdd;

    //商品数量
    private int num = 1;

    public MyAddSubView(Context context) {
        super(context);
    }

    public MyAddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.add_sub_view_layout, this);
        // TODO: 2020/1/8   必须手写
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sub:
                if (num > 1) {
                    num--;
                    mTvNum.setText("" + num);
                    if (onNumChangeListener != null) {
                        onNumChangeListener.onNumChange(num);
                    }
                } else {
                    Toast.makeText(getContext(), "不能再减了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_add:
                //数量+1
                num++;
                //中间数量文本重新赋值
                mTvNum.setText("" + num);
                //通知外部数量改变
                if (onNumChangeListener != null) {
                    onNumChangeListener.onNumChange(num);
                }
                break;
        }
    }


    //设置商品数量
    public void setNum(int num) {
        this.num = num;
        //中间文本赋值
        mTvNum.setText("" + num);
    }

    onNumChangeListener onNumChangeListener;

    public void setOnNumChangeListener(MyAddSubView.onNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

    public interface onNumChangeListener {
        void onNumChange(int num);
    }

}
