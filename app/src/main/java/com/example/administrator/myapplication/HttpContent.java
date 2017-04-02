package com.example.administrator.myapplication;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class HttpContent {


    private final HashMap<String, String> requseHeaders;

    private Socket underlySocket;

    public HttpContent() {
        requseHeaders = new HashMap<>();
    }

    public void setUnderlySocket(Socket socket) {
        this.underlySocket = socket;

    }

    public Socket getUnderlySocket() {
        return underlySocket;
    }

    public void addRequstHander(String headerName, String headerValue) {
        requseHeaders.put(headerName, headerValue);
    }

    public String getRequsestHanderValues(String headerName){
        return requseHeaders.get(headerName);
    }
}
