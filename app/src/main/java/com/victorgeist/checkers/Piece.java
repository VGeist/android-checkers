package com.victorgeist.checkers;

import java.util.UUID;
import java.util.List;

// extends to the Man and King classes
public abstract class Piece {
    private UUID mID;
    private Team mTeam;
    private int mRankPos; // row
    private int mFilePos; // column
    private int mPrevRankPos;
    private int mPrevFilePos;

    public Piece(Team team, int startRank, int startFile) {
        mID = UUID.randomUUID();
        mTeam = team;
        mRankPos = startRank;
        mFilePos = startFile;
    }

    // getters and setters
    // getters
    public UUID getID() {
        return mID;
    }

    public Team getTeam() {
        return mTeam;
    }
    
    public int getRankPos() {
        return mRankPos;
    }

    public int getFilePos() {
        return mFilePos;
    }

    public int getPrevRankPos() {
        return mPrevRankPos;
    }

    public int getPrevFilePos() {
        return mPrevFilePos;
    }

    // setters
    public void setRankPos(int rankPos) {
        mRankPos = rankPos;
    }

    public void setFilePos(int filePos) {
        mFilePos = filePos;
    }

    public void setPrevRankPos(int prevRankPos) {
        mPrevRankPos = prevRankPos;
    }

    public void setPrevFilePos(int prevFilePos) {
        mPrevFilePos = prevFilePos;
    }

    // methods

    // tries to move piece to location, this method ONLY moves the piece, returns true if successful, subclass overrides if they can do something other than move (e.g. jump)
    public boolean move(int newRank, int newFile) {
        if (isMoveValid(newRank, newFile)) {
            // save position data to previous rank and file
            setPrevRankPos(getRankPos());
            setPrevFilePos(getFilePos());

            // pickup the piece
            Game.get().removePiece(this);

            // update piece coordinates
            setRankPos(newRank);
            setFilePos(newFile);

            // place piece at new location
            Game.get().placePiece(this);

            Game.get().addNewLogEntry(getTeam().getTeamName() + " moved " + getClass().getSimpleName() + " from " +
                    getPrevRankPos() + ", " + getPrevFilePos() + " to " + getRankPos() + ", " + getFilePos());

            // mark previous tile pos, and new tile pos for update
            int[] newTile = { newRank, newFile };
            int[] prevTile = { getPrevRankPos(), getPrevFilePos() };

            Game.get().tilesToUpdate.add(newTile);
            Game.get().tilesToUpdate.add(prevTile);

            //NOTE: moves that end turn are defined in the subclass, for simplicity all moves end turn

            return true;
        } else {
            // piece unable to move to that location
            Game.get().addNewLogEntry("Can't move to that location.");
            return false;
        }
    }

    private boolean isMoveValid(int row, int col) {
        // find valid moves
        List<int[]> validMoves = findValidMoves();

        for(int[] move : validMoves) {
            if(move[0] == row && move[1] == col) {
                return true;
            }
        }
        return false;
    }

    // finds jumped piece
    public Piece findJumpedPiece() {
        // find row/col direction of jump
        int rowJumpDirection = (this.getRankPos() - this.getPrevRankPos()) / 2;
        int colJumpDirection = (this.getFilePos() - this.getPrevFilePos()) / 2;

        // return piece 1 space in direction of jump
        return Game.get().getPieceAtTile(this.getPrevRankPos() + rowJumpDirection, this.getPrevFilePos() + colJumpDirection);
    }

    // checks if last move was a jump
    public boolean jumpedAPiece() {
        return Math.abs(this.getRankPos() - this.getPrevRankPos()) > 1;
    }

    protected abstract List<int[]> findValidMoves();

    public abstract int getSpriteId();
}
