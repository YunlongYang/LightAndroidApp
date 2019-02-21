package online.heyworld.android.light.core.page.web.ext;

import android.webkit.WebView;

public class WebViewSettingUtil {
    public static void basic(WebView webView) {
        WebView mywebView = webView;
        mywebView.getSettings().setJavaScriptEnabled(true);
        mywebView.getSettings().setDomStorageEnabled(false);// 打开本地缓存提供JS调用,至关重要
//        mywebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
//        mywebView.getSettings().setAllowFileAccess(true);
//        mywebView.getSettings().setAppCacheEnabled(true);
//        String appCachePath = mywebView.getContext().getCacheDir().getAbsolutePath();
//        mywebView.getSettings().setAppCachePath(appCachePath);
//        mywebView.getSettings().setDatabaseEnabled(true);

    }
}
