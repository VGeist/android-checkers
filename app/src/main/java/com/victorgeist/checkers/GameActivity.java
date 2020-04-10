package com.victorgeist.checkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class GameActivity extends AppCompatActivity {
    // game objects
    private Game mActiveGame;
    private Piece mSelectedPiece;

    /*
        only including buttons needed for CHECKERS not all 64 tiles to keep the file size easier to manage
        tiles are identified by 1 letter and 1 number: A1 (column/file 1, row/rank 1)
        columns counted from left to right, rows counted from bottom to top
     */
    private ImageButton[][] mGameTiles = new ImageButton[8][8];

    // other objects
    private Button mPauseButton;
    private RecyclerView mLogRecyclerView;
    private LogAdapter mLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActiveGame = Game.get(GameActivity.this);

        setContentView(R.layout.activity_game);

        // retrieve piece placement information and display pieces
        AddGameBoardButtons();

        // log recycler view
        mLogRecyclerView = findViewById(R.id.game_log_recycler_view);
        mLogRecyclerView.setLayoutManager(new LinearLayoutManager(GameActivity.this));
        UpdateLogUI();

        // pause menu button
        mPauseButton = findViewById(R.id.pause_button);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, PauseActivity.class);
                startActivity(intent);
            }
        });
    }

    // changes image resource and sets disabled if there is a piece at the specified coordinates
    // coordinates given should always reflect the coordinates of the button
    private void UpdateGameTile(final int rowPos, final int colPos) {
        // retrieve button
        try {
            ImageButton button = mGameTiles[rowPos][colPos];

            // check if there is a piece
            if(mActiveGame.tileHasPiece(rowPos, colPos)) {
                // get the residing piece
                Piece piece = mActiveGame.getPieceAtTile(rowPos, colPos);

                // display piece
                button.setImageResource(piece.getSpriteResId());

                // set click event
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // select residing piece
                        mSelectedPiece = mActiveGame.getPieceAtTile(rowPos, colPos);

                        // TODO: remove test logging
                        mActiveGame.addNewLogEntry("Piece selected on tile " + mSelectedPiece.getFilePos() + ":" + mSelectedPiece.getRankPos());
                        UpdateLogUI();
                    }
                });
            } else {
                // no piece found, disable the button
                button.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    // initialize buttons that comprise the game board
    private void AddGameBoardButtons() {
        // get grid layout
        GridLayout gameBoard = findViewById(R.id.game_board);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // create tile and assign to location in mGameTiles
                mGameTiles[row][col] = new ImageButton(GameActivity.this);
                // parameters for tiles
                GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(
                        //set start position & set weight to 1
                        GridLayout.spec(GridLayout.UNDEFINED, 1f),   // row
                        GridLayout.spec(GridLayout.UNDEFINED, 1f)    // col
                );

                // apply parameters
                mGameTiles[row][col].setLayoutParams(gridParams);

                // modify layout_height/width to keep buttons within boundaries
                mGameTiles[row][col].getLayoutParams().width = 0;
                mGameTiles[row][col].getLayoutParams().height = 0;

                // set the background color for the button
                mGameTiles[row][col].setBackgroundResource(row % 2 == col % 2 ? R.color.colorPrimary : R.color.colorPrimaryDark);

                // add button to gameBoard
                gameBoard.addView(mGameTiles[row][col]);

                // update the button to reflect current game data
                UpdateGameTile(row, col);
            }
        }
    }

    private void UpdateLogUI() {
        Game gameInstance = Game.get(GameActivity.this);

        mLogAdapter = new LogAdapter(gameInstance.getGameLog());
        mLogRecyclerView.setAdapter(mLogAdapter);
        mLogRecyclerView.scrollToPosition(gameInstance.getGameLog().size() - 1);
    }

    // ViewHolder for Game Log RecyclerView
    private static class LogHolder extends RecyclerView.ViewHolder {
        private TextView mLogEntryTextView;

        public LogHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.log_entry, parent, false));

            mLogEntryTextView = itemView.findViewById(R.id.log_entry_text);
        }

        public void bind(String text) {
            mLogEntryTextView.setText(text);
        }
    }

    // ViewAdapter for Game Log RecyclerView
    private class LogAdapter extends RecyclerView.Adapter<LogHolder> {
        private List<String> mLogEntries;
        public LogAdapter(List<String> log) {
            mLogEntries = log;
        }

        @NonNull
        @Override
        public LogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(GameActivity.this);

            return new LogHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull LogHolder holder, int position) {
            holder.bind(mLogEntries.get(position));
        }

        @Override
        public int getItemCount() {
            return mLogEntries.size();
        }
    }
}
