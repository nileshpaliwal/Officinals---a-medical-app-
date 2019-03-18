package in.officinal.officinals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by mypc on 3/30/2018.
 */

public class Generic_Activity extends AppCompatActivity {


    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic);


        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.emedexpert.com/lists/brand-generic.shtml");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(Generic_Activity.this,Home.class);
        startActivity(intent);
    }*/
}
