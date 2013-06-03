package com.argentian.twiliotest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class TwilioTestActivity extends Activity implements View.OnClickListener{

    private TwilioTestPhone mTestPhone;
    private EditText mNumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twilio_test);

        mTestPhone = new TwilioTestPhone(this.getApplicationContext());

        ImageButton mDialButton = (ImageButton)findViewById(R.id.dialButton);
        mDialButton.setOnClickListener(this);

        ImageButton mHangupButton = (ImageButton)findViewById(R.id.hangupButton);
        mHangupButton.setOnClickListener(this);

        mNumberField = (EditText)findViewById(R.id.numberField);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.twilio_test, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.dialButton){
            mTestPhone.connect(mNumberField.getText().toString());
        }
        if(view.getId() == R.id.hangupButton){
            mTestPhone.disconnect();
        }
    }
}
