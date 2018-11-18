package com.example.shiyanba;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by lenovo on 2018/10/28.
 */
public class TestFragment extends Fragment {
    private int layoutID;
    public void setLayoutID(int layoutID){
        this.layoutID = layoutID;
    }

    /**
     * 返回一个view，使用layoutInflater将相应的layout
     * 载入形成相应的view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(layoutID,null);
        if(R.layout.web_fragment == layoutID){
            WebView webView = (WebView) view.findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("https://baidu.com");
        }
        return view;
    }
}
