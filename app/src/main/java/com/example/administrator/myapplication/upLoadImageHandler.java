package com.example.administrator.myapplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
// TODO: 2017/4/2 0002 注释太少   以后看的时候需要多多思考
/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class upLoadImageHandler implements IResourcUrlHandler {
    private String acceptPrefix = "/upload_image/";//规则
    private OutputStream outputStream;
    private PrintWriter printWriter;
    private FileOutputStream fileOutputStream;

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
//        printWriter.println("我是图片的URL");
//        printWriter.flush();

        long totalLength = Long.parseLong(content.getRequsestHanderValues("Content-length"));
        String tmpPath = "/mnt/sacard/test_upLoad.jpg";
        fileOutputStream = new FileOutputStream(tmpPath);
        InputStream in = content.getUnderlySocket().getInputStream();
        byte[] buffer = new byte[10240];
        int nReaded = 0;
        long nlefftLength = totalLength;
        while ((nReaded = in.read(buffer)) > 0 && nlefftLength > 0) {

            fileOutputStream.write(buffer, 0, nReaded);
            nlefftLength -= nReaded;
        }
        fileOutputStream.close();


        outputStream = content.getUnderlySocket().getOutputStream();
        printWriter = new PrintWriter(outputStream);
        printWriter.println("http/1.1 200 ok");
        printWriter.println();
//        printWriter.println("我是图片的URL");
//        printWriter.flush();

        onImageLoaded(tmpPath);
    }

    /**
     * 为什么是受保护的呢
     * @param tmpPath
     */
    protected void onImageLoaded(String tmpPath) {

    }
}
