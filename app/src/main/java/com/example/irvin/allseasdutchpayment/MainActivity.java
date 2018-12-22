package com.example.irvin.allseasdutchpayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private SharedPreferences mSharedPreferences;
    private static final String PREF_NAME = "myPrefsFile";
    Date currentTime;



    //Calculation Fields
    private EditText basWag;
    private EditText loyBon;
    private TextView offView;
    private TextView libView;
    private TextView penView;
    private TextView totalView;
    private Button calcu;

    private float basicWage;
    private float lib;
    private float libFloat;
    private float offAll;
    private final float PENMED = 295;
    private float total;
    private String totalString;

    //Shift To next Activity Fields
    Button mNextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase connection
        /*mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("message");

        mDatabaseReference.setValue("Hello Firebase");
        mDatabaseReference.setValue("Whats up thou");*/

        basWag = (EditText) findViewById(R.id.basWag);
        loyBon = (EditText) findViewById(R.id.loyBon);
        offView = (TextView) findViewById(R.id.offView);
        libView = (TextView) findViewById(R.id.libView);
        penView = (TextView) findViewById(R.id.penView);
        totalView = (TextView) findViewById(R.id.totalView);
        calcu = (Button) findViewById(R.id.calcu);

        calcu.setOnClickListener(calc);

        //Next Activity
        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mNextBtn.setOnClickListener(nextAct);


        //Display  Saved Values
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, 0);

        if (prefs.contains("Date")) {
            String msg = prefs.getString("Date", "No Saved Date Available");
            Float basicSave = prefs.getFloat("savedBasic", 0);
            Float libSave = prefs.getFloat("savedLib", 0);
            basWag.setText(basicSave.toString());
            loyBon.setText(libSave.toString());
            Toast.makeText(getApplicationContext(), "Resume from: " + msg, Toast.LENGTH_LONG).show();
        }

    }

    View.OnClickListener calc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {



            if (basWag.getText().toString() != null) {
                basicWage = Float.parseFloat(basWag.getText().toString());
            } else {Toast.makeText(getApplicationContext(),"Please Enter Your Basic Wage", Toast.LENGTH_SHORT).show();
            return;}

            //Loyal

            if (loyBon.getText().toString() != null) {
                lib = Float.parseFloat(loyBon.getText().toString());
            } else {Toast.makeText(getApplicationContext(),"Please Enter Your LIB", Toast.LENGTH_SHORT).show();
            return;}

            libFloat = basicWage * lib/100;
            libView.setText(String.valueOf(libFloat));
            //Offshore
            offAll = basicWage * 0.45f;
            offView.setText(String.valueOf(offAll));
            //Pension and Med
            penView.setText(String.valueOf(PENMED));

           total = (basicWage + libFloat + PENMED + offAll);
           totalString = Float.toString(total);
           totalView.setText(totalString);

            //Saving values for next launch
            mSharedPreferences = getSharedPreferences(PREF_NAME, 0);
            SharedPreferences.Editor editor = mSharedPreferences.edit();

            currentTime = Calendar.getInstance().getTime();

            String date = currentTime.toString();

            Toast.makeText(getApplicationContext(), "Saved at: " + date, Toast.LENGTH_LONG).show();

            editor.putString("Date", date);
            editor.putFloat("savedBasic", basicWage);
            editor.putFloat("savedLib", lib);
            editor.commit();

        }
    };
//Total calculation
    View.OnClickListener nextAct = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (totalView != null) {
                Intent nextIntent = new Intent(MainActivity.this, Main2Activity.class);
                String offSalary = offView.getText().toString();
                String totalSalary = totalView.getText().toString();

                Bundle bundle = new Bundle();

                bundle.putString("offWhole", offSalary);
                bundle.putString("wholeSalary", totalSalary);

                nextIntent.putExtras(bundle);

                startActivity(nextIntent);
            } else if (totalView == null) {
                Toast.makeText(getApplicationContext(), "Please Enter Your basic wage and LIB", Toast.LENGTH_LONG).show();
            }

            }
        };
    }
