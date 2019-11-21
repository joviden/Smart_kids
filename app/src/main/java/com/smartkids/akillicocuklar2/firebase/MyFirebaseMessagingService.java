package com.smartkids.akillicocuklar2.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartkids.akillicocuklar2.MainActivity;
import com.smartkids.akillicocuklar2.R;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

    }


    private void sendNotification(String messageTitle,String messageBody) {

        String CHANNEL_ID = "erdem";


        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        Intent notificationintent = new Intent(getApplicationContext(),MainActivity.class);
        notificationintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),100,notificationintent,PendingIntent.FLAG_UPDATE_CURRENT);

        try {


            if (Build.VERSION.SDK_INT >= 26) {
                CharSequence name = "Smart Kids";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
                builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.rateiconotify)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setChannelId(CHANNEL_ID)
                        .setSound(alarmsound)
                        .setAutoCancel(true);


            } else {

                int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.rateiconotify : R.mipmap.ic_launcher ;
                builder = new NotificationCompat.Builder(getApplicationContext(), "erdem")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(icon)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setSound(alarmsound)
                        .setAutoCancel(true); //kullanıcı swipe yaparak kapatabilsin diye
            }


            if (notificationManager != null) {
                notificationManager.notify(100, builder.build());
            }

        }catch (Exception e) {
            e.printStackTrace();
        }



    }
}
