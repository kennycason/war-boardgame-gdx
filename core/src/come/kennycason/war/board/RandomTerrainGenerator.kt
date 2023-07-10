package come.kennycason.war.board

import come.kennycason.war.Dice

class RandomTerrainGenerator {
    fun apply(board: Board) {
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                board.state[x][y].elevation = Dice.d(4) - 1
            }
        }
    }
}