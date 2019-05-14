package com.example.mohamedashour.smartovertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    private OnConnectivityChangedListener listener;

    public ConnectivityChangeReceiver(OnConnectivityChangedListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        boolean isConnected = isNetworkAvailable(context);
        listener.onConnectivityChanged(isConnected);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface OnConnectivityChangedListener {
        void onConnectivityChanged(boolean isConnected);
    }
}
