package com.victorgeist.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoadActivity extends AppCompatActivity {
    private Button mLoadSlot1;
    private Button mLoadSlot2;
    private Button mLoadSlot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        mLoadSlot1 = findViewById(R.id.load_slot1_button);
        mLoadSlot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadActivity.this, GameActivity.class);
                startActivity(intent);
                // load game info from storage
                Toast.makeText(LoadActivity.this, "loaded slot 1", Toast.LENGTH_SHORT).show();
            }
        });
        mLoadSlot2 = findViewById(R.id.load_slot2_button);
        mLoadSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadActivity.this, GameActivity.class);
                startActivity(intent);
                // load game info from storage
                Toast.makeText(LoadActivity.this, "loaded slot 2", Toast.LENGTH_SHORT).show();
            }
        });
        mLoadSlot3 = findViewById(R.id.load_slot3_button);
        mLoadSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadActivity.this, GameActivity.class);
                startActivity(intent);
                // load game info from storage
                Toast.makeText(LoadActivity.this, "loaded slot 3", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
