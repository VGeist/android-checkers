package com.victorgeist.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SaveActivity extends AppCompatActivity {
    private Button mSaveSlot1;
    private Button mSaveSlot2;
    private Button mSaveSlot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        mSaveSlot1 = findViewById(R.id.save_slot1_button);
        mSaveSlot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save game to slot 1
                Toast.makeText(SaveActivity.this, "saved slot 1", Toast.LENGTH_LONG).show();
            }
        });
        mSaveSlot2 = findViewById(R.id.save_slot2_button);
        mSaveSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save game to slot 2
                Toast.makeText(SaveActivity.this, "saved slot 2", Toast.LENGTH_LONG).show();
            }
        });
        mSaveSlot3 = findViewById(R.id.save_slot3_button);
        mSaveSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save game to slot 3
                Toast.makeText(SaveActivity.this, "saved slot 3", Toast.LENGTH_LONG).show();
            }
        });
    }
}
