package top.wangdf.fxbasis.common;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class Utils {

    private static final String TAG = "Utils";

    /**
     * 获取deviceId
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        //制造商 （Manufacturer）
        String manufacturer = android.os.Build.MANUFACTURER;
        //型号（Model）
        String model = android.os.Build.MODEL;
        //品牌（Brand）
        String brand = android.os.Build.BRAND;
        //设备名 （Device）
        String device = android.os.Build.DEVICE;
        Log.i(TAG, "serialNum: " + androidId);
        Log.i(TAG, "manufacturer: " + manufacturer);
        Log.i(TAG, "model: " + model);
        Log.i(TAG, "brand: " + brand);
        Log.i(TAG, "device: " + device);
        if (androidId == null) {
            return "0000000000000000";//暂时先此返回deviceId
        } else {
            return androidId;
        }
    }

    /**
     * 获取时间戳
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
