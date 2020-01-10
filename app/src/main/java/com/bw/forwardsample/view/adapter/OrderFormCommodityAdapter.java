package com.bw.forwardsample.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.forwardsample.R;
import com.bw.forwardsample.model.bean.OrderFormBean;
import com.bw.forwardsample.view.widget.MyAddSubView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFormCommodityAdapter extends RecyclerView.Adapter<OrderFormCommodityAdapter.MyViewHolder> {
    private List<OrderFormBean.OrderListBean.DetailListBean> detailList;

    private int orderStatus = 1;

    public OrderFormCommodityAdapter(int orderStatus, List<OrderFormBean.OrderListBean.DetailListBean> detailList) {
        this.orderStatus = orderStatus;
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_form_commodity, viewGroup, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //商品的bean类
        OrderFormBean.OrderListBean.DetailListBean detailListBean = detailList.get(i);

        //绑定数据
        myViewHolder.mProductTitleNameTv.setText(detailListBean.getCommodityName());
        myViewHolder.mProductPriceTv.setText("￥" + detailListBean.getCommodityPrice());
        String commodityPic = detailListBean.getCommodityPic();
        String[] split = commodityPic.split(",");
        Glide.with(myViewHolder.mProductIconIv).load(split[0]).into(myViewHolder.mProductIconIv);

        // TODO: 2020/1/10 当订单是待评价的状态的时候，需要显示 去评价 按钮
        if (orderStatus == 3) {
            myViewHolder.btnEvaluate.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.btnEvaluate.setVisibility(View.GONE);
        }
        myViewHolder.btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myViewHolder.itemView.getContext(), "点击了去评价", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_icon_iv)
        ImageView mProductIconIv;
        @BindView(R.id.product_title_name_tv)
        TextView mProductTitleNameTv;
        @BindView(R.id.product_price_tv)
        TextView mProductPriceTv;
        @BindView(R.id.btn_evaluate)
        Button btnEvaluate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
