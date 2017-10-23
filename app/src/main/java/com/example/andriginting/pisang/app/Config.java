package com.example.andriginting.pisang.app;

/**
 * Created by Andri Ginting on 10/10/2017.
 */

public class Config {
    //global topic untuk receive push notif
    public static final String TOPIC_GLOBAL ="global";

    //broadcast intent filter
    public static final String REGISTRATION_COMPLETE="registrationComplete";
    public static final String PUSH_NOTIFICATION="pushNotification";

    //id untuk menghandel notifikasi
    public static final int NOTIFICATION_ID=100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF="ps_firebase";

}
