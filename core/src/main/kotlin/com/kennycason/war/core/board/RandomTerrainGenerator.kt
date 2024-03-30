package com.kennycason.war.core.board

import com.kennycason.war.util.Dice

class RandomTerrainGenerator {
    fun apply(board: Board) {
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                board[x, y].elevation = Dice.d(4) - 1
            }
        }
    }
}