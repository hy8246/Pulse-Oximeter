package edu.wit.mobileapp.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by nguyenn6 on 8/2/2016.
 */
public class Menu1 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);
        ConnectBtn();
        HistoryBtn();
        LogoutBtn();

    }

    private void LogoutBtn()
    {
        Button Logoutbtn = (Button) findViewById(R.id.logoutBtn);
        Logoutbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Menu1.this, Login.class));
            }
        });
    }

    private void HistoryBtn()
    {
        Button historybtn = (Button)findViewById(R.id.history_button);
        historybtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intents = new Intent();
                intents.setClass(Menu1.this,History.class);
                Bundle b = getIntent().getExtras();
                intents.putExtras(b);
                startActivity(intents);
            }
        });
    }

    private void ConnectBtn()
    {
        Button connectbtn = (Button)findViewById(R.id.connect_button);
        connectbtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                Intent intents= new Intent();
                intents.setClass(Menu1.this, Current.class);
                Bundle b = getIntent().getExtras();
                intents.putExtras(b);
                startActivity(intents);


            }
        });
    }
}
