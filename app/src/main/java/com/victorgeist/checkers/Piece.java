package com.victorgeist.checkers;

import java.util.UUID;

// extends to the Man and King classes
public abstract class Piece {
    private UUID mID;
    private int mRankPos; // row
    private int mFilePos; // column

    private int mSpriteResId;
    private int mTeamNum;

    public Piece(int startRank, int startFile, int teamNum, int spriteId) {
        mID = UUID.randomUUID();
        mRankPos = startRank;
        mFilePos = startFile;
        mTeamNum = teamNum;
        mSpriteResId = spriteId;
    }

    public Piece(UUID id, int startRank, int startFile, int teamNum, int spriteId) {
        mID = id;
        mRankPos = startRank;
        mFilePos = startFile;
        mTeamNum = teamNum;
        mSpriteResId = spriteId;
    }

    // getters and setters
    public UUID getID() {
        return mID;
    }
    
    public int getRankPos() {
        return mRankPos;
    }

    public int getFilePos() {
        return mFilePos;
    }

    public int getTeamNum() {
        return mTeamNum;
    }

    public int getSpriteResId() {
        return mSpriteResId;
    }

    // setters
    // must be protected, only used to turn pieces into other types
    protected void setID(UUID ID) {
        mID = ID;
    }

    public void setRankPos(int rankPos) {
        mRankPos = rankPos;
    }

    public void setFilePos(int filePos) {
        mFilePos = filePos;
    }

    public void setTeamNum(int team) {
        mTeamNum = team;
    }

    public void setSpriteResId(int spriteResId) {
        mSpriteResId = spriteResId;
    }

    // methods
    public void movePiece(int newRank, int newFile) {
        // TODO: implement movePiece
        // place piece at coordinates
    }

    protected abstract boolean isMoveValid();
}
