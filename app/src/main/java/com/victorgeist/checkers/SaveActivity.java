package com.victorgeist.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveActivity extends AppCompatActivity {
    // files names for each slot
    private final static String mSlot1 = "slot1.ckr";
    private final static String mSlot2 = "slot2.ckr";
    private final static String mSlot3 = "slot3.ckr";

    // buttons that save to the corresponding slot
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
                Save(Game.get(), mSlot1);
                Toast.makeText(SaveActivity.this, "Game Saved in slot 1", Toast.LENGTH_LONG).show();
            }
        });

        mSaveSlot2 = findViewById(R.id.save_slot2_button);
        mSaveSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save game to slot 2
                Save(Game.get(), mSlot2);
                Toast.makeText(SaveActivity.this, "Game Saved in slot 2", Toast.LENGTH_LONG).show();
            }
        });

        mSaveSlot3 = findViewById(R.id.save_slot3_button);
        mSaveSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save game to slot 3
                Save(Game.get(), mSlot3);
                Toast.makeText(SaveActivity.this, "Game Saved in slot 3", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Save(Game game, String saveSlot) {
        // TODO: Save game object as string
        String gameString = game.getTurn();
        try {
            FileOutputStream writer = openFileOutput(saveSlot, MODE_PRIVATE);
            writer.write(gameString.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
