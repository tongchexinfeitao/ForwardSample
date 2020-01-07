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

        //当前的商家bean
        CartBean.ResultBean sellerBean = sellerList.get(groupPosition);
        //绑定商家名字
        sellerViewHolder.mSellerNameTv.setText(sellerBean.getCategoryName());

        //拿到所有商品
        List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = sellerBean.getShoppingCartList();

        //假设商家是选中状态
        boolean sellerIsChecked = true;

        //遍历所有商品，计算当前商家选中状态
        for (int i = 0; i < shoppingCartList.size(); i++) {
            CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(i);
            //只要有一个商品没选中，商家的状态就应该是false
            if (shoppingCartListBean.isChecked() == false) {
                sellerIsChecked = false;
                //终止循环
                break;
            }
        }
        //给商家的checkbox赋值
        sellerViewHolder.mSellerCb.setChecked(sellerIsChecked);

        //给商家的checkbox设置点击监听
        boolean finalSellerIsChecked = sellerIsChecked;
        sellerViewHolder.mSellerCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2020/1/7 1、 拿到商家的状态  2、状态置反 3、将置反后的状态设置给所有的商品 4、刷新适配器
                //商家状态
                boolean currentIsChecked = finalSellerIsChecked;
                //置反
                currentIsChecked = !currentIsChecked;
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

        //当前位置的商品bean
        CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = sellerList.get(groupPosition).getShoppingCartList().get(childPosition);

        //绑定数据
        commodityHolder.mProductTitleNameTv.setText(shoppingCartListBean.getCommodityName());
        commodityHolder.mProductPriceTv.setText("￥" + shoppingCartListBean.getPrice());
        Glide.with(commodityHolder.mProductIconIv).load(shoppingCartListBean.getPic()).into(commodityHolder.mProductIconIv);
        //给checkbox赋值
        commodityHolder.mChildCb.setChecked(shoppingCartListBean.isChecked());
        //给checkbox设置点击事件
        commodityHolder.mChildCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2020/1/7 注意：点击监听中，不允许操作checkbox
                // TODO: 2020/1/7 1、获取bean类中的状态值 2、置反  3、设置给bean 4、然后刷新适配器
                boolean checked = shoppingCartListBean.isChecked();
                shoppingCartListBean.setChecked(!checked);
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
}
