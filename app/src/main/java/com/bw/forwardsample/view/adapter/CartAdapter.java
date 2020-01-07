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

        // TODO: 2020/1/7 遍历当前商家下的所有商品，看看是否是全选状态
        //拿到第 groupPosition 位置的商家
        CartBean.ResultBean sellerBean = sellerList.get(groupPosition);
        //拿到当前商家下的所有商品
        List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = sellerBean.getShoppingCartList();
        //默认商家是选中的
        boolean sellerIsChecked = true;
        //遍历所有商品，如果有一个没选中，说明商家不应该被选中
        for (int i = 0; i < shoppingCartList.size(); i++) {
            //拿到第 i 个商品
            CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(i);
            //只要发现一个商品不是选中状态，那么就商家是未选中状态，然后直接结束遍历
            if (shoppingCartListBean.isChecked() == false) {
                sellerIsChecked = false;
                break;
            }
        }
        // TODO: 2020/1/7 给商家的checkbox绑定数据
        sellerViewHolder.mSellerCb.setChecked(sellerIsChecked);

        // TODO: 2020/1/7 点击商家的checkbox
        boolean finalSellerIsChecked = sellerIsChecked;
        sellerViewHolder.mSellerCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取点击前商家的状态
                boolean currentIsChecked = finalSellerIsChecked;

                //取反商家的状态
                currentIsChecked = !currentIsChecked;

                //遍历当前商家的所有商品，将取反后的商家的状态设置给商品
                for (int i = 0; i < shoppingCartList.size(); i++) {
                    shoppingCartList.get(i).setChecked(currentIsChecked);
                }
                notifyDataSetChanged();
            }
        });

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
        //商品名字绑定数据
        commodityHolder.mProductTitleNameTv.setText(shoppingCartListBean.getCommodityName());
        //商品价格绑定数据
        commodityHolder.mProductPriceTv.setText("￥" + shoppingCartListBean.getPrice());
        //商品图片绑定数据
        Glide.with(commodityHolder.mProductIconIv)
                .load(shoppingCartListBean.getPic())
                .into(commodityHolder.mProductIconIv);

        // TODO: 2020/1/7   商品checkbox绑定数据
        commodityHolder.mChildCb.setChecked(shoppingCartListBean.isChecked());

        // TODO: 2020/1/7 这是商品checkbox的点击监听吧
        commodityHolder.mChildCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartListBean.setChecked(!shoppingCartListBean.isChecked());
                notifyDataSetChanged();
            }
        });

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


    //计算总价
    public float calculateTotalPrice() {
        float totalPrice = 200;
        //遍历每一个商家
        for (int i = 0; i < sellerList.size(); i++) {
            //获取到第 i 个商家
            CartBean.ResultBean sellerBean = sellerList.get(i);
            //拿到第 i 个商家下的所有商品集合
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = sellerBean.getShoppingCartList();
            //遍历第 i 个商家下的所有的商品
            for (int j = 0; j < shoppingCartList.size(); j++) {
                //拿到第 j 个商品
                CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(j);
                //如果当前商品是选中状态，才去计算总价
                if (shoppingCartListBean.isChecked()) {
                    //总价累计
                    totalPrice += shoppingCartListBean.getPrice() * shoppingCartListBean.getCount();
                }
            }
        }
        return totalPrice;
    }


    //计算总价
    public float calculateTotalNum() {
        float totalNum = 200;
        //遍历每一个商家
        for (int i = 0; i < sellerList.size(); i++) {
            //获取到第 i 个商家
            CartBean.ResultBean sellerBean = sellerList.get(i);
            //拿到第 i 个商家下的所有商品集合
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = sellerBean.getShoppingCartList();
            //遍历第 i 个商家下的所有的商品
            for (int j = 0; j < shoppingCartList.size(); j++) {
                //拿到第 j 个商品
                CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(j);
                //如果当前商品是选中状态，才去计算总价
                if (shoppingCartListBean.isChecked()) {
                    //总价累计
                    totalNum += shoppingCartListBean.getCount();
                }
            }
        }
        return totalNum;
    }
}
