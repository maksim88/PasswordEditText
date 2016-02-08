package com.maksim88.passwordedittext.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.maksim88.passwordedittext.PasswordEditText;

public class MainActivity extends AppCompatActivity {

    Button submitButton;
    PasswordEditText pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton =  (Button)findViewById(R.id.submit_button);
        pwText = (PasswordEditText)findViewById(R.id.input_password);
    }
}
