package com.bw.forwardsample.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.forwardsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    @BindView(R.id.tv_qr_content)
    TextView mTvQrContent;
    @BindView(R.id.btn_qr_camera)
    Button mBtnQrCamera;
    @BindView(R.id.btn_qr_photos)
    Button mBtnQrPhotos;
    @BindView(R.id.iv_qr_picture)
    ImageView mIvQrPicture;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_qr_content)
        TextView mTvQrContent;
        @BindView(R.id.btn_qr_camera)
        Button mBtnQrCamera;
        @BindView(R.id.btn_qr_photos)
        Button mBtnQrPhotos;
        @BindView(R.id.iv_qr_picture)
        ImageView mIvQrPicture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
