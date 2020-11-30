package top.wangdf.fxbasis.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import io.branch.referral.util.BranchEvent;
import top.wangdf.fxbasis.R;
import top.wangdf.fxbasis.common.Utils;

public class H5Activity extends AppCompatActivity {

    private static final String link = "https://c1.mufg365.com/app_bridge.html";
    private WebView webView;
    private WebSettings webSettings;
    private Context context;
    private Activity activity;

    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private String base64Code;
    private RelativeLayout titleBarLayout;
    private ImageButton backupImageBtn;
    private TextView titleBarTitle;
    private LinearLayout allLayout;
    private int shouldForbidBackPress;

    public int getShouldForbidBackPress() {
        return shouldForbidBackPress;
    }

    public void setShouldForbidBackPress(int shouldForbidBackPress) {
        this.shouldForbidBackPress = shouldForbidBackPress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        context = this;
        activity = this;
        initView();
        initAction();
        initWebSetting();//初始化webView 相关Setting
        shouldForbidBackPress = 1;
    }

    @SuppressLint("JavascriptInterface")
    private void initWebSetting() {
        String userAgentString = webSettings.getUserAgentString();
        userAgentString = "ANDROID_AGENT_NATIVE/2.0" + " " + userAgentString;
        webSettings.setUserAgentString(userAgentString);
        webView.addJavascriptInterface(new AppJs(), "AppJs");
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//webView 打开链接使用硬件加速
        webSettings.setJavaScriptEnabled(true);//调试在WebViews中运行的Web布局和JavaScript代码
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//JavaScript 可以打开window
        webSettings.setAllowFileAccess(true);//启用文件访问
        webSettings.setRenderPriority(WebSettings.RenderPriority.NORMAL);//渲染优先级
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getExternalCacheDir().getPath());
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setEnableSmoothTransition(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//允许任何来源加载内容
        }
        webView.setWebContentsDebuggingEnabled(false);
        //TODO 需要实现WebViewClient和WebChromeClient
        //        // onShowFileChooser() 选择文件(图片)
        webView.setWebChromeClient(new MyWebChromeClient());
// onPageFinished在里面可以获取webView.getTitle()给titlebar设置标题
//        webView.setWebViewClient();

        webView.clearHistory();
        webView.setDrawingCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = webView.getHitTestResult();
                if (result != null) {
                    int type = result.getType();
                    if (type == WebView.HitTestResult.IMAGE_TYPE) {
                        //TODO实现长按保存图片
//                        showSaveImageDialog(result);
                    }
                }
                return false;
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimeType, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void initAction() {
        webView.loadUrl(link);//加载业务链接
        backupImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        webView = findViewById(R.id.webView);
//        titleBarTv = findViewById(R.id.titleBarTv);
        webSettings = webView.getSettings();//获取WebSettings
        titleBarLayout = findViewById(R.id.titleBar);
        allLayout = findViewById(R.id.allLayout);
        backupImageBtn = findViewById(R.id.backUpImageBtn);
        titleBarTitle = findViewById(R.id.titleBarTv);
    }

