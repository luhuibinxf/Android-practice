package com.example.administrator.myapplication.commlt;

import android.content.Context;

import com.example.administrator.myapplication.commlt.HttpContent;
import com.example.administrator.myapplication.commlt.IResourcUrlHandler;
import com.example.administrator.myapplication.util.StreamToolkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class ResourceInAssetHander implements IResourcUrlHandler {
    private String acceptPrefix = "/static/";//规则
    private OutputStream outputStream;
    private PrintWriter printWriter;
    private Context context;
    private InputStream open;


    @Override
    public boolean accept(String url) {
        return url.startsWith(acceptPrefix);//主要这个方法
    }

    @Override
    public void handler(String url, HttpContent content) throws IOException {
//        outputStream = content.getUnderlySocket().getOutputStream();
//        printWriter = new PrintWriter(outputStream);
//        printWriter.println("http/1.1 200 ok");
//        printWriter.println();
//        printWriter.println("123455984916");
//        printWriter.flush();

        int startIndex = acceptPrefix.length();

        String assetsPath = url.substring(startIndex);
        open = context.getAssets().open(assetsPath);

        byte[] raw = StreamToolkit.readRawFromSteam(open);

        open.close();

        OutputStream output = content.getUnderlySocket().getOutputStream();

        PrintStream printStream = new PrintStream(output);
        printStream.println("http/1.1 200 ok");
        printStream.println("输出的二进制长度" + raw.length);
        if (assetsPath.endsWith(".html")) {
            printStream.println("Content_Type:text/html");
        } else if (assetsPath.endsWith(".js")) {
            printStream.println("Content_Type:text/js");
        } else if (assetsPath.endsWith(".css")) {
            printStream.println("Content_Type:text/css");
        } else if (assetsPath.endsWith(".jpg")) {
            printStream.println("Content_Type:text/jpg");
        } else if (assetsPath.endsWith(".html")) {
            printStream.println("Content_Type:text/html");
        }
        printStream.println();
        printStream.write(raw);
        printStream.close();
    }
}
