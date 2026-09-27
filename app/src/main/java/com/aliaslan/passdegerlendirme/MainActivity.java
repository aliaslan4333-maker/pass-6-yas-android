package com.aliaslan.passdegerlendirme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setTextZoom(100);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.evaluateJavascript(
                    "window.print = function(){ AndroidPrint.printPage(); };",
                    null
                );
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new PrintBridge(this), "AndroidPrint");
        webView.loadUrl("file:///android_asset/index.html");
    }

    public class PrintBridge {
        private final Context context;

        PrintBridge(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void printPage() {
            runOnUiThread(() -> {
                PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
                if (printManager == null) return;

                PrintDocumentAdapter adapter = webView.createPrintDocumentAdapter("PASS_6_Yas_Degerlendirme");
                printManager.print(
                    "PASS 6 Yaş Değerlendirme",
                    adapter,
                    new PrintAttributes.Builder().build()
                );
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("AndroidPrint");
            webView.destroy();
        }
        super.onDestroy();
    }
}
