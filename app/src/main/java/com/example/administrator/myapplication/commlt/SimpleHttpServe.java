package com.example.administrator.myapplication.commlt;

import android.util.Log;

import com.example.administrator.myapplication.util.StreamToolkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class SimpleHttpServe {

    private final WebConfiguraction configuraction;
    private final ExecutorService threadPoot;
    private boolean isEnable;//服务启动标识

    private ServerSocket socket;

    private Set<IResourcUrlHandler> resource;

    public SimpleHttpServe(WebConfiguraction configuraction) {
        this.configuraction = configuraction;
        threadPoot = Executors.newCachedThreadPool();//结束之后不会立即把线程销毁
        resource = new HashSet<>();
    }

    /**
     * 开始服务
     */
    public void startAsync() {
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();//启动线程去异步监听
            }
        }).start();
    }


    /**
     * 停止服务
     */
    public void stopAsycn() throws IOException {//为什么会抛异常啊
        if (!isEnable) return;

        isEnable = false;

        socket.close();//会有什么后果
        socket = null;

    }

    private void doProcSync() {
        try {

            InetSocketAddress inetSocketAddress = new InetSocketAddress(configuraction.port);

            socket = new ServerSocket();
            socket.bind(inetSocketAddress);
            while (isEnable) {
                final Socket remotPeer = socket.accept();
                threadPoot.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("通信地址是", remotPeer.getRemoteSocketAddress().toString() + "");
                        onAcceptRemotePeer(remotPeer);
                    }
                });
            }
        } catch (IOException e) {
            //在哪里会走到这呢？？？？
            e.printStackTrace();
        }


    }

    /**
     * 后续操作
     *
     * @param remotPeer
     */
    private void onAcceptRemotePeer(Socket remotPeer) {
//        try {
//            remotPeer.getOutputStream().write("1213213213213213213".getBytes());
//        } catch (IOException e) {
//            Log.e("", e.getMessage().toString());
//            e.printStackTrace();
//        }
        try {

            HttpContent content = new HttpContent();
            content.setUnderlySocket(remotPeer);

            InputStream in = remotPeer.getInputStream();
            String headLine = null;

            String URl = StreamToolkit.readLine(in).split(" ")[1];//Url相对路径

            while ((headLine = StreamToolkit.readLine(in)) != null) {
                if (headLine.equals("\r\n")) {
                    break;
                }
                String[] pait = headLine.split(": ");
                content.addRequstHander(pait[0], pait[1]);
                Log.e("HAHAHA", headLine.toString() + "");
            }

            for (IResourcUrlHandler halder : resource) {
                if (!halder.accept(URl))
                    continue;

                halder.handler(URl,content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reqisterResourceHandler(IResourcUrlHandler handler) {
        resource.add(handler);
    }
}
