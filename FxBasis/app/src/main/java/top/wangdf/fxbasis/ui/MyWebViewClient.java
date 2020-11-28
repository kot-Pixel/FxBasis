package top.wangdf.fxbasis.ui;///*
//package top.wangdf.fxbasis.ui;
//
//import android.net.Uri;
//import android.os.Build;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//
//public class MyWebViewClient extends WebChromeClient {
//
//    ValueCallback<Uri> mUploadMessage;
//    ValueCallback<Uri[]> mFilePathCallback;
//
//    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//        if (mFilePathCallback != null)
//            return true;
//        mFilePathCallback = filePathCallback;//一个回调的初始化
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //如果选择的是拍照，这个值应该是类似这种image/jpeg,image/jpg,image/gif,image/png,image/bmp，所以我选择了判断包含
//            if (fileChooserParams.getAcceptTypes()[0].contains("image")) {
//                selectImage();//选择图片
//            } else {
//                selectFile();//选择文件
//            }
//        }
//        return true;
//    }
//
//    // For Android 3.0+
//    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//        //此处同以上
//        if (mUploadMessage != null)
//            return;
//        mUploadMessage = uploadMsg;//另一个回调的初始化
//        if (!StringUtil.isEmpty(acceptType) && acceptType.contains("image")) {
//            selectImage();
//        } else {
//            selectFile();
//        }
//
//    }
//
//    // For Android < 3.0
//    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//        openFileChooser(uploadMsg, "");
//    }
//
//    // For Android  > 4.1.1
//    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//        openFileChooser(uploadMsg, acceptType);
//    }
//
//}
//*/
