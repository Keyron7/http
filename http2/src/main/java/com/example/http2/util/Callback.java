package com.example.http2.util;

public interface Callback {
    void onResponse(String response);

    void onFailed(Exception e);
}
