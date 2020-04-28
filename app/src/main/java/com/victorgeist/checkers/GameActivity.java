package com.victorgeist.checkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class GameActivity extends AppCompatActivity {
    // game board is a 2d array of ImageButton objects
    private ImageButton[][] mGameTiles = new ImageButton[8][8];

    private Button mForfeitP1Button;
    private Button mForfeitP2Button;
    private Button mPauseButton;
    private RecyclerView mLogRecyclerView;
    private LogAdapter mLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mForfeitP1Button = findViewById(R.id.player1_forfeit_button);
        mForfeitP1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if Black's turn
                if(Game.getTurn().equals("Black")) {
                    // is turn of player 1
                    Game.forfeit();
                    endGame();
                }
            }
        });

        mForfeitP2Button = findViewById(R.id.player2_forfeit_button);
        mForfeitP2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checks the player turn is Red
                if(Game.getTurn().equals("Red")) {
                    // is turn of player 1
                    Game.forfeit();
                    endGame();
                }
            }
        });
    }

    // changes image resource and sets disabled if there is a piece at the specified coordinates
    // coordinates given should always reflect the coordinates of the button
    private void UpdateGameTile(int rowPos, int colPos) {
        // retrieve button
        ImageButton button = mGameTiles[rowPos][colPos];

        // get the residing piece
        Piece piece = Game.getPieceAtTile(rowPos, colPos);
        if(piece != null) {
            // piece exists on this tile, display
            button.setImageResource(piece.getSpriteId());
        } else {
            // no piece, set as empty
            button.setImageResource(0);
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

                // set click event
                final int r = row;
                final int c = col;
                mGameTiles[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tileClickEvent(r, c);
                    }
                });

                // update the button to reflect current game data
                UpdateGameTile(row, col);
            }
        }
    }

    // method for buttons
    private void tileClickEvent(int row, int col) {
        // if tile not empty, change selected piece or deselect if same
        Piece pieceOnTile = Game.getPieceAtTile(row, col);
        if (pieceOnTile == null && Game.selectedPiece != null) {
            // piece will move to location, if it can
            Game.selectedPiece.move(row, col);

            // update tiles
            for(int[] tile : Game.tilesToUpdate) {
                UpdateGameTile(tile[0], tile[1]);
            }

            // all tiles updated, clear update list
            Game.tilesToUpdate.clear();
        } else if (pieceOnTile != null) {
            // select the piece
            Game.selectPiece(pieceOnTile);
        } else {
            // a piece needs to be selected
            Game.addNewLogEntry("You must select a piece before you can move.");
        }
        UpdateLogUI();

        if(Game.getWinner() != null) {
            endGame();
        }
    }

    // disables all buttons to prevent modifying the game state
    private void endGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                mGameTiles[row][col].setEnabled(false);
            }
        }
        mForfeitP1Button.setEnabled(false);
        mForfeitP2Button.setEnabled(false);

        UpdateLogUI();
    }

    private void UpdateLogUI() {
        mLogAdapter = new LogAdapter(Game.getGameLog());
        mLogRecyclerView.setAdapter(mLogAdapter);
        mLogRecyclerView.scrollToPosition(Game.getGameLog().size() - 1);
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
