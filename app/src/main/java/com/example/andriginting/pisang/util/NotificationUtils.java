package com.example.andriginting.pisang.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.andriginting.pisang.R;
import com.example.andriginting.pisang.app.Config;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Andri Ginting on 10/23/2017.
 */

public class NotificationUtils {
    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context context;

    public NotificationUtils(Context context) {
        this.context = context;
    }

    public void showNotificationMessage(String judul, String pesan, String timestamp, Intent intent) {
        showNotificationMessage(judul, pesan, timestamp, intent, null);
    }

    public void showNotificationMessage(final String judul, final String pesan, final String timestamp, Intent intent, String imageUrl) {
        //check untuk notifikasi yg kosongan
        if (TextUtils.isEmpty(pesan))
            return;

        //untuk cek icon notifikasi
        final int icon = R.drawable.banana_pattern;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resulPendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "raw/notification");

        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Bitmap bitmap = getBitmapFromUrl(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, icon, judul, pesan, timestamp, resulPendingIntent, alarmSound);
                } else {
                    showSmallNotification(mBuilder, icon, judul, pesan, timestamp, resulPendingIntent, alarmSound);
                }
            }
        } else {
            showSmallNotification(mBuilder, icon, judul, pesan, timestamp, resulPendingIntent, alarmSound);
            playNotificationSound();
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String judul,
                                       String pesan, String timestamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(pesan);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon)
                .setTicker(judul)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(judul)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(inboxStyle)
                .setWhen(getTimeMiliSec(timestamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(pesan)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }

    private void showBigNotification(Bitmap bitmap,NotificationCompat.Builder mBuilder, int icon, String title,
                                     String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
                .setWhen(getTimeMiliSec(timeStamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE,notification);
    }

    public static long getTimeMiliSec(String timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timestamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Bitmap getBitmapFromUrl(String sURL) {
        try {
            URL url = new URL(sURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //untuk menjalankan bunyi notifikasi
    public void playNotificationSound(){
        try{
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + context.getPackageName() + "raw/notification");

            Ringtone r = RingtoneManager.getRingtone(context,alarmSound);
            r.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //untuk menghapus notifikasi pada awal
    public static void clearNotification(Context context){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    //untuk mengecek apakah apps berjalan di background apa tidak
    public static boolean isAppIsInBackground(Context context){
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH){
            List<ActivityManager.RunningAppProcessInfo> runninProcesses = am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo processInfo : runninProcesses){
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                    for (String activeProcess : processInfo.pkgList){
                        if (activeProcess.equals(context.getPackageName())){
                            isInBackground =false;
                        }
                    }
                }
            }
        }else{
            List<ActivityManager.RunningTaskInfo> taskInfos = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfos.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())){
                isInBackground = false;
            }
        }
        return  isInBackground;
    }
}
