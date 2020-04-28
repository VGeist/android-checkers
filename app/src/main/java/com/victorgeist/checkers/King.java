package com.victorgeist.checkers;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    //create a king manually
    public King(Team team, int startRank, int startFile) {
        super(team, startRank, startFile);
    }

    // create a king from a man
    public King(Man man) {
        super(man.getTeam(), man.getRankPos(), man.getFilePos());
    }

    @Override
    public boolean move(int newRank, int newFile) {
        // try to move
        if(super.move(newRank, newFile)) {
            // move successful, check if special conditions ar met
            if (jumpedAPiece()) {
                // find the jumped piece
                Piece jumped = findJumpedPiece();

                // add message to log
                Game.addNewLogEntry("The piece at " + jumped.getRankPos() + ", " + jumped.getFilePos() + " was taken!");

                // mark tile for update
                int[] jumpedTile = { jumped.getRankPos(), jumped.getFilePos() };
                Game.tilesToUpdate.add(jumpedTile);

                // remove piece from play
                Game.removePiece(findJumpedPiece());
            }

            // move successful, end turn
            Game.nextTurn();

            return true;
        } else {
            // move unsuccessful
            return false;
        }
    }

    @Override
    protected List<int[]> findValidMoves() {
        //TODO: add backward moving moves
        int forward = this.getTeam().getForward();

        List<int[]> validMoves = new ArrayList<>();

        // moves to test
        int[][] movesToTest = {
                {getRankPos() + forward, getFilePos() - 1}, // Forward left
                {getRankPos() + forward, getFilePos() + 1}, // forward right
                {getRankPos() - forward, getFilePos() - 1}, // backward left
                {getRankPos() - forward, getFilePos() + 1}  // backward right
        };

        // check moves
        for (int[] move : movesToTest) {
            if(Game.inBoundary(move[0], move[1])) {
                // within boundary
                Piece blockingPiece = Game.getPieceAtTile(move[0], move[1]); // gets piece at the tile, or returns null
                if (blockingPiece != null) {
                    // obstructing piece
                    if (blockingPiece.getTeam() != this.getTeam()) {
                        // enemy piece
                        // find jump location

                        int rowVelocity = move[0] - this.getRankPos();
                        int colVelocity = move[1] - this.getFilePos();

                        move[0] += rowVelocity;
                        move[1] += colVelocity;
                        if (!Game.tileHasPiece(move[0], move[1])) {
                            // tile empty, can jump
                            // add move
                            validMoves.add(move);
                        }
                    }
                } else {
                    // tile empty, can move
                    validMoves.add(move);
                }
            }
        }
        // return list of moves
        return validMoves;
    }

    @Override
    public int getSpriteId() {
        return getTeam().getKingSpriteId();
    }
}
