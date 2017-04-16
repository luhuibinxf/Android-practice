package com.example.administrator.myapplication.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class StreamToolkit {
    /**
     * 读取每一段
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static final String readLine(InputStream in) throws IOException {
//        StringBuffer sb=new StringBuffer()
        StringBuilder sb = new StringBuilder();
        int cl = 0;
        int c2 = 0;
        while (c2 != -1 && !(cl == '\r' && c2 == '\n')) {
            cl = c2;
            c2 = in.read();
            sb.append((char) c2);
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public static byte[] readRawFromSteam(InputStream open) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//jdk基础API(内存)
        byte[] buffer = new byte[10240];
        int nreaded;

        while ((nreaded = open.read(buffer)) > 0) {
            bos.write(buffer, 0, nreaded);
        }
        return bos.toByteArray();
    }
}
