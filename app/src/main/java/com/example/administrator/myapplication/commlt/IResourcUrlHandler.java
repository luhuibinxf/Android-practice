package com.example.administrator.myapplication.commlt;

import com.example.administrator.myapplication.commlt.HttpContent;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public interface IResourcUrlHandler {

    boolean accept(String url);

    void handler(String url,HttpContent content) throws IOException;
}
