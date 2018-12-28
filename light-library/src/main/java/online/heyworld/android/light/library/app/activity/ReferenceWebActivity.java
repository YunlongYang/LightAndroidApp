package online.heyworld.android.light.library.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import online.heyworld.android.light.library.R;
import online.heyworld.android.light.library.util.WebViewUtil;

public class ReferenceWebActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        webView = findViewById(R.id.web_view);
        WebViewUtil.setting(webView);
        webView.loadUrl("https://cn.bing.com");
    }
}
