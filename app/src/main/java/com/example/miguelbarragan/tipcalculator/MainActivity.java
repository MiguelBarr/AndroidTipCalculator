package com.example.miguelbarragan.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener
{
    //WIDGET VARIABLES
    private EditText editTextBillAmount;
    private SeekBar  seekbarPercentage;

    //TEXT VIEW VARIABLES
    private TextView textViewBillAmount;
    private TextView textViewPercentage;
    private TextView textViewTip;
    private TextView textViewTotal;

    //CALCULATION VARIABLES
    private double billAmount = 0.0;
    private double percent    = 0.0;

    //set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat  = NumberFormat.getPercentInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBillAmount = (EditText) findViewById(R.id.user_amount);
        editTextBillAmount.addTextChangedListener(this); //(TextWatcher)

        textViewBillAmount = (TextView) findViewById(R.id.textView_BillAmount);
        textViewPercentage = (TextView) findViewById(R.id.percentage_view);
        textViewTip        = (TextView) findViewById(R.id.tip_view);
        textViewTotal      = (TextView) findViewById(R.id.bill_total);

        seekbarPercentage  = (SeekBar) findViewById(R.id.percent_bar);
        seekbarPercentage.setOnSeekBarChangeListener(this);
        seekbarPercentage.setMax(100);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= "+charSequence);
        //surround risky calculations with try catch (what if billAmount is 0 ?
        //charSequence is converted to a String and parsed to a double for you
        try
        {
            Log.d("MainActivity", "Bill Amount = "+billAmount);
            billAmount = Double.parseDouble(charSequence.toString()) / 100;
            textViewBillAmount.setText(currencyFormat.format(billAmount));
            calculate();

        }
        catch (NumberFormatException e)
        {
            billAmount = 0.0;
            textViewBillAmount.setText(currencyFormat.format(billAmount));

        }
    }

    /**
     * onProgressChanged()
     * This method is used to track the progress of the seekbar. Its progress is used as the tip
     * percentage and utilized in calculating the tip and bill amount.
     *Progress is also used to display the tip inside the tip text view.
     * @param seekBar :takes in the SeekBar object used by the user to calculate tip.
     * @param progress :an int that tracks the progress of the seekbar object that is passed.
     * @param b :a boolean variable.
     */

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b)
    {
        textViewPercentage.setText(String.valueOf(progress) + "%");
        percent = (double) progress / 100.0; //calculate percent based on seeker value
        calculate();
    }

    /**
     * Calculate()
     *This method is used to calculate the tip and the bill total that is displayed
     * on the tip and bill text views respectively.
     */

    private void calculate()
    {
        Log.d("MainActivity", "inside calculate method");

        double tip       = billAmount * percent;
        double totalBill = billAmount + tip;

        textViewTip.setText(currencyFormat.format(tip));
        textViewTotal.setText(currencyFormat.format(totalBill));
    }

    //--------------------------------------------------------------------------------------------//
    //------------------------------------UNUSED METHODS------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

    @Override
    public void afterTextChanged(Editable editable){}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar){}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar){}

}
