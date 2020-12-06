package top.wangdf.fxbasis.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.gson.Gson;

import top.wangdf.fxbasis.R;
import top.wangdf.fxbasis.entity.PureWebView;

public class PureH5Activity extends AppCompatActivity {

    private PureWebView webViewData;

    private RelativeLayout titleBarLayout;

    private TextView textView;

    private ImageButton imageButton;

    private WebView webView;

    private LinearLayout pureAllLayout;

    private static final String TAG ="PureH5Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pure_h5);
        String data = getIntent().getStringExtra("data");
        if (data == null) {
            Toast.makeText(this, "打开纯净的webView失败", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            webViewData = new Gson().fromJson(data, PureWebView.class);
        }
        initView();
        initData();
    }


    private void initView() {
        titleBarLayout = findViewById(R.id.pureTtitleBar);
        textView = findViewById(R.id.pureTitleBarTv);
        imageButton = findViewById(R.id.pureBackUpImageBtn);
        webView = findViewById(R.id.pureWebView);
        pureAllLayout = findViewById(R.id.pureAllLayout);
        imageButton = findViewById(R.id.pureBackUpImageBtn);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        if (webViewData.isRewriteTitle()) {
            textView.setText(webViewData.getTitle());
        }
        if (webViewData.isHasTitleBar()) {
            titleBarLayout.clearAnimation();
            titleBarLayout.setBackgroundColor(Color.parseColor(webViewData.getTitleColor()));
            textView.setTextColor(ColorStateList.valueOf(Color.parseColor(webViewData.getTitleTextColor())));
            pureAllLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        titleBarLayout.setVisibility(View.VISIBLE);
                    }
                });
        } else {
            titleBarLayout.clearAnimation();
            pureAllLayout.post(new Runnable() {
                @Override
                public void run() {
                    titleBarLayout.setVisibility(View.GONE);
                }
            });
        }

        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            getWindow().setStatusBarColor(Color.parseColor(webViewData.getTitleColor()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            if (webViewData.getStateBarTextColor().equals("black")) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            getWindow().setStatusBarColor(Color.parseColor(webViewData.getTitleColor()));
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if (TextUtils.isEmpty(webViewData.getHtml()) && TextUtils.isEmpty(webViewData.getPostData())) {
            Log.i(TAG, "initData: " + webViewData.getUrl());
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (request == null) return false;

                    try {
                        if (!request.getUrl().toString().startsWith("http://") && !request.getUrl().toString().startsWith("https://")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
                            startActivity(intent);
                            return true;
                        }
                    } catch (Exception e) {
                        return true;
                    }
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
            });
            webView.loadUrl(webViewData.getUrl());

        } else if (!TextUtils.isEmpty(webViewData.getHtml())) {
            webView.postUrl(webViewData.getUrl(), webViewData.getPostData().getBytes());
        } else {
            webView.loadDataWithBaseURL(webViewData.getUrl(), webViewData.getHtml(), null, "utf-8", null);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!webView.canGoBack()) {
                    finish();
                } else {
                    webView.goBack();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: ");
        if (webViewData.isWebBack()) {
            super.onBackPressed();
        }
    }
}