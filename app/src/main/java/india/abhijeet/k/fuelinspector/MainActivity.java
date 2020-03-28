package india.abhijeet.k.fuelinspector;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.security.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    public static final String crt_dt="crt_dt";
    public static final String aftr_dt="aftr_dt";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

   // Context mContext=MainActivity.this;
    SharedPreferences appPreferences;
    boolean isAppInstalled=false;

    AdView mainAdView;

  //  private static int SPLASH_TIME_OUT =1000;
  //ActionBar acb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        /*Creating Shortcut*/
        appPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        isAppInstalled=appPreferences.getBoolean("isAppInstalled",false);
        if (isAppInstalled==false)

        {
            dialogBox();
            Intent shortcutIntent=new Intent(getApplicationContext(),MainActivity.class);
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            Intent intent=new Intent();
                    intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"Fuel Inspector");
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.mipmap.ic_launcher));
                intent.setAction("android.action.INSTALL_SHORTCUT");
                getApplicationContext().sendBroadcast(intent);

                SharedPreferences.Editor editor=appPreferences.edit();
                editor.putBoolean("isAppInstalled",true);
                editor.commit();


        }


        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);


    //    acb =getSupportActionBar();
      //  acb.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff185d")));





        //Ads closing for 24 hours code

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



        }


        mainAdView=findViewById(R.id.mainAdview);



        if (seter==true){

                 mainAdView.setVisibility(View.GONE);
           // Toast.makeText(this,"Ad Closed for 24 hours",Toast.LENGTH_SHORT).show();
        }
        else {
            mainAdView.setVisibility(View.VISIBLE);

            //Banner Ad testing
           // MobileAds.initialize(this," ca-app-pub-3940256099942544~3347511713");

            //original:-
            MobileAds.initialize(this,"ca-app-pub-3528391977169031~9420405665");


           // AdRequest adRequest= new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

            //  original:-
            AdRequest   adRequest= new AdRequest.Builder().build();


            // Banner Ad
            mainAdView.loadAd(adRequest);


        }










    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();

        menuInflater.inflate(R.menu.about_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int abt=item.getItemId();

        if (abt==R.id.about_button)
        {
            dialogBox();

        }

        else if (abt==R.id.rateApp_button){

            try {

                Intent rate = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=india.abhijeet.k.fuelinspector"));
                     startActivity(rate);

            }catch (Exception e){


            }

            }


        else if (abt==R.id.shareButton){

            try {

               shareApp();

            }catch (Exception e){


            }

        }

        return super.onOptionsItemSelected(item);



    }

    public void shareApp(){

         Intent myintent =new Intent(Intent.ACTION_SEND);
          myintent.setType("text/plain");
          String Body="Get Fuel Inspector calculate the cost,distance,total fuel";
          String sub="Get Fuel Inspector calculate the cost,distance,total fuel \n  https://play.google.com/store/apps/details?id=india.abhijeet.k.fuelinspector";
          myintent.putExtra(Intent.EXTRA_SUBJECT,Body);
        myintent.putExtra(Intent.EXTRA_TEXT,sub);
        startActivity(Intent.createChooser(myintent,"Share via "));

    }








    public void dialogBox()
    {


        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("About us");
        builder.setMessage("Thank you for installing the \n Fuel Inspector v1.1.0 \n Application developed by Abhijeet Kadam.");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.create();


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });


        builder.show();

    }


    @Override
    public void onBackPressed() {


        final AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);

        builder1.setTitle("Fuel Inspector");
        builder1.setMessage("Do you want to exit from this application?");
        builder1.setIcon(R.mipmap.ic_launcher);
        builder1.setCancelable(true);
        builder1.create();


        builder1.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


          finish();

            }
        });

        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        builder1.show();
    }
}
