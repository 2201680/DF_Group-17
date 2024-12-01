package com.example.hiddenconnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.documentsui.ServerConnectionService;


public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "Device booted. Starting ServerConnectionService...");
            Intent serviceIntent = new Intent(context, ServerConnectionService.class);
            context.startForegroundService(serviceIntent);
        }
    }
}
