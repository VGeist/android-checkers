package com.victorgeist.checkers;

import java.util.UUID;

public class King extends Piece {

    public King(int startRank, int startFile, int teamNum, int spriteId) {
        super(startRank, startFile, teamNum, spriteId);
    }

    // used to turn a man piece into a king
    public King(UUID id, int rankPos, int teamNum, int filePos, int spriteId) {
        super(id, rankPos, filePos, teamNum, spriteId);
    }

    @Override
    protected boolean isMoveValid() {
        // TODO: implement isMoveValid
        return false;
    }
}
