package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player

object PrimaryFormationPiecePlacer {
    fun place(board: Board) {
        val maxX = board.width - 1
        val maxY = board.height - 1

        val pieces = mutableListOf(
            Commander(Player.BLACK, 0, 0),
            Missile(Player.BLACK, 0, 1),
            Infantry(Player.BLACK, 0, 2),

            Bomber(Player.BLACK, 1, 0),
            AirDefense(Player.BLACK, 1, 1),
//            Bomber(Player.BLACK, 1, 1),
            Infantry(Player.BLACK, 1, 2),

            Artillery(Player.BLACK, 2, 0),
            Artillery(Player.BLACK, 2, 1),
            Infantry(Player.BLACK, 2, 2),

            Tank(Player.BLACK, 3, 0),
            Tank(Player.BLACK, 3, 1),
            Infantry(Player.BLACK, 3, 2),

            Bomber(Player.BLACK, 4, 0),
            Infantry(Player.BLACK, 4, 1),
            Infantry(Player.BLACK, 4, 2),

            Infantry(Player.BLACK, 5, 0),
            Infantry(Player.BLACK, 5, 1),
            Infantry(Player.BLACK, 5, 2),


            Commander(Player.WHITE, maxX - 0, maxY - 0),
            Missile(Player.WHITE, maxX - 0, maxY - 1),
            Infantry(Player.WHITE, maxX - 0, maxY - 2),

            Bomber(Player.WHITE, maxX - 1, maxY - 0),
            AirDefense(Player.WHITE, maxX - 1, maxY - 1),
//            Bomber(Player.WHITE, maxX - 1, maxY - 1),
            Infantry(Player.WHITE, maxX - 1, maxY - 2),

            Artillery(Player.WHITE, maxX - 2, maxY - 0),
            Artillery(Player.WHITE, maxX - 2, maxY - 1),
            Infantry(Player.WHITE, maxX - 2, maxY - 2),

            Tank(Player.WHITE, maxX - 3, maxY - 0),
            Tank(Player.WHITE, maxX - 3, maxY - 1),
            Infantry(Player.WHITE, maxX - 3, maxY - 2),

            Bomber(Player.WHITE, maxX - 4, maxY - 0),
            Infantry(Player.WHITE, maxX - 4, maxY - 1),
            Infantry(Player.WHITE, maxX - 4, maxY - 2),

            Infantry(Player.WHITE, maxX - 5, maxY - 0),
            Infantry(Player.WHITE, maxX - 5, maxY - 1),
            Infantry(Player.WHITE, maxX - 5, maxY - 2),
        )

        for (piece in pieces) {
            board[piece.x, piece.y].piece = piece
        }
    }
}