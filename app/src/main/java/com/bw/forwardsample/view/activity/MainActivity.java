package com.bw.forwardsample.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.forwardsample.util.NetUtil;
import com.bw.forwardsample.R;
import com.bw.forwardsample.base.BaseActivity;
import com.bw.forwardsample.base.BasePresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tv_qr_content)
    TextView mTvQrContent;
    @BindView(R.id.btn_qr_camera)
    Button mBtnQrCamera;
    @BindView(R.id.btn_qr_photos)
    Button mBtnQrPhotos;
    @BindView(R.id.iv_qr_picture)
    ImageView mIvQrPicture;

    @Override
    protected void initData() {



        Map<String, String> map = new HashMap<>();
        map.put("phone", "15501186623");
        map.put("pwd", "123456");

        NetUtil.getInstance().getJsonPost("http://172.17.8.100/small/user/v1/login", map, new NetUtil.MyCallBack() {
            @Override
            public void onGetJson(String json) {

                Toast.makeText(MainActivity.this, "成功" + json, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(MainActivity.this, "失败" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void initView() {
        mIvQrPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CodeUtils.analyzeByImageView(mIvQrPicture, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        Toast.makeText(MainActivity.this, "成功 " + result, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
    }

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.tv_qr_content, R.id.btn_qr_camera, R.id.btn_qr_photos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //点击文本，生成二维码
            case R.id.tv_qr_content:
                String s = mTvQrContent.getText().toString();
                //生成了一个二维码      string -> 二维码
//                Bitmap image = CodeUtils.createImage(s, 400, 400, null);
                Bitmap image = CodeUtils.createImage(s, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                mIvQrPicture.setImageBitmap(image);
                break;
            //点击相机
            case R.id.btn_qr_camera:
                CodeUtils.analyzeByCamera(this);
                break;
            //点击相册
            case R.id.btn_qr_photos:
                CodeUtils.analyzeByPhotos(this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO: 2019/12/27  接受结果   CodeUtils.onActivityResult ，相机和相册必须重写  onActivityResult
        CodeUtils.onActivityResult(this, requestCode, resultCode, data, new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Toast.makeText(MainActivity.this, "成功 " + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnalyzeFailed() {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
