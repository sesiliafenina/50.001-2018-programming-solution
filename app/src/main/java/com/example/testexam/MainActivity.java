package com.example.testexam;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/* ATTENTION ------------------------
please put in your own package statement to get it to work in your android studio project
Easy way is to just copy-and-paste the code below to the android-generated file
ATTENTION --------
*/

import android.content.SharedPreferences;
import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //DO NOT MODIFY THE INSTANCE VARIABLE NAMES INCLUDED BELOW
    //YOU MAY ADD YOUR OWN


    EditText editTextFaceValue;
    EditText editTextSellingPrice;
    EditText editTextAnnualInterest;
    EditText editTextDuration;
    Button buttonCalculateYield;
    TextView textViewResult;
    final String sharedPrefFile = "sharedPref";
    SharedPreferences sharedPreferences;
    final String KEY_FACEVALUE = "faceValue";
    final String KEY_SELLINGPRICE = "sellingPrice";
    final String KEY_ANNUALINTEREST = "annualInterest";
    final String KEY_DURATION = "duration";
    final String EMPTY_STRING = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        editTextAnnualInterest = findViewById(R.id.editTextAnnualInterest);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextFaceValue = findViewById(R.id.editTextFaceValue);
        editTextSellingPrice = findViewById(R.id.editTextSellingPrice);
        buttonCalculateYield = findViewById(R.id.buttonCalculateYield);

        editTextDuration.setText(sharedPreferences.getString(KEY_DURATION, EMPTY_STRING));
        editTextAnnualInterest.setText(sharedPreferences.getString(KEY_ANNUALINTEREST, EMPTY_STRING));
        editTextFaceValue.setText(sharedPreferences.getString(KEY_FACEVALUE, EMPTY_STRING));
        editTextSellingPrice.setText(sharedPreferences.getString(KEY_SELLINGPRICE, EMPTY_STRING));

        buttonCalculateYield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // extract values from edittext
                String annualInterest = editTextAnnualInterest.getText().toString();
                String duration = editTextDuration.getText().toString();
                String faceValue = editTextFaceValue.getText().toString();
                String sellingPrice = editTextSellingPrice.getText().toString();

                try {
                    if (annualInterest.isEmpty() || duration.isEmpty() || faceValue.isEmpty() || sellingPrice.isEmpty()){
                        throw new IllegalArgumentException("String cannot be empty");
                    }

                    double newDuration = Double.parseDouble(duration);
                    double newAnnualInterest = Double.parseDouble(annualInterest);
                    double newFaceValue = Double.parseDouble(faceValue);
                    double newdSellingPrice = Double.parseDouble(sellingPrice); // can use Double.valueOf

                    // launch calculation using classes in part 1
                    try {
                        Bond b = new Bond.BondBuilder().setDuration(newDuration)
                                .setFaceValue(newFaceValue)
                                .setInterestPayment(newAnnualInterest)
                                .setSellingPrice(newdSellingPrice).createBond();
                        if (newAnnualInterest > 0){
                            b.setYieldCalculator(new ZeroCouponYield());
                        }
                        else{
                            b.setYieldCalculator(new WithCouponYield());
                        }

                        CaculatePayment cp = new CaculatePayment();
                        cp.execute(b);
                    }
                    catch (IllegalArgumentException e){
                        Toast.makeText(getApplicationContext(), "Invalid input has been entered", Toast.LENGTH_LONG).show();
                    }


                }
                catch(IllegalArgumentException e){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class CaculatePayment extends AsyncTask<Bond, Void, Double>{

        @Override
        protected Double doInBackground(Bond... bonds) {
            Double result = bonds[0].calculateYTM();
            return Math.round(result * 10000) * 100/1000000.0;
        }

        @Override
        protected void onPostExecute(Double d){
            super.onPostExecute(d);
            TextView result = findViewById(R.id.textViewResult);
            result.setText(d.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FACEVALUE, editTextFaceValue.getText().toString());
        editor.putString(KEY_SELLINGPRICE, editTextSellingPrice.getText().toString());
        editor.putString(KEY_DURATION, editTextDuration.getText().toString());
        editor.putString(KEY_ANNUALINTEREST, editTextAnnualInterest.getText().toString());
        editor.apply();
    }

}

