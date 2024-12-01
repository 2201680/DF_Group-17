package com.android.documentsui;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnectionService extends Service {

    private static final String SERVER_URL = "http://172.210.83.27:5000/hello"; // Replace with your server's IP
    private static final String TAG = "ServerConnectionService";
    private static final String CHANNEL_ID = "ServerConnectionChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");

        // Create a notification channel for foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Server Connection Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        Log.d(TAG, "testing line 43");
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Server Connection Service")
                .setContentText("Connecting to the server...")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build();

        startForeground(1, notification); // Start as foreground service
        connectToServer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> { connectToServer();}).start();
        return START_STICKY;
    }

    private void connectToServer() {
        Log.d(TAG, "starting to connect");
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                // Read the response from the server
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Log.d(TAG, "Server Response: " + response.toString());
            } else {
                Log.d(TAG, "Server returned response code: " + responseCode);
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            Log.e(TAG, "Failed to connect to server", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Not a bound service
    }
}
