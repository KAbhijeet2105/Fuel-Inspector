package india.abhijeet.k.fuelinspector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ElectricCalculator extends AppCompatActivity {
    public static final String crt_dt="crt_dt";
    public static final String aftr_dt="aftr_dt";
    private InterstitialAd interstitialAd;

    TextView econ;
    ToggleButton eleTogbtn;

boolean chk;
   // ActionBar acb;
    EditText Econsumption,EfuelUnitCost,EDistance,EMoney,ETotalFuelCost;

    boolean EleDistanceLock=true,EleMoneyLock=true,EleTotalFuelLock=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_calculator);


    //  original:-  MobileAds.initialize(this,"ca-app-pub-3528391977169031~9420405665");



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


            interstitialAd =new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-3528391977169031/3119444941");
          //  interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
            interstitialAd.loadAd(new AdRequest.Builder().build());


            interstitialAd.setAdListener(new AdListener(){
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























        //  acb =getSupportActionBar();
      //  acb.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff185d")));
                 econ=findViewById(R.id.txtELeCons);
            eleTogbtn=findViewById(R.id.EleBtntoggleMileage);
        Econsumption=findViewById(R.id.eleConsumption);
        EfuelUnitCost=findViewById(R.id.eleCostUnit);
        /* Last 3 add text watcher*/
        EDistance=findViewById(R.id.editEleDistance);
        EMoney=findViewById(R.id.editEleMoney);
        ETotalFuelCost=findViewById(R.id.editEleTotCost);



        /* Last 3 add text watcher start from here*/

        EDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (EleDistanceLock)
                {
                    ELEcalculateDistance();

                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        EMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (EleMoneyLock)
                {
                    ELEcalculateMoney();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        ETotalFuelCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (EleTotalFuelLock)
                {
                    ELEcalculateTotalFuel();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

   public void togEle(View v)
   {



       if (eleTogbtn.isChecked())
       {
           chk=true;
         //  Toast.makeText(this,"Checked!   ",Toast.LENGTH_SHORT).show();
           econ.setText("Mileage of your vehicle(km/miles)");
           Econsumption.setHint("km/miles");

       }
       else {
         //  Toast.makeText(this," not Checked!   ",Toast.LENGTH_SHORT).show();
           chk=false;
           econ.setText("Consumption per 100(km/miles)");
           Econsumption.setHint("kWh");
       }



   }







    public void ELEcalculateDistance()
    {

        EleDistanceLock=true;
        EleMoneyLock=false;
        EleTotalFuelLock=false;

        try {
            double answer,totalfuel;
            double x, y, z;

            x = Double.parseDouble(Econsumption.getText().toString().trim());
            y = Double.parseDouble(EfuelUnitCost.getText().toString().trim());
            z = Double.parseDouble(EDistance.getText().toString().trim());

            if (chk)
            {
                x=100/x;


            }


            answer = (z * x) / 100;
            answer = answer * y;

            totalfuel=(z*x)/100;


            if(EDistance.getText().toString()==""||EMoney.getText().toString().trim()==null)
            {

                ETotalFuelCost.setText(""+0);

                EMoney.setText(""+0);
            }


            EMoney.setText(""+answer);
            ETotalFuelCost.setText(""+totalfuel);


        }catch (Exception e)

        {

            //   Toast.makeText(StaticDistance.this,"Exception:"+e,Toast.LENGTH_LONG).show();
        }


        resetLocks();

    }







    public void ELEcalculateMoney(){


        EleDistanceLock=false;
        EleMoneyLock=true;
        EleTotalFuelLock=false;



        try {
            double distanceans,totalfuel,milage;
            double x, y, z;


            x = Double.parseDouble(Econsumption.getText().toString().trim());
            y = Double.parseDouble(EfuelUnitCost.getText().toString().trim());
            z = Double.parseDouble(EMoney.getText().toString().trim());

            if (chk)
            {
                x=100/x;


            }


            totalfuel=z/y;

            milage=100/x;

            distanceans=totalfuel*milage;

            if(EMoney.getText().toString()==""||EMoney.getText().toString().trim()==null)
            {

                EDistance.setText(""+0);

                ETotalFuelCost.setText(""+0);
            }


            EDistance.setText(""+distanceans);

            ETotalFuelCost.setText(""+totalfuel);


        }catch (Exception e)

        {

            //   Toast.makeText(StaticDistance.this,"Exception:"+e,Toast.LENGTH_LONG).show();
        }



        resetLocks();


    }







    public void ELEcalculateTotalFuel()
    {
        EleDistanceLock=false;
        EleMoneyLock=false;
        EleTotalFuelLock=true;


        try {
            double answer,distans;
            double x, y, z;



            x = Double.parseDouble(Econsumption.getText().toString().trim());
            y = Double.parseDouble(EfuelUnitCost.getText().toString().trim());
            z = Double.parseDouble(ETotalFuelCost.getText().toString().trim());



            if (chk)
            {
                x=100/x;


            }

            answer = z*y;

            distans=x*y;
            distans=(100*answer)/distans;


            if(ETotalFuelCost.getText().toString()==""||EMoney.getText().toString().trim()==null)
            {

                EDistance.setText(""+0);

                EMoney.setText(""+0);
            }


            EMoney.setText(""+answer);
            EDistance.setText(""+distans);

        }catch (Exception e)

        {

            //  Toast.makeText(StaticDistance.this,"Exception:"+e,Toast.LENGTH_LONG).show();
        }




        resetLocks();
    }








































    public void resetLocks()
    {

        EleDistanceLock=true;
        EleMoneyLock=true;
        EleTotalFuelLock=true;


    }


    public void ClearAll()
    {


        Econsumption.setText("");
        EfuelUnitCost.setText("");

        EDistance.setText("");
        EMoney.setText("");
        ETotalFuelCost.setText("");


        Econsumption.requestFocus();

    }





    public void ClearUpperUnits(View v)
    {


        Econsumption.setText("");
        EfuelUnitCost.setText("");

    }

    public void ClearDownUnit(View v)
    {

        EDistance.setText("");
        EMoney.setText("");
        ETotalFuelCost.setText("");



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();

        menuInflater.inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int abt=item.getItemId();

        if (abt==R.id.menu_info)
        {

            dialogBox();
        }


        else if(abt==R.id.menu_reset){


            ClearAll();


        }

        else if(abt==R.id.menu_helpbtn){


            helpdialog();


        }


        return super.onOptionsItemSelected(item);



    }


    public void helpdialog()
    {


        final AlertDialog.Builder builder=new AlertDialog.Builder(ElectricCalculator.this);

        builder.setTitle("Help");
        builder.setMessage("If electricity costs $0.11 per kWh(per unit cost) \n and the vehicle consumes \n" +

                "34 kWh to travel 100 miles(Consumption of vehicle per 100miles).  If \n" +

                "electricity costs $0.11 per kilowatt-hour, \ncharging an all-electric vehicle \n" +

                "with a 70-mile range(Distance)will cost about $2.618 (cost of the trip)\n and it will use 23.8kWh electricity.");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.create();


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });


        builder.show();




    }




















    public void dialogBox()
    {


        final AlertDialog.Builder builder=new AlertDialog.Builder(ElectricCalculator.this);

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
