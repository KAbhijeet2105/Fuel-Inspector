package india.abhijeet.k.fuelinspector;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RewardVideoActivity extends AppCompatActivity {
    Button rewardbtn;
    public static final String crt_dt="crt_dt";
    public static final String aftr_dt="aftr_dt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);

        final SharedPreferences setting=getSharedPreferences(crt_dt,0);


        final RewardedVideoAd rewardedVideoAd= MobileAds.getRewardedVideoAdInstance(this);

        rewardedVideoAd.loadAd("ca-app-pub-3528391977169031/8134616837",new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

        rewardbtn=findViewById(R.id.btnRemoveAds);
        MobileAds.initialize(this);






        rewardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (rewardedVideoAd.isLoaded())
                 {
                     rewardedVideoAd.show();

                 }else{

                     rewardedVideoAd.loadAd("ca-app-pub-3528391977169031/8134616837",new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
                 }


            }
        });



        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

                Toast.makeText(RewardVideoActivity.this,"You have to watch complete video",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onRewarded(RewardItem rewardItem) {


                SimpleDateFormat sdfx= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                Calendar c=Calendar.getInstance();
               // Date today=c.getTime();

             //   c.add(Calendar.HOUR,24);
                c.add(Calendar.DATE,1);
                Date tomarow=c.getTime();


                setting.edit()

                        .putString(aftr_dt,tomarow.toString())

                        .commit();

                Toast.makeText(RewardVideoActivity.this,"Ads Closed for 1 day"+tomarow,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(RewardVideoActivity.this,"Please Check the internet connection!",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onRewardedVideoCompleted() {





            }
        });







    }


}
