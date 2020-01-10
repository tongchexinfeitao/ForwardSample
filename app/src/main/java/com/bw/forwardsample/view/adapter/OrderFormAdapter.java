package com.bw.forwardsample.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.forwardsample.R;
import com.bw.forwardsample.model.bean.OrderFormBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFormAdapter extends RecyclerView.Adapter<OrderFormAdapter.MyViewHolder> {

    private List<OrderFormBean.OrderListBean> orderList;

    public OrderFormAdapter(List<OrderFormBean.OrderListBean> orderList) {

        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_form, viewGroup, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //拿到订单bean
        OrderFormBean.OrderListBean orderListBean = orderList.get(i);

        myViewHolder.mTvOrderId.setText("订单号 " + orderListBean.getOrderId());
        myViewHolder.mTvTime.setText("时间 " + orderListBean.getOrderTime());

        // TODO: 2020/1/9 这是给recyclerview绑定数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myViewHolder.itemView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myViewHolder.mRecyclerCommidity.setLayoutManager(linearLayoutManager);


        //这个订单中的所有商品集合
        List<OrderFormBean.OrderListBean.DetailListBean> detailList = orderListBean.getDetailList();

        OrderFormCommodityAdapter orderFormCommodityAdapter = new OrderFormCommodityAdapter(orderListBean.getOrderStatus(), detailList);
        myViewHolder.mRecyclerCommidity.setAdapter(orderFormCommodityAdapter);


        // TODO: 2020/1/10 给按钮绑定数据
        if (orderListBean.getOrderStatus() == 1) {
            myViewHolder.mBtnPayOrReceive.setVisibility(View.VISIBLE);
            myViewHolder.mBtnPayOrReceive.setText("去支付");
        } else if (orderListBean.getOrderStatus() == 2) {
            myViewHolder.mBtnPayOrReceive.setVisibility(View.VISIBLE);
            myViewHolder.mBtnPayOrReceive.setText("确认收货");
        } else {
            myViewHolder.mBtnPayOrReceive.setVisibility(View.GONE);
        }

        myViewHolder.mBtnPayOrReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderListBean.getOrderStatus() == 1) {
                    Toast.makeText(myViewHolder.itemView.getContext(), "点击了支付按钮", Toast.LENGTH_SHORT).show();
                } else if (orderListBean.getOrderStatus() == 2) {
                    Toast.makeText(myViewHolder.itemView.getContext(), "点击了确认收货按钮", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_id)
        TextView mTvOrderId;
        @BindView(R.id.recycler_commidity)
        RecyclerView mRecyclerCommidity;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.btn_pay_or_receive)
        Button mBtnPayOrReceive;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
