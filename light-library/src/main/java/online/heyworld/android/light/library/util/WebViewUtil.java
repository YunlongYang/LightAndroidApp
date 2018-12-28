package online.heyworld.android.light.library.util;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by admin on 2018/12/28.
 */

public class WebViewUtil {

    public static void setting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 设置支持javascript脚本
        webSettings.setJavaScriptEnabled(true);

        webSettings.setLoadsImagesAutomatically(true);

        // 设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(true);
        // 设置不出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        // 设置不可以缩放
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);

        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        // 自适应 屏幕大小界面
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);

        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
    }
}
