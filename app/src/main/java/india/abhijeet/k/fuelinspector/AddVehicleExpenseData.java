package india.abhijeet.k.fuelinspector;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddVehicleExpenseData extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener{

    EditText edKms,edRemrks,edAmt;
    TextView edExpdt;
    String ExpType,Expencedate="",ExpAmt,kilometers="",remark="";
   Spinner expenceTypeSpin;
    ExpenceDBHelper expenceDBHelper;


   boolean set=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_expense_data);
//initializing field
        edExpdt=findViewById(R.id.txtExpDate);
        edKms=findViewById(R.id.editExpenseKilomiter);
        edRemrks=findViewById(R.id.editExpenseRemark);
        edAmt=findViewById(R.id.editExpenseAmount);
         expenceTypeSpin= findViewById(R.id.ExpenceTypeSpinr);

          expenceDBHelper =new ExpenceDBHelper(this);


           ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.types,android.R.layout.simple_spinner_item);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           expenceTypeSpin.setAdapter(adapter);
           expenceTypeSpin.setOnItemSelectedListener(this);


           edExpdt.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   DialogFragment datepicker= new DatePickerFragment();
                   datepicker.show(getSupportFragmentManager(),"date");
               }
           });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       if (position==0)
       {
           ExpType=null;
       }
       else {
           ExpType = parent.getItemAtPosition(position).toString().trim();

           Toast.makeText(parent.getContext(), "Selected: " + ExpType, Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        ExpType=null;
    }



    public void initilizer()
    {
        ExpAmt=edAmt.getText().toString();

        try{
        if (ExpType.equals(null)||Expencedate.equals(null)||ExpAmt.equals(null))
        {

             Toast.makeText(this,"Please enter the valid data",Toast.LENGTH_SHORT).show();
            set=false;
        }
        else {
            set=true;

        }}
        catch (Exception ins)
        {
            Toast.makeText(this,"Please enter the valid data",Toast.LENGTH_SHORT).show();


        }


        kilometers=edKms.getText().toString();
        remark=edRemrks.getText().toString();

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        Expencedate= DateFormat.getDateInstance().format(c.getTime());
        edExpdt.setText(""+Expencedate);


    }


    //data insert code here

    public void insertData(View view)
    {
        long id=0;
        initilizer();
        if (set==true)
        {
            try {

                 id=expenceDBHelper.insertRecord(ExpType,Expencedate,ExpAmt,kilometers,remark);

                Toast.makeText(this," \n Type= "+ExpType+"\n date:"+Expencedate

                        +"\nAmount:"+ExpAmt+"\nKilometers:"+kilometers+"\nRemark:"+remark+" ",Toast.LENGTH_LONG).show();

                       expenceDBHelper.disconnect();
                Toast.makeText(this,"Your Record has been saved!  at= "+id,Toast.LENGTH_SHORT).show();

            }
            catch (Exception e)
            {

                e.printStackTrace();
            }




        }
        else
        {
            initilizer();

        }


    }











}
