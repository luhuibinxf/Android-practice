package com.example.administrator.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.administrator.myapplication.commlt.ResourceInAssetHander;
import com.example.administrator.myapplication.commlt.SimpleHttpServe;
import com.example.administrator.myapplication.commlt.WebConfiguraction;
import com.example.administrator.myapplication.commlt.upLoadImageHandler;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private WebConfiguraction webConfiguraction;
    private SimpleHttpServe serve;
    private ImageView mIv;
    private String tmpPath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webConfiguraction = new WebConfiguraction();
        webConfiguraction.maxParallels = 50;
        webConfiguraction.port = 8088;

        serve = new SimpleHttpServe(webConfiguraction);
        serve.reqisterResourceHandler(new ResourceInAssetHander());
        serve.reqisterResourceHandler(new upLoadImageHandler() {
            @Override
            protected void onImageLoaded(String tmpPath) {//重载的例子，很好的例子
                showLoad( tmpPath);
            }
        });

        serve.startAsync();
    }

    private void showLoad(final String tmpPath) {//需要的地方软件吗/*/*/*/*/*/**/*/*/*/*/*/
        runOnUiThread(new Runnable() {//这个方法很少有qi作用是？？？？
            @Override
            public void run() {
                mIv = (ImageView) findViewById(R.id.mian_iv);
                bitmap = BitmapFactory.decodeFile(tmpPath);
                mIv.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            serve.stopAsycn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
