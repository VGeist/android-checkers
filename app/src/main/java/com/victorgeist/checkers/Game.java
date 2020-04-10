package com.victorgeist.checkers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private static Game sActiveGame;
    private static Piece[][] mGameBoard = new Piece[8][8];
    public static List<String> mGameLog = new ArrayList<String>();
    private static List<Piece> mTeam1 = new ArrayList<Piece>();
    private static List<Piece> mTeam2 = new ArrayList<Piece>();

    private int mPlayerTurn;

    public static Game getActiveGame() {
        return sActiveGame;
    }

    // return the active game, if no game exists create a new one
    public static Game get(Context context) {
        if (sActiveGame == null) {
            sActiveGame = new Game();
        }
        return sActiveGame;
    }

    private Game () {
        // create a new game
        newGame();
    }

    public void newGame() {
        // clear game data
        mGameLog.clear();
        mTeam1.clear();
        mTeam2.clear();

        // place pieces at initial positions (for checkers)
        for(int row = 7; row > 4; row--) {
            for(int col = 0 + ((row + 1) % 2); col < 8; col += 2){
                // add piece to board
                mGameBoard[row][col] = new Man(row, col, 1, R.drawable.ic_man_black);
                // add to team 1
                mTeam1.add(mGameBoard[row][col]);

                // mirror placement for team 2
                // values for mirrored positions
                int mirrorRow = 7 - row;
                int mirrorCol = 7 - col;

                // add piece to board
                mGameBoard[mirrorRow][mirrorCol] = new Man(mirrorRow, mirrorCol, 2, R.drawable.ic_man_red);
                // add to team 2
                mTeam1.add(mGameBoard[mirrorRow][mirrorCol]);
            }
        }

        mGameLog.add("Game Start");
    }

    public List<String> getGameLog() {
        return mGameLog;
    }

    public static Piece[][] getGameBoard() {
        return mGameBoard;
    }

    public static void setGameBoard(Piece[][] board) {
        mGameBoard = board;
    }

    // checks if a tile has a piece on it, takes the coordinates of the tile checked, returns boolean
    public boolean tileHasPiece(int row, int col) {
        if(mGameBoard[row][col] != null) {
            return true;
        } else {
            return false;
        }
    }

    // gets the piece at the specified coordinates, if no piece is found returns null ??
    public Piece getPieceAtTile(int row, int col) {
        Piece pieceAtCoords = mGameBoard[row][col];
        return pieceAtCoords;
    }

    // add an entry to the log
    public void addNewLogEntry(String msg) {
        mGameLog.add(msg);
    }
}