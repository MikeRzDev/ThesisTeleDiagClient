package co.edu.unab.gti.thesistelediagclient.device;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import co.edu.unab.gti.thesistelediagclient.util.AppContext;

/**
 * Created by user on 11/23/2015.
 */
public class NotificationService {

    public final static void sendNotification(String tickerText, int drawableIconId, String contentTitle,
                                              String ContentText, PendingIntent pendingIntent){
        Context context = AppContext.getContext();
        Notification.Builder notificationBuilder = new Notification.Builder(
                    context)
                    .setTicker(tickerText)
                    .setSmallIcon(drawableIconId)
                    .setAutoCancel(true)
                    .setContentTitle(contentTitle)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                    .setContentText(ContentText);

        if (pendingIntent != null)
        notificationBuilder.setContentIntent(pendingIntent);

        // Pass the Notification to the NotificationManager:
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int randomNum = 1 + (int)(Math.random()*10000000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            mNotificationManager.notify(randomNum,
                    notificationBuilder.build());
        } else {
            mNotificationManager.notify(randomNum,
                    notificationBuilder.getNotification());
        }

    }
}
