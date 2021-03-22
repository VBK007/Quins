package com.example.quins.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {

    public static final int PICK_IMAGE_REQUEST =1234 ;
    public static String chilpoststatus="Quin";
    public static String photourl="https://firebasestorage.googleapis.com/v0/b/gceians-b080e.appspot.com/o/p.png?alt=media&token=f42d995b-e7cc-4237-8667-7d76881328ef";
    public static String put_key="";

    public static boolean isConnectionAvailabele(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null){

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null&& networkInfo.isConnected()&& networkInfo.isConnectedOrConnecting()&&networkInfo.isAvailable()){
                return true;
            }

        }
        return false;
    }
}
