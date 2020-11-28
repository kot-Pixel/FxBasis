package top.wangdf.fxbasis.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import io.branch.referral.util.BranchEvent;

public class AppJs {
    private H5Activity activity;

    private WebView webView;

    private Context mContext;

    private static final String TAG = "AppJS";

    public AppJs(H5Activity h5Activity, WebView webView, Context context) {
        activity = h5Activity;
        webView = webView;
        mContext = context;
    }

    /**
     * 获取设备id
     * 必须保证有值
     * 获取不到的时候生成一个UUID
     */
    @JavascriptInterface
    @NonNull
    public String getDeviceId() {
        Log.i(TAG, "getDeviceId: excute");
        return "";
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
        Log.i(TAG, "takeFCMPushId: Excute");
        return "";
    }

    /**
     * 获取渠道
     */
    @JavascriptInterface
    public String takeChannel() {
        Log.i(TAG, "takeChannel:  Excute");
        return "google";
    }

    /**
     * 获取ANDROID_ID
     * public static final String ANDROID_ID
     */
    @JavascriptInterface
    public String getGoogleId() {
        //TODO
        Log.i(TAG, "getGoogleId:  Excute");
        return "";
    }

    /**
     * 集成branch包的时候已经带有Google Play Service核心jar包
     * 获取gpsadid 谷歌广告id
     * AdvertisingIdClient.getAdvertisingIdInfo() 异步方法
     */
    @JavascriptInterface
    public String getGaId() {
        //TODO
        Log.i(TAG, "getGaId:  Excute");
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
        Log.i(TAG, "openGoogle:  Excute");
    }

    /**
     * 头像获取
     * 流程:H5调用方法 - 打开图片选择器 - 回调返回H5
     * base64使用格式：Base64.NO_WRAP
     *
     * @param callbackMethod 回传图片时调用H5的方法名
     */
    @JavascriptInterface
    public void takePortraitPicture(String callbackMethod) {
        // TODO
        // 参考实现：成员变量记录下js方法名，图片转成base64字符串后调用该js方法传递给H5
        // 下面一段代码仅供参考，能实现功能即可
        Log.i(TAG, "takePortraitPicture:  Excute");
        String str = "";
        if (!TextUtils.isEmpty(callbackMethod)) {
            StringBuilder builder = new StringBuilder(callbackMethod).append("(");
            builder.append("'").append("data:image/png;base64,").append(str).append("'");
            builder.append(")");
            String method = builder.toString();
            String javaScript = "javascript:" + method;
            webView.evaluateJavascript(javaScript, null);
        }
    }

    /**
     * 控制webview是否显示 TitleBar
     * （点击返回键webview 后退）
     *
     * @param visible
     */
    @JavascriptInterface
    public void showTitleBar(boolean visible) {
        //TODO
        Log.i(TAG, "showTitleBar:  Excute");
    }

    /**
     * AppJs是否存在交互方法 告诉H5是否存在传入的对应方法
     *
     * @param callbackMethod 回调给H5时调用的JavaScript方法
     * @param name           需要查询AppJs中是否存在的方法
     */
    @JavascriptInterface
    public void isContainsName(final String callbackMethod, String name) {
        final boolean has = false;
        //TODO 遍历所有提供的@JavascriptInterface，判断否含有name方法，把结果通过JavaScript反馈给H5
//        has =
        Log.i(TAG, "isContainsName:  Excute");
        //以下是回调给H5的部分
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String javaScript = "javascript:" + callbackMethod + "(" + has + ")";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript(javaScript, null);
                } else {
                    webView.loadUrl(javaScript);
                }
            }
        });
    }

    /**
     * 由h5控制是否禁用系统返回键
     *
     * @param forbid 是否禁止返回键 1:禁止
     */
    @JavascriptInterface
    public void shouldForbidSysBackPress(int forbid) {
        //TODO 以下仅供参考
        Log.i(TAG, "shouldForbidSysBackPress:  Excute");
        //WebActivity成员变量记录下是否禁止
//        mContext.setShouldForbidBackPress(forbid);
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
//        mContext.setShouldForbidBackPress(forbid);
        //同上
//        mContext.setBackPressJSMethod(methodName);
        //WebActivity成员变量记录下js方法名 在禁止返回时调用js方法
        Log.i(TAG, "forbidBackForJS:  Excute");
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
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
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
        Log.i(TAG, "openPureBrowser:  Excute");
    }

    /**
     * branch事件统计
     *
     * @param eventName 统计事件名称
     */
    @JavascriptInterface
    public void branchEvent(String eventName) {
        new BranchEvent(eventName).logEvent(mContext);
        Log.i(TAG, "branchEvent:  Excute");
    }

    /**
     * branch事件统计
     *
     * @param eventName  统计时间名称
     * @param parameters 自定义统计参数
     */
    @JavascriptInterface
    public void branchEvent(String eventName, String parameters) throws JSONException {
        Log.i(TAG, "branchEvent:  Excute");
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
        branchEvent
                .logEvent(mContext);
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
        Log.i(TAG, "branchEvent:  Excute");
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
        branchEvent
                .setCustomerEventAlias(alias)
                .logEvent(mContext);

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
        Log.i(TAG, "facebookEvent:  Excute");
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
        Log.i(TAG, "facebookEvent:  Excute");
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
        Log.i(TAG, "facebookEvent:  Excute");
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
        Log.i(TAG, "firebaseEvent:  Excute");
        JSONObject obj = new JSONObject(parameters);
        Bundle bundle = new Bundle();
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = obj.optString(key);
            bundle.putString(key, value);
        }
        FirebaseAnalytics.getInstance(mContext).logEvent(category, bundle);
    }
}
