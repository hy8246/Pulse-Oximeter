package edu.wit.mobileapp.listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    public TextView data_display;
    public TextView evaluation;
    public TextView username;
    public Button history_back_button;
    private Spinner date_selection;
    public static SQLiteDatabase db;
    public static Cursor cursor;
    public static String name;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        data_display=(TextView)findViewById(R.id.raw_data);
        //Get the Username from intent
        Bundle b = this.getIntent().getExtras();
        name= b.getString("username");
        username = (TextView)findViewById(R.id.history_username);
        username.setText(name);

        //create the data base
        String path= "/data/data/"+getPackageName()+"/user_store.db";
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
//        String sql= "CREATE TABLE IF NOT EXISTS username(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,sex INTEGER, age INTEGER, date TEXT, averagehb INTEGER );";
//        db.execSQL(sql);
        //Simulate the input
//        ContentValues newValues= new ContentValues();
//        String[] nameData = {"John","John","John","Eric","Eric"};
//        int[] sex= {1,1,1,1,1};
//        int[] age={23,23,23,21,21};
//        String[] date ={"08/01/2016","07/26/2016","07/24/2016","07/23/2016","07/21/2016"};
//        int[] heartbeat={70,68,80,65,59};

//        for(int i =0;i<3;i++)
//        {
//            newValues.put("name",nameData[i]);
//            newValues.put("sex",sex[i]);
//            newValues.put("age",age[i]);
//            newValues.put("date",date[i]);
//            newValues.put("averagehb",heartbeat[i]);
//
//            db.insert("username",null,newValues);
//        }

//        sql="DROP TABLE IF EXISTS username";
//        db.execSQL(sql);

        date_selection = (Spinner)findViewById(R.id.spinner);
        List<String> list= new ArrayList<String>();
        List<String> hbList=new ArrayList<String>();
        String[] selectColumn={"date","averagehb"};
        String where = "name LIKE '"+name+"'";

        // cursor=db.query("table_name",columns, where, where_args, group_by, having, order_by);
        Cursor cursor = db.query("username", selectColumn, where,null,null,null,null);

        while(cursor.moveToNext())
        {
            String data_date= cursor.getString(cursor.getColumnIndex("date"));
            list.add(data_date);

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_selection.setAdapter(dataAdapter);
        date_selection.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        BackBtn();



    }

    private void BackBtn() {
        Button BackBtn = (Button)findViewById(R.id.history_back_button);
        BackBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                db.close();
                Intent intents = new Intent();
                intents.setClass(History.this,Menu1.class);
                Bundle b = getIntent().getExtras();
                intents.putExtras(b);
                startActivity(intents);
    }

});


}}