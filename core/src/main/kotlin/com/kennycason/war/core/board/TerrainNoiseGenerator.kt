package com.kennycason.war.core.board

import com.kennycason.war.util.Dice


object TerrainNoiseGenerator {
    private val range = 1
    private val probability = 50

    fun apply(board: Board) {
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                if (Dice.d(100) > probability) {
                    board[x, y].elevation += Dice.d(-1, 1)
                    if (board[x, y].elevation < 0) {
                        board[x, y].elevation = 0
                    }
                }
            }
        }
    }
}