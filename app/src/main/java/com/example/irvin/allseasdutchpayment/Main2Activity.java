package com.example.irvin.allseasdutchpayment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText mDaysOff;
    TextView mTotalPayOff;
    float offsWhole;
    float wholesSalary;
    Button totCalcu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.days_worked);

        mDaysOff = (EditText) findViewById(R.id.daysOff);
        mTotalPayOff = (TextView) findViewById(R.id.totallyForTrip);
        totCalcu = (Button) findViewById(R.id.totCalcu);



        Bundle bundle = getIntent().getExtras();
        String offWhole = bundle.getString("offWhole");
        String wholeSalary = bundle.getString("wholeSalary");

        offsWhole = Float.parseFloat(offWhole);
        wholesSalary = Float.parseFloat(wholeSalary);


        Toast nextPage = Toast.makeText(getApplicationContext(),"Your Total Offshore allowance is CACHING: " + "\u20ac" + offWhole, Toast.LENGTH_LONG);
        nextPage.setGravity(Gravity.CENTER,0,0);
        nextPage.show();

        totCalcu.setOnClickListener(secondActCalc);

    }

    View.OnClickListener secondActCalc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            float offMoney = offsWhole / 30 * Float.parseFloat(mDaysOff.getText().toString());
            float money = wholesSalary - offsWhole + offMoney;

            String moneyString = Float.toString(money);
            mTotalPayOff.setText("\u20ac" + moneyString);
        }
    };
}
