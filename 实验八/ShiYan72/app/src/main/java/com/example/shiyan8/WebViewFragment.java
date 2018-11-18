package com.example.shiyan8;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.shiyan72.R;

/**
 * Created by lenovo on 2018/10/31.
 */
public class WebViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View contentView = inflater.inflate(R.layout.fragment_web_view, container, false);
        WebView webView = (WebView)contentView.findViewById(R.id.webview);
        //用于在本地显示而不是弹出一个新窗口
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://baidu.com");
        return contentView;
    }

}
