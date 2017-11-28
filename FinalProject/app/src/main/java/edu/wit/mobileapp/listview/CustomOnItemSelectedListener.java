package edu.wit.mobileapp.listview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.AdapterView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by haopan on 8/4/16.
 */
public class CustomOnItemSelectedListener implements OnItemSelectedListener {
    TextView evulation;
    TextView data_display;
    Cursor cursor;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String date_selected=parent.getItemAtPosition(position).toString();
        Log.v("myApp",date_selected);

        data_display=(TextView)view.getRootView().findViewById(R.id.raw_data);

        String data=null;
        String[] column_select={"sex","age","averagehb"};
        String where = "date LIKE '"+date_selected+"' AND name LIKE '"+History.name+"'";

        int sex=0, age=0, hb=0;
        cursor=History.db.query("username",column_select,where,null,null,null,null);
        if(cursor.moveToNext())
        {
            sex=cursor.getInt(cursor.getColumnIndex("sex"));
            age=cursor.getInt(cursor.getColumnIndex("age"));
            hb = cursor.getInt(cursor.getColumnIndex("averagehb"));
            data= "Daily average heart beat is equals to: "+ hb;
            Log.v("MyApp",data);
            Log.v("MyApp",sex+" "+age+" "+hb);
        }
        data_display.setText(data);
        EvaluationCalculate lab= new EvaluationCalculate(sex,age,hb);
        String evaluation_text =lab.calculate();
//
        evulation=(TextView)view.getRootView().findViewById(R.id.evaluation);
        evulation.setText(evaluation_text);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {

    }
}
