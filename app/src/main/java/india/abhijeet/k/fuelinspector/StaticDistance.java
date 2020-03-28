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
import android.widget.Button;
import android.widget.CompoundButton;
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

public class StaticDistance extends AppCompatActivity {

    public static final String crt_dt="crt_dt";
    public static final String aftr_dt="aftr_dt";

    private InterstitialAd interstitialAd;

    // Banner Ad AdView myadview1;
    boolean chk;
 ToggleButton togMileage;
     TextView cons;

     EditText consumption,fuelUnitCost,Distance,Money,TotalFuelCost;

        boolean DistanceLock=true,MoneyLock=true,TotalFuelLock=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_distance);

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









     //   original Banner ad:- MobileAds.initialize(this,"ca-app-pub-3528391977169031~9420405665");

        //testing



       if (seter==true){


        //   Toast.makeText(this,"Ad Closed for 24 hours",Toast.LENGTH_SHORT).show();
       }
       else {


           interstitialAd =new InterstitialAd(this);
           interstitialAd.setAdUnitId("ca-app-pub-3528391977169031/5957361273");
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






        consumption=findViewById(R.id.editConsumption);
        fuelUnitCost=findViewById(R.id.editCostPerFuel);
        Distance=findViewById(R.id.editDistance);
        Money=findViewById(R.id.editMoney);
        TotalFuelCost=findViewById(R.id.editTotalFuel);

        cons=findViewById(R.id.txtViewConsum);
         togMileage=findViewById(R.id.toggleMileage);






         Distance.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

                 if (DistanceLock)
                 {

                     calculateDistance();
                 }


             }

             @Override
             public void afterTextChanged(Editable s) {





             }
         });







         Money.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {


                     if (MoneyLock)
                         {
                                calculateMoney();

                              }


             }

             @Override
             public void afterTextChanged(Editable s) {


             }
         });




         TotalFuelCost.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {


                   if (TotalFuelLock)
                   {
                       calculateTotalFuel();
                   }


             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });











    }


    public void tog(View v){


        if (togMileage.isChecked())
        {
            chk=true;
           // Toast.makeText(this,"Checked!   ",Toast.LENGTH_SHORT).show();
            cons.setText("Mileage of your vehicle(km/miles)");

        }
        else {
           // Toast.makeText(this," not Checked!   ",Toast.LENGTH_SHORT).show();
            chk=false;

            cons.setText("Consumption per 100(km/miles)");
        }


    }





    /*Calculate Money and Total Fuel count by Distance*/

      public void calculateDistance()
      {

           DistanceLock=true;
           MoneyLock=false;
           TotalFuelLock=false;

          try {
              double answer,totalfuel,realconsum;
              double x, y, z;



              x = Double.parseDouble(consumption.getText().toString().trim());
              y = Double.parseDouble(fuelUnitCost.getText().toString().trim());
              z = Double.parseDouble(Distance.getText().toString().trim());

             if (chk)
             {
                 x=100/x;


             }

                 answer = (z * x) / 100;
                 answer = answer * y;

                 totalfuel = (z * x) / 100;

                 Money.setText(""+answer);
                 TotalFuelCost.setText(""+totalfuel);


              if(Distance.getText().toString()==""||Money.getText().toString().trim()==null)
              {

                  TotalFuelCost.setText(""+0);

                  Money.setText(""+0);
              }




            //  Toast.makeText(StaticDistance.this,"answer:"+answer,Toast.LENGTH_LONG).show();

          }catch (Exception e)

          {

           //   Toast.makeText(StaticDistance.this,"Exception:"+e,Toast.LENGTH_LONG).show();
          }


          reset();

      }




      /*Calculate Distance and Total Fuel count by Money*/



      public void calculateMoney(){


          DistanceLock=false;
          MoneyLock=true;
          TotalFuelLock=false;



          try {
              double distanceans,totalfuel,milage;
              double x, y, z,additionCunsumAndPerunit;





              x = Double.parseDouble(consumption.getText().toString().trim());
              y = Double.parseDouble(fuelUnitCost.getText().toString().trim());
              z = Double.parseDouble(Money.getText().toString().trim());

              if (chk)
              {
                  x=100/x;


              }

              totalfuel=z/y;

              milage=100/x;

              distanceans=totalfuel*milage;

              // additionCunsumAndPerunit=x*y;
              //distanceans = (100 * z) / additionCunsumAndPerunit;


              if(Money.getText().toString()==""||Money.getText().toString().trim()==null)
              {

                  Distance.setText(""+0);

                  TotalFuelCost.setText(""+0);
              }



              Distance.setText(""+distanceans);

              TotalFuelCost.setText(""+totalfuel);
           //   Toast.makeText(StaticDistance.this,"Distance:"+distanceans,Toast.LENGTH_SHORT).show();

          }catch (Exception e)

          {

           //   Toast.makeText(StaticDistance.this,"Exception:"+e,Toast.LENGTH_LONG).show();
          }



          reset();


      }




    /*Calculate Distance and Money count by Total Fuel */

      public void calculateTotalFuel()
      {
          DistanceLock=false;
          MoneyLock=false;
          TotalFuelLock=true;


          try {
              double answer,distans;
              double x, y, z;



              x = Double.parseDouble(consumption.getText().toString().trim());
              y = Double.parseDouble(fuelUnitCost.getText().toString().trim());
              z = Double.parseDouble(TotalFuelCost.getText().toString().trim());


              if (chk)
              {
                  x=100/x;


              }


              answer = z*y;

             distans=x*y;
               distans=(100*answer)/distans;


              if(TotalFuelCost.getText().toString()==""||Money.getText().toString().trim()==null)
              {

                  Distance.setText(""+0);

                  Money.setText(""+0);
              }





              Money.setText(""+answer);
             Distance.setText(""+distans);
           //   Toast.makeText(StaticDistance.this,"answer:"+answer,Toast.LENGTH_LONG).show();

          }catch (Exception e)

          {

            //  Toast.makeText(StaticDistance.this,"Exception:"+e,Toast.LENGTH_LONG).show();
          }




          reset();
      }














 public void reset()
 {

     DistanceLock=true;
     MoneyLock=true;
     TotalFuelLock=true;


 }



    public void ClearAll()
    {

        Distance.setText("");
        Money.setText("");
        TotalFuelCost.setText("");
        consumption.setText("");
        fuelUnitCost.setText("");

        consumption.requestFocus();
    }









    public void ClearDistanceMoneyTotFuel(View v)
    {

        Distance.setText("");
        Money.setText("");
        TotalFuelCost.setText("");
    }

    public void ClearConsumCostperUnit(View v)
    {

        consumption.setText("");
        fuelUnitCost.setText("");
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


        final AlertDialog.Builder builder=new AlertDialog.Builder(StaticDistance.this);

        builder.setTitle("Help");
        builder.setMessage("If fuel costs $1.22 per liter(per unit cost) \n and the vehicle consumes \n" +

                "2.63 liters to travel 100 miles(Consumption of vehicle per 100miles). \n If " +

                " the vehicle travels with the distance of  140km (Distance) \n will cost about $4.492 (cost of the trip) \n and it will use 3.682 liters of fuel\n" +
                "(Total fuel count)");
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


        final AlertDialog.Builder builder=new AlertDialog.Builder(StaticDistance.this);

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
