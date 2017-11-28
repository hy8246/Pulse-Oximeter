package edu.wit.mobileapp.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Register extends AppCompatActivity
{


    EditText name;
    EditText age;

    Button submit;
    RadioButton maleBtn;
    RadioButton femaleBtn;
    int gender =-1;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        submit= (Button)findViewById(R.id.submit_btn);
        maleBtn= (RadioButton)findViewById(R.id.male_radioBtn);
        femaleBtn=(RadioButton)findViewById(R.id.female_radioBtn);

        maleBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gender=1;
            }
        });
        femaleBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gender=0;
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View view)
            {
                name=(EditText)findViewById(R.id.usernameValue);
                age=(EditText)findViewById(R.id.age_value);
                String nametemp=name.getText().toString().toLowerCase();

                Intent intent = new Intent();
                intent.setClass(Register.this, Menu1.class);
                Bundle bundle = new Bundle();


                bundle.putString("username", String.valueOf(nametemp));
                bundle.putInt("age", Integer.parseInt(age.getText().toString()));
                bundle.putInt("gender",gender);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }





}