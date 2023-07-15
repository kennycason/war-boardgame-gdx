package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.util.Dice
import com.kennycason.war.core.board.Board

object RandomPiecePlacer {
    fun place(board: Board) {
        val pieces = mutableListOf(
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),

            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),

            Tank(Color.BLACK, 0, 0),
            Tank(Color.BLACK, 0, 0),

            Tank(Color.WHITE, 0, 0),
            Tank(Color.WHITE, 0, 0),

            Artillery(Color.BLACK, 0, 0),
            Artillery(Color.BLACK, 0, 0),

            Artillery(Color.WHITE, 0, 0),
            Artillery(Color.WHITE, 0, 0),

            Bomber(Color.BLACK, 0, 0),
            Bomber(Color.BLACK, 0, 0),

            Bomber(Color.WHITE, 0, 0),
            Bomber(Color.WHITE, 0, 0),

            Missile(Color.BLACK, 0, 0),
            Missile(Color.BLACK, 0, 0),

            Missile(Color.WHITE, 0, 0),
            Missile(Color.WHITE, 0, 0),

            Commander(Color.BLACK, 0, 0),

            Commander(Color.WHITE, 0, 0)
        )

        while (pieces.isNotEmpty()) {
            val x = Dice.d(board.width) - 1
            val y = Dice.d(board.height) - 1
            if (board.state[x][y].piece == null) {
                val piece = pieces.removeAt(0)
                piece.x = x
                piece.y = y
                board.state[x][y].piece = piece
            }
        }
    }
}