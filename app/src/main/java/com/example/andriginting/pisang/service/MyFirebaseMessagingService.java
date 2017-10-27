package com.example.andriginting.pisang.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.andriginting.pisang.app.Config;
import com.example.andriginting.pisang.util.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

/**
 * Created by Andri Ginting on 10/24/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e(TAG,"From: "+remoteMessage.getFrom());

        //cek apakah notifikasi memiliki data
        if (remoteMessage == null){
            return;
        }

        if (remoteMessage.getNotification() !=null){
            Log.e(TAG,"Notification Body: "+remoteMessage.getData().toString());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size()>0){
            Log.e(TAG,"Data Payload: "+remoteMessage.getData().toString());

            try{
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void handleDataMessage(JSONObject json) {

    }

    private void handleNotification(String pesan){
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())){
            //ketika app di foreground, push pesan
            Intent pushNotification = new Intent(Config.INSTANCE.getPUSH_NOTIFICATION());
            pushNotification.putExtra("pesan",pesan);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            //untk mengaktifkan suara ketika notifikasi masuk
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
        }
    }

}
