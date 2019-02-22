package online.heyworld.android.light.core.page.web.ext;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import online.heyworld.android.light.library.listener.ArgListener;

public class WebViewUtil {
    public static void setFeatures(WebView webView, ArgListener<WebViewFeatures> webViewFeaturesArgListener) {
        WebViewFeatures webViewFeatures = new WebViewFeatures();
        webViewFeaturesArgListener.on(webViewFeatures);
        webViewFeatures.accept(webView);
    }


    public static class WebViewFeatures{

        WebViewClientFeatures webViewClientFeatures = new WebViewClientFeatures();
        WebChromeClientFeatures webChromeClientFeatures = new WebChromeClientFeatures();

        public WebViewClient getWebViewClient() {
            return webViewClientFeatures.webViewClient;
        }

        public WebChromeClient getWebChromeClient() {
            return webChromeClientFeatures.webChromeClient;
        }

        public WebViewClientFeatures getWebViewClientFeatures() {
            return webViewClientFeatures;
        }

        public WebChromeClientFeatures getWebChromeClientFeatures() {
            return webChromeClientFeatures;
        }

        void accept(WebView webView){
            webView.setWebViewClient(getWebViewClient());
            webView.setWebChromeClient(getWebChromeClient());
        }
    }

    public static class WebViewClientFeatures{
        final WebViewClient webViewClient = new WebViewClient(){

        };
    }

    public static class WebChromeClientFeatures{
        ProgressBar progressBar;
        boolean syncTitleEnable = false;
        final WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(progressBar.getVisibility() == View.GONE && newProgress<100 ){
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
                if(newProgress>=100){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(syncTitleEnable){
                    if(view.getContext() instanceof Activity){
                       ((Activity) view.getContext()).setTitle(title);
                    }
                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
        };

        public void bindProgressBar(ProgressBar progressBar){
            this.progressBar = progressBar;
            progressBar.setVisibility(View.GONE);
            progressBar.setMax(100);
        }

        public void syncTitle() {
            syncTitleEnable = true;
        }
    }

    public static void destroy(WebView webView) {
        ViewParent parent = webView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(webView);
        }
        webView.stopLoading();
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearHistory();
        webView.clearView();
        webView.removeAllViews();
        webView.destroy();
    }
}
