package india.abhijeet.k.fuelinspector;

import android.Manifest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TripCounterGUI extends AppCompatActivity implements Input_dialouge.InputDialougeListener{

    public static final String crt_dt="crt_dt";
    public static final String aftr_dt="aftr_dt";

    int STORAGE_PERMISSION_CODE=1;
    private InterstitialAd interstitialAd;

BroadcastReceiver broadcastReceiver;
   TextView textView;
   TextView runMoney,runFuelCount;

   Button starttrk,stoptrk;

String newdistance;
Double coverdistance,Runcunsumption=0.0,RunTotalFuel=0.0;




    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver==null){

            broadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    coverdistance=intent.getExtras().getDouble("distance");


                    try {
                        coverdistance = coverdistance / 1000;


                    }catch (Exception e){

                        Log.d("EXX:",""+e);
                    }

                    textView.setText( String.format(" %.3f km",coverdistance));

                }
            };

        }
        registerReceiver(broadcastReceiver,new IntentFilter("location update"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        //ads 24 hour check code

        boolean  seter=false;

        Calendar c=Calendar.getInstance();
        Date today=c.getTime();


        SharedPreferences setting=getSharedPreferences(crt_dt,0);
        String rewardDate="";
        rewardDate=setting.getString(aftr_dt,"");

        SimpleDateFormat sdfx= new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");

        Date rewardDt;



        try {

            rewardDt=  sdfx.parse(rewardDate);

            //mainLine
            seter= today.before(rewardDt);//dont display ads



        } catch (ParseException e) {
            e.printStackTrace();



          //  Toast.makeText(this,"Exception "+e,Toast.LENGTH_LONG).show();

        }




        if (seter==true){


           // Toast.makeText(this,"Ad Closed for 24 hours",Toast.LENGTH_SHORT).show();
        }
        else {

            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-3528391977169031/3226847034");
           // interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
            interstitialAd.loadAd(new AdRequest.Builder().build());

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();

                    //interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitialAd.show();
                }
            });

        }






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_counter_gui);
               textView=findViewById(R.id.showLiveDis);
          runMoney=findViewById(R.id.runTotalCost);
        runFuelCount=findViewById(R.id.runTotalFuelCount);


         starttrk=findViewById( R.id.btnStartTrack);

        stoptrk=findViewById( R.id.btnStopTrack);

         starttrk.setEnabled(false);
        stoptrk.setEnabled(false);




//check for permission ,request for permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)==getPackageManager().PERMISSION_GRANTED){
          //  Toast.makeText(this,"Permission Granted ",Toast.LENGTH_SHORT).show();

            starttrk.setEnabled(true);

                stoptrk.setEnabled(true);



        }
        else {
            reqPermi();

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode==STORAGE_PERMISSION_CODE){

            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {


            //    Toast.makeText(this,"Permission Granted!!  ",Toast.LENGTH_SHORT).show();
                starttrk.setEnabled(true);


            }
            else {
                Toast.makeText(this,"Permission Not Granted!! Please Allow the permission  ",Toast.LENGTH_SHORT).show();


            }
        }
    }
//permission request method
    public void reqPermi()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.ACCESS_FINE_LOCATION)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed!!")
                    .setMessage("Permission require for calculating the trip distance!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(TripCounterGUI.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},STORAGE_PERMISSION_CODE);

                        }
                    }).create().show();
        }
        else{

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},STORAGE_PERMISSION_CODE);

        }

    }

    //////////////////////////////////////////////////////////////////////////////////new code//////////////////////////



    public void StartService(View view){

        runMoney.setText("");
        runFuelCount.setText("");



        starttrk.setEnabled(false);
        stoptrk.setEnabled(true);

        Intent serviceIntent=new Intent(this,OdometerService.class);

      //  startService(seviceIntent);


        ContextCompat.startForegroundService(this,serviceIntent);

        try {
            //textView.setText(""+coverdistance+"km");

            textView.setText( String.format(" %.3f km",coverdistance));


        }catch (Exception er){

            Log.d("exception",""+er);
        }

    }


    public void StopService(View view){
        starttrk.setEnabled(true);
        stoptrk.setEnabled(false);

        Intent serviceIntent=new Intent(this,OdometerService.class);
          stopService(serviceIntent);
          //by using putExtra you can send data here!!
        try {
            //textView.setText(""+coverdistance+"km");

            textView.setText( String.format(" %.3f km",coverdistance));


        }catch (Exception er){

            Log.d("exception",""+er);
        }

        openDialouge();
        }

        public void openDialouge(){

        Input_dialouge input_dialouge=new Input_dialouge();
        input_dialouge.show(getSupportFragmentManager(),"input_dialouge");


        }

    @Override
    public void applyData(String runConsump, String runFuelCost) {
        double answer,totalfuel;
        double x, y, z;

        try {
            Runcunsumption=  Double.parseDouble(runConsump);
            RunTotalFuel= Double.parseDouble(runFuelCost);
        } catch (Exception e) {
            e.printStackTrace();
        }

         x=Runcunsumption;
        y=RunTotalFuel;
        z=coverdistance;

               try{
                   x=100/x;
                   answer = (z * x) / 100;
                   answer = answer * y;

                   totalfuel = (z * x) / 100;

                   runMoney.setText( String.format("Total cost for this trip : %.2f currency unit",answer));
                   runFuelCount.setText( String.format("Total Fuel count for this trip: %.2f  Liters",totalfuel));


                 //  runMoney.setText("Total cost for this trip : "+answer+"  currency unit");
                  // runFuelCount.setText("Total Fuel count for this trip:"+totalfuel+" Liters");


               }
               catch (Exception caci)
               {

                   Toast.makeText(this, "Please Enter Correct Values!", Toast.LENGTH_SHORT).show();
               }



    }
}
