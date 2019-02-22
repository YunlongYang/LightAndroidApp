package online.heyworld.android.light.core.page.web;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import online.heyworld.android.light.R;
import online.heyworld.android.light.core.page.web.ext.WebViewSettingUtil;
import online.heyworld.android.light.core.page.web.ext.WebViewUtil;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.app.ui.ActivityUiHelper;

public class WebViewActivity extends BaseCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = findViewById(R.id.web_view);
        mProgressBar = findViewById(R.id.content_web_progress_bar);
        WebViewSettingUtil.basic(mWebView);
        WebViewUtil.setFeatures(mWebView,webViewFeatures -> {
            webViewFeatures.getWebChromeClientFeatures().bindProgressBar(mProgressBar);
            webViewFeatures.getWebChromeClientFeatures().syncTitle();
        });
        String url = getIntent().getDataString();
        if(TextUtils.isEmpty(url)){
            activityUiHelper.tip(ActivityUiHelper.PLACE_TOAST,"地址信息未找到",Toast.LENGTH_SHORT);
        }else{
            mWebView.loadUrl(getIntent().getDataString());
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebViewUtil.destroy(mWebView);
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else{
            finish();
        }
    }
}
