package online.heyworld.android.light.core.page.web;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import online.heyworld.android.light.R;
import online.heyworld.android.light.core.page.web.ext.WebViewSettingUtil;
import online.heyworld.android.light.core.page.web.ext.WebViewUtil;

public class WebViewActivity extends AppCompatActivity {

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
        });
        mWebView.loadUrl("https://html5test.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
