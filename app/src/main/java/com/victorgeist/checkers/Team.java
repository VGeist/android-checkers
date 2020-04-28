package com.victorgeist.checkers;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String mTeamName;
    public List<Piece> Pieces;
    private int mManSpriteId;
    private int mKingSpriteId;
    private int mForward;           // determines which direction is forward, should only be 1 or -1

    public Team(String teamName, int manSpriteId, int kingSpriteId, int forward) {
        mTeamName = teamName;
        Pieces = new ArrayList<Piece>();
        mManSpriteId = manSpriteId;
        mKingSpriteId = kingSpriteId;
        mForward = forward;
    }

    // getters and setters
    public int getManSpriteId() {
        return mManSpriteId;
    }

    public void setManSpriteId(int manSpriteId) {
        mManSpriteId = manSpriteId;
    }

    public int getKingSpriteId() {
        return mKingSpriteId;
    }

    public void setKingSpriteId(int kingSpriteId) {
        mKingSpriteId = kingSpriteId;
    }

    public int getForward() {
        return mForward;
    }

    public void setForward(int forward) {
        mForward = forward;
    }

    public String getTeamName() {
        return mTeamName;
    }
}
