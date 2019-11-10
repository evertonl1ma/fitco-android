package mangobits.startupkit.core.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import com.mangobits.agroaz.splash.SplashActivity;
import com.mangobits.fitco.R;
import com.mangobits.startupkit.core.notification.Notification;


public class MSKFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            Notification notification = mapper.readValue(remoteMessage.getData().get("body"), Notification.class);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 0);

            android.app.Notification fcmNoti = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(notification.getMessage())
                   //todo: .setSmallIcon(R.drawable.placeholder_logo)
                    .setContentIntent(contentIntent)
//                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notificacao))
                    .build();

            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            manager.notify(123, fcmNoti);


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
