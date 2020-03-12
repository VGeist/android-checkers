package com.victorgeist.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {
    private Button mApplyButton;
    private Button mDefaultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mApplyButton = findViewById(R.id.apply_button);
        mApplyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(OptionsActivity.this, "changes saved", Toast.LENGTH_SHORT).show();
            }
        });

        mDefaultsButton = findViewById(R.id.reset_to_default_button);
        mDefaultsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(OptionsActivity.this, "defaults restored", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
