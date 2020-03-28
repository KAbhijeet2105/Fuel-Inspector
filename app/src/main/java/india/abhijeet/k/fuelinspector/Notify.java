package india.abhijeet.k.fuelinspector;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notify extends Application {

    public static final String CHANNEL_ID="notify_user";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotification();

    }

    private void createNotification() {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel serviceChannel=new NotificationChannel(
                    CHANNEL_ID,
                    "ServiceNotification",
                    NotificationManager.IMPORTANCE_DEFAULT


            );


            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);


        }

    }
}
