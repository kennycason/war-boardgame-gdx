package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.piece.AirDefense
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.core.piece.PieceType
import kotlin.math.abs

object AirDefenseDetector {

    fun getNeighborAirDefense(board: Board, piece: Piece, move: Move): Piece? {
        for (x in -1 until 2) {
            for (y in -1 until 2) {
                if (move.toX + x < 0 || move.toX + x >= board.width) continue
                if (move.toY + y < 0 || move.toY + y >= board.height) continue

                val neighborPiece = board.state[move.toX + x][move.toY + y].piece
                if (neighborPiece != null
                    && neighborPiece.type == PieceType.AIR_DEFENSE
                    && piece.player != neighborPiece.player) {
                    return neighborPiece
                }
            }
        }
        return null
    }

    // TODO stop calling if no enemy air defense
    fun updateScoreBaseOnAirDefense(board: Board, piece: Piece, move: Move) {
        for (x in -1 until 2) {
            for (y in -1 until 2) {
                if (move.toX + x < 0 || move.toX + x >= board.width) continue
                if (move.toY + y < 0 || move.toY + y >= board.height) continue

                val neighborPiece = board.state[move.toX + x][move.toY + y].piece
                if (neighborPiece != null
                    && neighborPiece.type == PieceType.AIR_DEFENSE
                    && piece.player != neighborPiece.player) {
                    move.airDefense = neighborPiece as AirDefense
                    move.destroyed?.let {
                        board.state[it.x][it.y].piece = it
                    }
//                    move.destroyed = piece
                    move.score -= piece.type.score // TODO does this need to worry about current player and sign(+/-)?
                    return
                }
            }
        }
    }

}