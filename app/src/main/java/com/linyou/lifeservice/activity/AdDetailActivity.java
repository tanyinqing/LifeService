package com.linyou.lifeservice.activity;

import android.graphics.Bitmap;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.entity.Advertisement;

/**
 * 用户协议
 * @author 志强
 *
 */
@ContentView(R.layout.activity_addetail)
public class AdDetailActivity extends TitleBarActivity
{
    private static final String TAG = "AdDetailActivity";

    @ViewInject(R.id.webView)
    private WebView webView;

    @Override
    void RightButtonClicked() {

    }

    @Override
    void LeftButtonClicked() {
        finish();
    }
    
    @Override
    void setListeners()
    {
    }
    
    Advertisement advertisement;
    
    @Override
    void initDatas()
    {
        advertisement = (Advertisement)getIntent().getSerializableExtra(Constant.INTENT_ADS);
        setButtonLeft("返回");
        setTitle("广告");

        webView.loadDataWithBaseURL("", advertisement.getContent(), "text/html", "utf-8", "");
        webView.setWebViewClient(new WebViewClient(){
            
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.e(TAG, "onPageFinished");
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.e(TAG, "onPageStarted");
                super.onPageStarted(view, url, favicon);
            }
            
        });
    }
    
}
