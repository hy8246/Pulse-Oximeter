package edu.wit.mobileapp.listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login  extends AppCompatActivity
{
    EditText user;
    Button loginBtn;
    Button exitBtn;
    Button register;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    loginBtn = (Button) findViewById(R.id.login_button);
    exitBtn = (Button) findViewById(R.id.exitBtn);
    register=(Button)findViewById(R.id.register_button);

        String path= "/data/data/"+getPackageName()+"/user_store.db";
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        String sql= "CREATE TABLE IF NOT EXISTS username(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,sex INTEGER, age INTEGER, date TEXT, averagehb INTEGER );";
        db.execSQL(sql);
//        Simulate the input
//        ContentValues newValues= new ContentValues();
//        String[] nameData = {"john","john","john","eric","eric"};
//        int[] sex= {1,1,1,1,1};
//        int[] age={23,23,23,21,21};
//        String[] date ={"08/01/2016","07/26/2016","07/24/2016","07/23/2016","07/21/2016"};
//        int[] heartbeat={70,68,80,65,59};
//
//        for(int i =0;i<5;i++)
//        {
//            newValues.put("name",nameData[i]);
//            newValues.put("sex",sex[i]);
//            newValues.put("age",age[i]);
//            newValues.put("date",date[i]);
//            newValues.put("averagehb",heartbeat[i]);
//
//            db.insert("username",null,newValues);
//        }

        loginBtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v){
                user = (EditText)findViewById(R.id.username_input);
                String input = user.getText().toString().toLowerCase();



                String[] selectColumn={"name"};
                String where = "name LIKE '"+input+"'";

                // cursor=db.query("table_name",columns, where, where_args, group_by, having, order_by);
                Cursor cursor = db.query("username", selectColumn, where,null,null,null,null);
                if(cursor.moveToNext()) {
                    Intent intent = new Intent();
                    intent.setClass(Login.this, Menu1.class);
                    Bundle b = new Bundle();
                    b.putString("username", String.valueOf(input));
                    intent.putExtras(b);
                    db.close();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"User doesn't exist, Please Register",Toast.LENGTH_LONG).show();

                }

            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);


            }
        });
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(Login.this, Register.class);
                startActivity(intent);

            }

        });
    }
}
