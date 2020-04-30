package com.victorgeist.checkers;

import java.util.ArrayList;
import java.util.List;

public class Game implements java.io.Serializable {
    private static Game sActiveGame;
    private int mBoardBoundary = 7; // NOTE: boundary value assumes the index of GameBoard starts at 0
    private Piece[][] mGameBoard;
    private List<String> mGameLog = new ArrayList<>();
    private Team blackTeam = new Team("Black", R.drawable.ic_man_black, R.drawable.ic_king_black, -1);
    private Team redTeam = new Team("Red", R.drawable.ic_man_red, R.drawable.ic_king_red, 1);
    private String mTurn;        // uses teamName of Team to determine turns
    private String mWinner;
    public transient Piece selectedPiece;
    public List<int[]> tilesToUpdate = new ArrayList<>();

    // return the active game, if no game exists create a new one
    public static Game get() {
        if (sActiveGame == null) {
            sActiveGame = new Game();
        }
        return sActiveGame;
    }

    public Game() {
        newGame();
    }

    // getters and setters
    public List<String> getGameLog() {
        return mGameLog;
    }

    public Piece[][] getGameBoard() {
        return mGameBoard;
    }

    public void setGameBoard(Piece[][] board) {
        mGameBoard = board;
    }

    public int getBoardBoundary() {
        return mBoardBoundary;
    }

    public String getTurn() {
        return mTurn;
    }

    public void setTurn(String turn) {
        mTurn = turn;
    }

    public String getWinner() {
        return mWinner;
    }

    public void setWinner(String winner) {
        mWinner = winner;
        addNewLogEntry(winner + " has won the game! Congratulations " + winner + "!");
    }

    public void newGame() {
        // reset game data
        sActiveGame = this; // set the active game to the current object
        mGameLog.clear();
        redTeam.Pieces.clear();
        blackTeam.Pieces.clear();
        mGameBoard = new Piece[mBoardBoundary + 1][mBoardBoundary + 1];
        mTurn = blackTeam.getTeamName();
        mWinner = null;

        // place pieces at initial positions (for checkers)
        for(int row = mBoardBoundary; row > 4; row--) {
            for(int col = (row + 1) % 2; col <= mBoardBoundary; col += 2){
                Man blackMan = new Man(blackTeam, row, col);
                placePiece(blackMan);

                // mirror placement for team 2
                // values for mirrored positions
                int mirrorRow = mBoardBoundary - row;
                int mirrorCol = mBoardBoundary - col;

                Man redMan = new Man(redTeam, mirrorRow, mirrorCol);
                placePiece(redMan);
            }
        }

        refreshBoard();

        addNewLogEntry("Game Start. Good Luck, Have Fun!");
    }

    // updates sActiveGame with a new game
    public void LoadGame(Game game) {
        // set activeGame
        sActiveGame = game;

        // deselect piece
        selectedPiece = null;

        // mark all tiles for update
        refreshBoard();
    }

    // checks if a tile has a piece on it, returns boolean
    public boolean tileHasPiece(int row, int col) {
        return inBoundary(row, col) && mGameBoard[row][col] != null;
    }

    // gets the piece at the specified coordinates, if no piece is found returns null
    public Piece getPieceAtTile(int row, int col) {
        if(tileHasPiece(row, col)) {
            return mGameBoard[row][col];
        } else {
            return null;
        }
    }

    // check the row and col values are within the game boundaries
    public boolean inBoundary(int row, int col) {
        return row <= mBoardBoundary && row >= 0 && col <= mBoardBoundary && col >= 0;
    }

    // add an entry to the log
    public void addNewLogEntry(String msg) {
        mGameLog.add(msg);
    }

    // remove the piece from the gameboard and piece list
    public void removePiece(Piece piece) {
        mGameBoard[piece.getRankPos()][piece.getFilePos()] = null;
        piece.getTeam().Pieces.remove(piece);
    }

    // place a piece, update gameboard and team's pieces list
    public void placePiece(Piece piece) {
        mGameBoard[piece.getRankPos()][piece.getFilePos()] = piece;
        piece.getTeam().Pieces.add(piece);
    }

    // it is the next player's turn
    public void nextTurn() {
        // clear selected piece
        selectedPiece = null;

        // check if game is over
        if (!isGameOver()) {
            // get the team who goes next and update turn variable
            mTurn = mTurn.equals(blackTeam.getTeamName()) ? redTeam.getTeamName() : blackTeam.getTeamName();
        }
    }

    // select given piece, if the piece given is already selected, deselect the piece
    public void selectPiece(Piece piece) {
        if(piece == selectedPiece) {
            // same piece, deselect
            selectedPiece = null;
        } else if (piece.getTeam().getTeamName().equals(getTurn())) {
            // if on the same team, select
            selectedPiece = piece;
        } else {
            // piece should not be selected (is not on the correct team)
            addNewLogEntry("You may only select pieces on your team.");
        }
    }

    public void forfeit() {
        addNewLogEntry(mTurn + " has forfeited.");
        setWinner(mTurn.equals(blackTeam.getTeamName()) ? redTeam.getTeamName() : blackTeam.getTeamName());
    }

    // returns true/false if game is over, if a winner was already declared returns true, if a team is out of pieces it declares a winner and return true
    public boolean isGameOver() {
        if(mWinner != null) {
            // winner already declared
            return true;
        } else if (blackTeam.Pieces.isEmpty()) {
            // winner not found, check if black has lost
            setWinner(redTeam.getTeamName());
            return true;
        } else if (redTeam.Pieces.isEmpty()) {
            // winner not found, check if red has lost
            setWinner(blackTeam.getTeamName());
            return true;
        } else {
            // NOTE: Technically a player who cannot move loses. However, for simplicity, it is up to the players to forfeit in such case.
            return false;
        }
    }

    // marks all tiles for update
    private void refreshBoard() {
        for (int row = 0; row <= mBoardBoundary; row++) {
            for (int col = 0; col <= mBoardBoundary; col++) {
                int[] tile = { row, col };
                tilesToUpdate.add(tile);
            }
        }
    }

    // TODO: remove testGame
    public Game NewTestGame() {
        // reset game data
        sActiveGame = this; // set the active game to the current object
        mGameLog.clear();
        redTeam.Pieces.clear();
        blackTeam.Pieces.clear();
        mGameBoard = new Piece[mBoardBoundary + 1][mBoardBoundary + 1];
        mTurn = blackTeam.getTeamName();
        mWinner = null;

        // place pieces at initial positions (for checkers)
        Man blackMan = new Man(blackTeam, 7, 0);
        placePiece(blackMan);

        Man redMan = new Man(redTeam, 0, 7);
        placePiece(redMan);

        refreshBoard();

        addNewLogEntry("Game Start. Good Luck, Have Fun!");

        return this;
    }
}