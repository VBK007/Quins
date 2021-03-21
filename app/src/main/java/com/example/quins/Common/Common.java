package com.example.quins.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {

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
