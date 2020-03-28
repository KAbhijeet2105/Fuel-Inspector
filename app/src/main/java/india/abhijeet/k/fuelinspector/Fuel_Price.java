package india.abhijeet.k.fuelinspector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.webkit.WebView.RENDERER_PRIORITY_BOUND;

public class Fuel_Price extends AppCompatActivity {

    public static final String crt_dt="crt_dt";
    public static final String aftr_dt="aftr_dt";

  //  ActionBar acb;

    private InterstitialAd interstitialAd;
    ProgressBar WebProgressBar;
    ImageView WebImgView;

    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel__price);
        WebView myWebView = findViewById(R.id.myWebView);
        WebProgressBar=findViewById(R.id.WebProBar);
        WebImgView=findViewById(R.id.WebImgView);

       // acb =getSupportActionBar();
       // acb.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff185d")));


        WebProgressBar.setMax(100);


        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);




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



           // Toast.makeText(this,"Exception "+e,Toast.LENGTH_LONG).show();

        }






        if (seter==true){


          //  Toast.makeText(this,"Ad Closed for 24 hours",Toast.LENGTH_SHORT).show();
        }else {


            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-3528391977169031/2516400591");
           // interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
            interstitialAd.loadAd(new AdRequest.Builder().build());

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();

                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitialAd.show();
                }
            });

        }



           myWebView.setWebViewClient(new WebViewClient());
             myWebView.setWebChromeClient(new WebChromeClient(){

                 @Override
                 public void onProgressChanged(WebView view, int newProgress) {
                     super.onProgressChanged(view, newProgress);

                     WebProgressBar.setProgress(newProgress);

                 }

                 @Override
                 public void onReceivedTitle(WebView view, String title) {
                     super.onReceivedTitle(view, title);

                     getSupportActionBar().setTitle("Fuel Price in India!");

                 }

                 @Override
                 public void onReceivedIcon(WebView view, Bitmap icon) {
                     super.onReceivedIcon(view, icon);
                     WebImgView.setImageBitmap(icon);

                 }
             });




        myWebView.loadUrl("https://www.mypetrolprice.com/petrol-price-in-india.aspx");



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


          else if (abt==R.id.shareButton)
          {
                     shareApp();

          }

        return super.onOptionsItemSelected(item);



    }

    public void shareApp(){

        Intent myintent =new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");
        String Body="Get Fuel Inspector";
        String sub="Get Fuel Inspector calculate the cost,distance,total fuel\n  https://play.google.com/store/apps/details?id=india.abhijeet.k.fuelinspector";
        myintent.putExtra(Intent.EXTRA_SUBJECT,Body);
        myintent.putExtra(Intent.EXTRA_TEXT,sub);
        startActivity(Intent.createChooser(myintent,"Share via "));

    }






    public void dialogBox()
    {


        final AlertDialog.Builder builder=new AlertDialog.Builder(Fuel_Price.this);

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



}
