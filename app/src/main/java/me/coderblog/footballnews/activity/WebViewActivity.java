package me.coderblog.footballnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.util.Utils;

public class WebViewActivity extends AppCompatActivity {
    //常量声明
    private static final String DETAIL = "资讯详情";

    //变量声明
    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        initToolbar(DETAIL);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            //点击Webview中的超链接时回调此函数，可在此函数中拦截超链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.reload(); //暂停视频声音
    }

    private void initToolbar(String name) {
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Utils.getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    //监听Toolbar按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://返回按钮
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //监听手机返回按钮，使得返回上一个页面不是退出当前Activity
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
