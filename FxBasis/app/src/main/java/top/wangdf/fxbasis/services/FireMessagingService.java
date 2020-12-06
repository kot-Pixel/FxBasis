package top.wangdf.fxbasis.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

import top.wangdf.fxbasis.R;
import top.wangdf.fxbasis.entity.PushMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FireMessagingService extends FirebaseMessagingService {

    private static final String TAG  = "FireMessageFire";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.i(TAG, "onMessageReceived: " + remoteMessage.getData().toString());
        Map<String, String> data = remoteMessage.getData();
        if (!data.isEmpty()) {
            PushMessage pushMessage = new Gson().fromJson(data.toString(), PushMessage.class);
            Log.i(TAG, "onMessageReceived: " + pushMessage.toString());
            createNotification(getBaseContext(), pushMessage);
        }

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Log.i(TAG, "onMessageReceived: " + notification);
        if (notification != null) {
//            showNotification(getBaseContext(), notification);
            Log.i(TAG, "onMessageReceived: " + notification.getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.i(TAG, "onNewToken: " + s);
    }

    private void createNotification(Context context, PushMessage pushMessageModel) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String brand = Build.BRAND;
        long createTime = Long.parseLong(pushMessageModel.getData().getCreateTime());
        String channel = "Fxbasis";
        String fxbasisChannelId = createNotificationChannel(channel, notificationManager);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, fxbasisChannelId)
                .setContentTitle(pushMessageModel.getData().getPushTopic())
                .setContentText(pushMessageModel.getData().getPushContent())
                .setSmallIcon(R.mipmap.logo)//
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setWhen(createTime)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        if (!TextUtils.isEmpty(brand) && brand.equalsIgnoreCase("samsung")) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
            notification.setLargeIcon(bitmap);
        }
        notification.setContentIntent(setPendingIntent(context, pushMessageModel));
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        notificationManager.notify(100, notification.build());
    }

    /**
     * Android 8.0以上创建通知必须需要一个通知的渠道。Android 8.0 创建通知的话，则不需要渠道。
     * @param channelId
     * @param notificationManager
     * @return 创建好的渠道
     */
    private String createNotificationChannel(
            String channelId,
            NotificationManager notificationManager
    ) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            channelId,
                            channelId,//channelName 和 ChannelId 相同
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
            notificationChannel.enableLights(true); //开启指示灯，如果设备有的话。
            notificationChannel.enableVibration(true); //开启震动
            notificationChannel.setLightColor(Color.RED); // 设置指示灯颜色
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);//设置是否应在锁定屏幕上显示此频道的通知
            notificationChannel.setShowBadge(true); //设置是否显示角标
            notificationChannel.setBypassDnd(true); // 设置绕过免打扰模式
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400}); //设置震动频率
            notificationChannel.setDescription(channelId);
            notificationManager.createNotificationChannel(notificationChannel);
            return channelId;
        } else {
            return null;
        }
    }

    private PendingIntent setPendingIntent(Context context, PushMessage data) {
        Intent intent;
        String url = data.getData().getUrl();
        if (TextUtils.isEmpty(url)) {
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        } else {
            //TODO WebView打开这个Url
            Uri uri = Uri.parse(url);
            intent = new Intent(Intent.ACTION_VIEW, uri);
        }
        return PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
    }
}
