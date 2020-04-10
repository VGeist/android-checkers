package com.victorgeist.checkers;

public class Man extends Piece {
    public Man(int startRank, int startFile, int teamNum, int spriteId) {
        super(startRank, startFile, teamNum, spriteId);
    }

    @Override
    public void movePiece(int newRank, int newFile) {
        // TODO: implement movePiece
        // move piece to tile
        this.setRankPos(newRank);
        this.setFilePos(newFile);

        // if last row, become king
    }

    @Override
    public boolean isMoveValid() {
        // TODO: implement isMoveValid()
        // check that a move is valid
        return false;
    }

    public King becomeKing() {
        // return king for replacing man
        // change sprite image: red if team 1 , black if 2
        int newSpriteID = this.getTeamNum() == 1 ? R.drawable.ic_king_red : R.drawable.ic_king_black;
        return new King(this.getID(), this.getRankPos(), this.getFilePos(), this.getTeamNum(), newSpriteID);
    }
}
