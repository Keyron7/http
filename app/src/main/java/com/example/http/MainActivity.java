package com.example.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.http2.util.Callback;
import com.example.http2.util.Httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
        TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button  = findViewById(R.id.send_request);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Httputil.getInstance().execute("http://47.99.165.194/album/newest", new Callback() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("!!!!!!!","?"+response);
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });
            }
            });
        }
}
