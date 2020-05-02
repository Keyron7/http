package com.example.http2.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Httputil {
    private static class Holder{
        private final static Httputil INSTANCE = new Httputil();

    }

    public static Httputil getInstance(){
        return Holder.INSTANCE;
    }

    private int corePoolSize;
    private int maxPoolSize;
    private long keepAliveTime = 30;
    private TimeUnit timeUnit = TimeUnit.MINUTES;
    private ThreadPoolExecutor executor;
    private Httputil() {
        corePoolSize = Runtime.getRuntime().availableProcessors()*2+1;
        maxPoolSize = 30;
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingDeque<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
    public void execute(final String url, final Callback callBack) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                start(url, callBack);
            }
        });
    }

    private void start(String url, Callback callBack) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            InputStream is = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            //获取响应文本
            callBack.onResponse(sb.toString());
            is.close();
            reader.close();
        } catch (IOException e) {
            callBack.onFailed(e);
        } finally {
            if (null != httpURLConnection) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
