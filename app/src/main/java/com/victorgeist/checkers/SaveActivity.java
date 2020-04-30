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
import java.io.ObjectOutputStream;

public class SaveActivity extends AppCompatActivity {
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
                Save(Game.get(), 1);
                Toast.makeText(SaveActivity.this, "Game Saved in slot 1", Toast.LENGTH_LONG).show();
            }
        });

        mSaveSlot2 = findViewById(R.id.save_slot2_button);
        mSaveSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save game to slot 2
                Save(Game.get(), 2);
                Toast.makeText(SaveActivity.this, "Game Saved in slot 2", Toast.LENGTH_LONG).show();
            }
        });

        mSaveSlot3 = findViewById(R.id.save_slot3_button);
        mSaveSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save game to slot 3
                Save(Game.get(), 3);
                Toast.makeText(SaveActivity.this, "Game Saved in slot 3", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Save(Game game, int slotNum) {
        // TODO: Save game object as string
        String fileName = "slot" + slotNum + ".ser";
        try {
            // create fileOutput and objectOutput streams
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // save game
            out.writeObject(game);

            // close streams
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
