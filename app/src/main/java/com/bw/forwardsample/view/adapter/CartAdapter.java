package com.bw.forwardsample.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.forwardsample.R;
import com.bw.forwardsample.model.bean.CartBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends BaseExpandableListAdapter {
    //商家集合
    private List<CartBean.ResultBean> sellerList;

    public CartAdapter(List<CartBean.ResultBean> sellerList) {
        this.sellerList = sellerList;
    }

    //商家数量
    @Override
    public int getGroupCount() {
        return sellerList.size();
    }

    //这是第  groupPosition  个 商家，下对应的商品集合的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return sellerList.get(groupPosition).getShoppingCartList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //商家的布局
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SellerViewHolder sellerViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_cart_parent, null);
            sellerViewHolder = new SellerViewHolder(convertView);
            convertView.setTag(sellerViewHolder);
        } else {
            sellerViewHolder = (SellerViewHolder) convertView.getTag();
        }

        //拿到商家数据
        CartBean.ResultBean resultBean = sellerList.get(groupPosition);

        //绑定数据
        sellerViewHolder.mSellerNameTv.setText(resultBean.getCategoryName());

        return convertView;
    }

    //商品布局
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CommodityHolder commodityHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_cart_child, null);
            commodityHolder = new CommodityHolder(convertView);
            convertView.setTag(commodityHolder);
        } else {
            commodityHolder = (CommodityHolder) convertView.getTag();
        }

        //拿到商品数据
        CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = sellerList.get(groupPosition).getShoppingCartList().get(childPosition);
        commodityHolder.mProductTitleNameTv.setText(shoppingCartListBean.getCommodityName());
        commodityHolder.mProductPriceTv.setText("￥" + shoppingCartListBean.getPrice());
        Glide.with(commodityHolder.mProductIconIv)
                .load(shoppingCartListBean.getPic())
                .into(commodityHolder.mProductIconIv);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //商家的holder
    static class SellerViewHolder {
        @BindView(R.id.seller_cb)
        CheckBox mSellerCb;
        @BindView(R.id.seller_name_tv)
        TextView mSellerNameTv;

        SellerViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //商品的holder
    static class CommodityHolder {
        @BindView(R.id.child_cb)
        CheckBox mChildCb;
        @BindView(R.id.product_icon_iv)
        ImageView mProductIconIv;
        @BindView(R.id.product_title_name_tv)
        TextView mProductTitleNameTv;
        @BindView(R.id.product_price_tv)
        TextView mProductPriceTv;

        CommodityHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
