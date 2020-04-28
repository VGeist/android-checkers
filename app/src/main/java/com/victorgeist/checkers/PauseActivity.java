package com.victorgeist.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PauseActivity extends AppCompatActivity {
    private Button mSaveButton;
    private Button mOptionsButton;
    private Button mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        mSaveButton = findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, SaveActivity.class);
                startActivity(intent);
            }
        });

        mResetButton = findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // restart game
                Game.get().newGame();
                Toast.makeText(PauseActivity.this, "Game Restarted", Toast.LENGTH_SHORT).show();
            }
        });

        mOptionsButton = findViewById(R.id.options_button);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });
    }
}
