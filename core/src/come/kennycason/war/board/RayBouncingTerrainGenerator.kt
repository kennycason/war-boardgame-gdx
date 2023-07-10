package come.kennycason.war.board

import array2d
import come.kennycason.war.Dice

class RayBouncingTerrainGenerator {

    fun apply(board: Board) {
        // init [[-1, ..], ..]
        val newState: Array<Array<Int>> = array2d(board.width, board.height) { -1 }

        var x = Dice.d(board.width) - 1
        var y = Dice.d(board.height) - 1
        var lastHeight = -1
        for (i in 0 until 100) {
            when (newState[x][y]) {
                -1 -> {
                    newState[x][y] = lastHeight
                    x += Dice.d(2) - 1
                    y += Dice.d(2) - 1
                }
                else -> {}
            }
        }

//        for (y in 0 until board.height) {
//            for (x in 0 until board.width) {
//                board.state[x][y].elevation = Dice.d(4) - 1
//            }
//        }

        // copy to board
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                board.state[x][y].elevation = newState[x][y]
            }
        }
    }
}