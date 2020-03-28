package india.abhijeet.k.fuelinspector;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import static india.abhijeet.k.fuelinspector.Notify.CHANNEL_ID;

public class OdometerService extends Service {


    LocationListener locationListener;
    LocationManager locationManager;
    Location lastlocation=null;

    double distance=0;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

      //  super.onCreate();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                return;
            }
        } else {

            Toast.makeText(this, "Please make sure is your Location Service is Active and accept the location permission ", Toast.LENGTH_LONG).show();

        }

        ////location listener code here///

        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastlocation==null)
                {

                    lastlocation=location;

                }
                distance+=location.distanceTo(lastlocation);
                lastlocation=location;


                Intent i=new Intent("location update");
                i.putExtra("distance",distance);
                sendBroadcast(i);




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent i=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);


            }
        };




           locationManager=(LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,900,1,locationListener);























    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //you can get data from GUI by using intent here!!










        // locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);


        Intent notifyIntent=new Intent(this,TripCounterGUI.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notifyIntent,0);

        Notification notification =new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Trip Started")
                .setContentText("Calculating distance!")
                .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

      //  if (locationManager!=null)
        //        locationManager.removeUpdates(locationListener);




    }
}

