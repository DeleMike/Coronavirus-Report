package com.mikeinvents.coronavirusupdate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mikeinvents.coronavirusupdate.R;
import com.mikeinvents.coronavirusupdate.fragment.NewsFeedFragment;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private boolean isRedirected;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        swipeRefreshLayout = findViewById(R.id.refresh_web);
        webView = findViewById(R.id.web_view);
        Intent intent = getIntent();
        url = intent.getStringExtra(NewsFeedFragment.URL_TO_WEB);


        if(!(url.contains("https"))){
            url = url.replace("http","https");
        }

        final String urlStr = url;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startWebView(webView, urlStr);
            }
        });

        startWebView(webView, url);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(WebView webView, String url) {
        webView.setWebViewClient(new WebViewClient(){
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url){
                if(!(url.contains("https"))){
                    url = url.replace("http","https");
                }
                view.setWebChromeClient(new WebChromeClient());
                view.loadUrl(url);
                isRedirected = true;
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isRedirected = false;
            }

            @Override
            public void onLoadResource(WebView view, String url){
                if(!isRedirected){
                    if(progressDialog == null){
                        progressDialog = new ProgressDialog(WebActivity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    isRedirected = true;
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                        progressDialog = null;
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().supportZoom();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_web_refresh:
                startWebView(webView, url);
                break;
            case R.id.action_web_open:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.alert_dialog_string_web))
                        .setPositiveButton(getString(R.string.yes_web), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browseIntent);
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel_web), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();


                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }

    }

}