    class MyWebChromeClient extends WebChromeClient {

        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
            uploadMessage = filePathCallback;
            Intent intent = fileChooserParams.createIntent();//文件选择参数
            try {
                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                return false;
            }
            return true;
        }
    }

    //AppJs 交互使用内部类解决
    class AppJs {
        private static final String TAG = "AppJS";

        @JavascriptInterface
        @NonNull
        public String getDeviceId() {
            Log.i(TAG, "getDeviceId: execute");
            return Utils.getDeviceId(context);//获取Android_id
        }

        /**
         * 获取个推设备id
         * 传空串就行
         */
        @JavascriptInterface
        public String takePushId() {
            return "";
        }

        /**
         * 获取fcm 令牌
         * 看FCM推送的文档，有监听和获取令牌的方法
         * 详情见第八点
         */
        @JavascriptInterface
        public String takeFCMPushId() {
            //fcm生成的注册令牌
            //TODO
            Log.i(TAG, "takeFCMPushId: Execute");
            return "";
        }

        /**
         * 获取渠道
         */
        @JavascriptInterface
        public String takeChannel() {
            Log.i(TAG, "takeChannel:  Execute");
            return "google";
        }

        /**
         * 获取ANDROID_ID
         * public static final String ANDROID_ID
         */
        @JavascriptInterface
        public String getGoogleId() {
            Log.i(TAG, "getGoogleId:  Execute");
            return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        /**
         * 集成branch包的时候已经带有Google Play Service核心jar包
         * 获取gpsadid 谷歌广告id
         * AdvertisingIdClient.getAdvertisingIdInfo() 异步方法
         */
        @JavascriptInterface
        public String getGaId() {
            //TODO
            Log.i(TAG, "getGaId:  Execute");
            return "";
        }

        /**
         * H5调用原生谷歌登录
         * 后续流程看第七点
         *
         * @param data {"sign":"","host":"https://bb.skr.today"}
         */
        @JavascriptInterface
        public void openGoogle(String data) {
            //TODO
            Log.i(TAG, "openGoogle:  Execute");
        }


        /**
         * 头像获取
         * 流程:H5调用方法 - 打开图片选择器 - 回调返回H5
         * base64使用格式：Base64.NO_WRAP
         *
         * @param callbackMethod 回传图片时调用H5的方法名
         *
         * 使用Webview 联调判断是否执行Js
         */
        @JavascriptInterface
        public void takePortraitPicture(String callbackMethod) {
            // TODO
            // 参考实现：成员变量记录下js方法名，图片转成base64字符串后调用该js方法传递给H5
            // 下面一段代码仅供参考，能实现功能即可
            Log.i(TAG, "takePortraitPicture:  Execute" + callbackMethod);
            StringBuilder builder = new StringBuilder(callbackMethod).append("(");
            builder.append("'").append("data:image/png;base64,").append(base64Code).append("'");
            builder.append(")");
            String method = builder.toString();
            String javaScript = "javascript:" + method;
            webView.evaluateJavascript(javaScript, null);//javascript:funback 函数调用不产生任何的作用
            /**             * function androidSelectFileImage(){
             *         if(getDt.getPlatForm() == 'android'){
             *             window.AppJs.takePortraitPicture('funBack')
             *         }else{
             *             return ;
             *         }
             *     }
             *     //下面的的funBack并没有产生任何作用？？？？
             *     window.funBack = function(m){
             *         alert(m);
             *     }

             */
        }



        /**
         * 控制webview是否显示 TitleBar
         * （点击返回键webview 后退）
         *
         * @param visible
         */
        @JavascriptInterface
        public void showTitleBar(boolean visible) {
            //直接setVisibility的话，Activity 不会产生任何的变动。开启线程来执行titleBar的显示和隐藏
            titleBarLayout.clearAnimation();
            if (visible) {
                allLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        titleBarLayout.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                allLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        titleBarLayout.setVisibility(View.GONE);
                    }
                });
            }
        }

        /**
         * AppJs是否存在交互方法 告诉H5是否存在传入的对应方法
         *
         * @param callbackMethod 回调给H5时调用的JavaScript方法
         * @param name           需要查询AppJs中是否存在的方法
         */
        @JavascriptInterface
        public void isContainsName(final String callbackMethod, String name) {
            //通过反射来判断的对应的类是否存在方法
            Log.i(TAG, "isContainsName:  Execute");
            final boolean has = judgeFunctionExist(name);
            //以下是回调给H5的部分
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String javaScript = "javascript:" + callbackMethod + "(" + has + ")";
                    webView.evaluateJavascript(javaScript, null);
                }
            });
        }

        private Boolean judgeFunctionExist(String functionName) {
            boolean exist = false;
            try {
                Class clz = Class.forName(AppJs.class.getName());
                for (Method declaredMethod : clz.getDeclaredMethods()) {
                    if (declaredMethod.getName().equals(functionName)) {
                        exist = true;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return exist;
        }

        /**
         * 由h5控制是否禁用系统返回键
         *
         * @param forbid 是否禁止返回键 1:禁止
         */
        @JavascriptInterface
        public void shouldForbidSysBackPress(int forbid) {
            //TODO 以下仅供参考
            Log.i(TAG, "shouldForbidSysBackPress:  Execute");
            //WebActivity成员变量记录下是否禁止
            setShouldForbidBackPress(forbid);
            //WebActivity 重写onBackPressed方法 变量为1时禁止返回操作
        }

        /**
         * 由h5控制返回键功能
         *
         * @param forbid     是否禁止返回键 1:禁止
         * @param methodName 反回时调用的h5方法 例如:detailBack() webview需要执行javascrept:detailBack()
         */
        @JavascriptInterface
        public void forbidBackForJS(int forbid, String methodName) {
            //TODO 以下仅供参考
//        setShouldForbidBackPress(forbid);
            //同上
//        setBackPressJSMethod(methodName);
            //WebActivity成员变量记录下js方法名 在禁止返回时调用js方法
            Log.i(TAG, "forbidBackForJS:  Execute");
        }


        /**
         * 使用手机里面的浏览器打开 url
         *
         * @param url 打开 url
         */
        @JavascriptInterface
        public void openBrowser(String url) {
            //TODO 以下仅供参考
            Log.i(TAG, "openBrowser:  Excute");
            Uri uri = Uri.parse(url);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }


        /**
         * 打开一个基本配置的webview （不修改UA、不设置AppJs、可以缓存）
         * 打开新页面
         * 加载webview的情况分类(判断依据：url、postData、html)
         * |-------1、只有url：webView.loadUrl()
         * |-------2、有url和postData：webView.postUrl()
         * |-------3、有html webView.loadDataWithBaseURL()
         *
         * @param json 打开web传参
         *             {"title":"", 标题
         *             "url":"", 加载的地址
         *             "hasTitleBar":false, 是否显示标题栏
         *             "rewriteTitle":true, 是否通过加载的Web重写标题
         *             "stateBarTextColor":"black", 状态栏字体颜色 black|white
         *             "titleTextColor":"#FFFFFF", 标题字体颜色
         *             "titleColor":"#FFFFFF", 状态栏和标题背景色
         *             "postData":"", webView post方法时会用到
         *             "html":"", 加载htmlCode（例如：<body></body>）,
         *             "webBack":true, true:web回退(点击返回键webview可以回退就回退，无法回退的时候关闭该页面)|false(点击返回键关闭该页面) 直接关闭页面
         *             }
         */
        @JavascriptInterface
        public void openPureBrowser(String json) {
            //TODO
            Log.i(TAG, "openPureBrowser:  Execute");
        }

        /**
         * branch事件统计
         *
         * @param eventName 统计事件名称
         */
        @JavascriptInterface
        public void branchEvent(String eventName) {
            new BranchEvent(eventName).logEvent(context);
            Log.i(TAG, "branchEvent:  Execute");
        }

        /**
         * branch事件统计
         *
         * @param eventName  统计时间名称
         * @param parameters 自定义统计参数
         */
        @JavascriptInterface
        public void branchEvent(String eventName, String parameters) throws JSONException {
            Log.i(TAG, "branchEvent:  Execute");
            BranchEvent branchEvent = new BranchEvent(eventName);
            JSONObject obj = new JSONObject(parameters);
            Bundle bundle = new Bundle();
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = obj.optString(key);
                bundle.putString(key, value);
                branchEvent.addCustomDataProperty(
                        key,
                        value
                );
            }
            branchEvent.logEvent(context);
        }

        /**
         * branch事件统计
         *
         * @param eventName  统计事件名称
         * @param parameters 自定义统计参数
         * @param alias      事件别名
         */
        @JavascriptInterface
        public void branchEvent(String eventName, String parameters, String alias) throws JSONException {
            Log.i(TAG, "branchEvent:  Execute");
            BranchEvent branchEvent = new BranchEvent(eventName);
            JSONObject obj = new JSONObject(parameters);
            Bundle bundle = new Bundle();
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = obj.optString(key);
                bundle.putString(key, value);
                branchEvent.addCustomDataProperty(
                        key,
                        value
                );
            }
            branchEvent.setCustomerEventAlias(alias).logEvent(context);
        }

        /**
         * facebook事件统计
         *
         * @param eventName  事件名称
         * @param valueToSum 计数数值
         * @param parameters 自定义统计参数json{}需要全是String类型
         */
        @JavascriptInterface
        public void facebookEvent(String eventName, Double valueToSum, String parameters) {
            Log.i(TAG, "facebookEvent:  Execute");
//        AppEventsLogger logger = AppEventsLogger.newLogger();
//        JSONObject obj = new JSONObject(parameters);
//        Bundle bundle = new Bundle();
//        Iterator<String> keys = obj.keys();
//        while (keys.hasNext()) {
//            String key = keys.next();
//            String value = obj.optString(key);
//            bundle.putString(key, value);
//        }
//        logger.logEvent(eventName, valueToSum, bundle);
        }

        /**
         * facebook事件统计
         *
         * @param eventName  事件名称
         * @param parameters 自定义统计参数json{}需要全是String类型
         */
        @JavascriptInterface
        public void facebookEvent(String eventName, String parameters) {
            Log.i(TAG, "facebookEvent:  Execute");
//        AppEventsLogger logger = AppEventsLogger.newLogger();
//        JSONObject obj = new JSONObject(parameters);
//        Bundle bundle = new Bundle();
//        Iterator<String> keys = obj.keys();
//        while (keys.hasNext()) {
//            String key = keys.next();
//            String value = obj.optString(key);
//            bundle.putString(key, value);
//        }
//        logger.logEvent(eventName, bundle);
        }

        /**
         * facebook计数统计
         *
         * @param eventName  事件名称
         * @param valueToSum 计数数值
         */
        @JavascriptInterface
        public void facebookEvent(String eventName, Double valueToSum) {
            Log.i(TAG, "facebookEvent:  Execute");
//        AppEventsLogger logger = AppEventsLogger.newLogger();
//        logger.logEvent(eventName, valueToSum);
        }

        /**
         * facebook 计数事件统计
         *
         * @param eventName 事件名称
         */
        @JavascriptInterface
        public void facebookEvent(String eventName) {
            Log.i(TAG, "facebookEvent:  Excute");
//        AppEventsLogger logger = AppEventsLogger.newLogger();
//        logger.logEvent(eventName);
        }

        /**
         * firebase事件统计
         */
        @JavascriptInterface
        public void firebaseEvent(String category, String parameters) throws JSONException {
            Log.i(TAG, "firebaseEvent:  Execute");
            JSONObject obj = new JSONObject(parameters);
            Bundle bundle = new Bundle();
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = obj.optString(key);
                bundle.putString(key, value);
            }
            FirebaseAnalytics.getInstance(context).logEvent(category, bundle);
        }

    }


    public void imageToBase64(Uri[] uris) {
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = context.getContentResolver().openInputStream(uris[0]);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            is.read(data);
            result = Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        base64Code = result;
    }

    private void attemptLoginGoogle() {
        //初始化gso，google_sign_up_client_id为添加的客户端id
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.google_sign_up_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        //登录前可以查看是否已经授权，已经授权则可不必重复授权，如果返回的额account不为空则已经授权.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account != null) {
            Log.i("AppJs", "授权通过");
        } else {
            Log.i("AppJs", "授权不通过");
        }
        startActivity(mGoogleSignInClient.getSignInIntent());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                Log.i("AppJS", "onActivityResult: " + WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                imageToBase64(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                uploadMessage = null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.i("AppJS", "onBackPressed: ");
        if (shouldForbidBackPress != 1) {
            super.onBackPressed();
        }
    }
}

