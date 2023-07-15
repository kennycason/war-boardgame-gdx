package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board

object TestPiecePlacer {
    fun place(board: Board) {
        val pieces = mutableListOf(
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.WHITE, 1, 1),

            Tank(Color.BLACK, 1, 3),

            Bomber(Color.BLACK, 3, 3),

            Bomber(Color.WHITE, 0, 3)
        )

        for (piece in pieces) {
            board.state[piece.x][piece.y].piece = piece
        }
    }
}