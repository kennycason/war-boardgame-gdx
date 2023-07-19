package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player

object TestPiecePlacer {
    fun place(board: Board) {
        val pieces = mutableListOf(
//            Infantry(Player.BLACK, 0, 0),
//            Infantry(Player.WHITE, 1, 1),

       //     Tank(Player.BLACK, 1, 3),

        //    Bomber(Player.BLACK, 3, 3),

//            Bomber(Player.WHITE, 0, 3)
                Bomber(Player.BLACK, 5, 5),
                Infantry(Player.WHITE, 6, 2)
        )

        for (piece in pieces) {
            board.state[piece.x][piece.y].piece = piece
        }
    }
}