package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.util.Dice

object RandomPiecePlacer {
    fun place(board: Board) {
        val pieces = mutableListOf(
            Infantry(Player.BLACK, 0, 0),
            Infantry(Player.BLACK, 0, 0),
            Infantry(Player.BLACK, 0, 0),
            Infantry(Player.BLACK, 0, 0),
            Infantry(Player.BLACK, 0, 0),
            Infantry(Player.BLACK, 0, 0),
            Infantry(Player.BLACK, 0, 0),
            Infantry(Player.BLACK, 0, 0),

            Infantry(Player.WHITE, 0, 0),
            Infantry(Player.WHITE, 0, 0),
            Infantry(Player.WHITE, 0, 0),
            Infantry(Player.WHITE, 0, 0),
            Infantry(Player.WHITE, 0, 0),
            Infantry(Player.WHITE, 0, 0),
            Infantry(Player.WHITE, 0, 0),
            Infantry(Player.WHITE, 0, 0),

            Tank(Player.BLACK, 0, 0),
            Tank(Player.BLACK, 0, 0),

            Tank(Player.WHITE, 0, 0),
            Tank(Player.WHITE, 0, 0),

            Artillery(Player.BLACK, 0, 0),
            Artillery(Player.BLACK, 0, 0),

            Artillery(Player.WHITE, 0, 0),
            Artillery(Player.WHITE, 0, 0),

            Bomber(Player.BLACK, 0, 0),
            Bomber(Player.BLACK, 0, 0),

            Bomber(Player.WHITE, 0, 0),
            Bomber(Player.WHITE, 0, 0),

            Missile(Player.BLACK, 0, 0),
            Missile(Player.BLACK, 0, 0),

            Missile(Player.WHITE, 0, 0),
            Missile(Player.WHITE, 0, 0),

            Commander(Player.BLACK, 0, 0),

            Commander(Player.WHITE, 0, 0)
        )

        while (pieces.isNotEmpty()) {
            val x = Dice.d(board.width) - 1
            val y = Dice.d(board.height) - 1
            if (board[x, y].piece == null) {
                val piece = pieces.removeAt(0)
                piece.x = x
                piece.y = y
                board.add(piece)
            }
        }
    }
}