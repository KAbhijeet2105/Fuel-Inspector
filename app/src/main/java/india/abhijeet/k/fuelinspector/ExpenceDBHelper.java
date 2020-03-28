package india.abhijeet.k.fuelinspector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class ExpenceDBHelper {


    public static final String RecordId="RecordId";
    public static final String Type="Type";
    public static final String Date="Date";
    public static final String Amount="Amount";
    public static final String TotalKm="TotalKm";
    public static final String Remark="Remark";


    public static final String databasename="ExpenseDB";
    public static final String tablename="expense";
    public static final String databaseversion="1";



    public static final String create_table ="create TABLE expense("+RecordId+"integer primary key autoincrement,"+Type+"text not null,"+Date+" text not null,"+Amount+" text not null,"+TotalKm+" text,"+Remark+" text);";

     private final Context ct;
     private DatabaseHelper dbhelper;
      private SQLiteDatabase database;

     public ExpenceDBHelper(Context context)
     {
         this.ct=context;
         dbhelper=new DatabaseHelper(ct);

     }

     private class DatabaseHelper extends SQLiteOpenHelper
     {
Context ct;
DatabaseHelper(Context c)
{
    super(c,databasename,null, Integer.parseInt(databaseversion));
    ct=c;

}

         @Override
         public void onCreate(SQLiteDatabase database) {
             try {


                 database.execSQL(create_table);
                 Log.d("database","Table created!");
             }
             catch (Exception ec)
             {

                 ec.printStackTrace();
             }
         }

         @Override
         public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

     database.execSQL("drop table if exists expense");
     Toast.makeText(ct,"Upgrading database...",Toast.LENGTH_SHORT).show();

         }

         @Override
         public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
             database.execSQL("drop table if exists expense");
             onCreate(database);
             Toast.makeText(ct,"Downgrading database...",Toast.LENGTH_SHORT).show();

         }
     }//inner class



    public ExpenceDBHelper connect()throws SQLException
    {
        database=dbhelper.getWritableDatabase();
        return this;


    }


    public void disconnect()
    {

        dbhelper.close();

    }


    public long insertRecord(String type,String date, String amt, String totalKm, String remark)
    {
        this.connect();

        ContentValues cv= new ContentValues();
        cv.put(Type,type);
        cv.put(Date,date);
        cv.put(Amount,amt);
        cv.put(TotalKm,totalKm);
        cv.put(Remark,remark);

        //Toast.makeText(this," \n Type= "+type+"\n date:"+date

          //      +"\nAmount:"+amt+"\nKilometers:"+totalKm+"\nRemark:"+remark+" ",Toast.LENGTH_LONG).show();



        return database.insert(tablename,null,cv);


    }


    public Cursor retriveExpences()
    {

        this.connect();
        return database.query(tablename,new String[]{RecordId,Type,Date,Amount,TotalKm,Remark},null,null,null,null,null);

    }

public boolean deleteRecord(long id)
{

    this.connect();
    return database.delete(tablename,RecordId+"="+id,null)>0;

}

public boolean updateRecord(long id,String type,String date, String amt, String totalKm, String remark)
{
    this.connect();
    ContentValues cvalues= new ContentValues();
    cvalues.put(Type,type);
    cvalues.put(Date,date);
    cvalues.put(Amount,amt);
    cvalues.put(TotalKm,totalKm);
    cvalues.put(Remark,remark);


    return database.update(tablename,cvalues,RecordId+"="+id,null)>0;

}


}//outer class
