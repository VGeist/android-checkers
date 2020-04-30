package com.victorgeist.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

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
                LoadGame(1);
            }
        });

        mLoadSlot2 = findViewById(R.id.load_slot2_button);
        mLoadSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadGame(2);
            }
        });

        mLoadSlot3 = findViewById(R.id.load_slot3_button);
        mLoadSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadGame(3);
            }
        });
    }

    private Game GetGameFromFile(String file) {
        try {
            FileInputStream fileIn = openFileInput(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Game game = (Game) in.readObject();
            in.close();
            fileIn.close();
            return game;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void LoadGame(int slotNum) {
        String fileName = "slot" + slotNum + ".ser";
        Game loadedGame = GetGameFromFile(fileName);
        if (loadedGame != null) {
            // game exists, load it as the activeGame
            Game.get().LoadGame(loadedGame);

            // open gameActivity
            Intent intent = new Intent(LoadActivity.this, GameActivity.class);
            startActivity(intent);

            // remove loadActivity form backstack
            finish();

            // loaded successfully message
            Toast.makeText(LoadActivity.this, "Game Loaded", Toast.LENGTH_SHORT).show();
        } else {
            // error occurred do not move to game activity, display error message
            Toast.makeText(LoadActivity.this, "An error occurred while loading the game.", Toast.LENGTH_SHORT).show();
        }
    }
}
