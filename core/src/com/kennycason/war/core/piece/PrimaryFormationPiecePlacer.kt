package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board

object PrimaryFormationPiecePlacer {
    fun place(board: Board) {
        val maxX = board.width - 1
        val maxY = board.height - 1

        val pieces = mutableListOf(
            Commander(Color.BLACK, 0, 0),
            Missile(Color.BLACK, 0, 1),
            Infantry(Color.BLACK, 0, 2),

            Bomber(Color.BLACK, 1, 0),
            Bomber(Color.BLACK, 1, 1),
            Infantry(Color.BLACK, 1, 2),

            Artillery(Color.BLACK, 2, 0),
            Artillery(Color.BLACK, 2, 1),
            Infantry(Color.BLACK, 2, 2),

            Tank(Color.BLACK, 3, 0),
            Tank(Color.BLACK, 3, 1),
            Infantry(Color.BLACK, 3, 2),

            Infantry(Color.BLACK, 4, 0),
            Infantry(Color.BLACK, 4, 1),
            Infantry(Color.BLACK, 4, 2),


            Commander(Color.WHITE, maxX - 0, maxY - 0),
            Missile(Color.WHITE, maxX - 0, maxY - 1),
            Infantry(Color.WHITE, maxX - 0, maxY - 2),

            Bomber(Color.WHITE, maxX - 1, maxY - 0),
            Bomber(Color.WHITE, maxX - 1, maxY - 1),
            Infantry(Color.WHITE, maxX - 1, maxY - 2),

            Artillery(Color.WHITE, maxX - 2, maxY - 0),
            Artillery(Color.WHITE, maxX - 2, maxY - 1),
            Infantry(Color.WHITE, maxX - 2, maxY - 2),

            Tank(Color.WHITE, maxX - 3, maxY - 0),
            Tank(Color.WHITE, maxX - 3, maxY - 1),
            Infantry(Color.WHITE, maxX - 3, maxY - 2),

            Infantry(Color.WHITE, maxX - 4, maxY - 0),
            Infantry(Color.WHITE, maxX - 4, maxY - 1),
            Infantry(Color.WHITE, maxX - 4, maxY - 2)
        )

        for (piece in pieces) {
            board.state[piece.x][piece.y].piece = piece
        }
    }
}