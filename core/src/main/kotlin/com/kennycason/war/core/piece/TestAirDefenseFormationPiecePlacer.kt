package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player

object TestAirDefenseFormationPiecePlacer {
    fun place(board: Board) {
        val maxX = board.width - 1
        val maxY = board.height - 1

        val pieces = mutableListOf(
            Commander(Player.BLACK, 0, 0),
            Missile(Player.BLACK, 1, 1),
            Bomber(Player.BLACK, 0, 3),
            Infantry(Player.BLACK, 0, 2),

            AirDefense(Player.WHITE, 4, 4),
            Tank(Player.WHITE, 3, 3),
            Commander(Player.BLACK, maxX, maxY),
        )

        for (piece in pieces) {
            board.add(piece)
        }
    }
}