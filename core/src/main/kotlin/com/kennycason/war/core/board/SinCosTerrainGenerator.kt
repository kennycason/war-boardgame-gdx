package com.kennycason.war.core.board

import kotlin.math.cos
import kotlin.math.sin

object SinCosTerrainGenerator {
    fun apply(board: Board) {
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val z = sin(x.toDouble() - 5) + cos(y.toDouble() - 5)
                val normalizedZ = (z + 2).toInt()
//                println("$x $y -> $z $normalizedZ")
                board[x, y].elevation = normalizedZ
            }
        }
    }
}