package com.example.andriginting.pisang.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.andriginting.pisang.app.Config;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Andri Ginting on 10/24/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //menyimpan reg id untu shared pref
        storeRegIdInPref(refreshedToken);

        //menyimpan reg id di dalam server firebase
        sendRegistrationToServer(refreshedToken);

        //notify UI that registration completed
        Intent registrationComplete = new Intent(Config.INSTANCE.getREGISTRATION_COMPLETE());
        registrationComplete.putExtra("token",refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(String token){
        //sending gcm to server
        Log.e(TAG,"sendRegistrationToServer "+token);
    }

    private void storeRegIdInPref(String Token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.INSTANCE.getSHARED_PREF(),0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId",Token);
        editor.commit();
    }
}
