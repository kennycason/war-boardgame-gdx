package com.kennycason.war.core.board

class DefaultTerrainGenerator {
    private val terrain = arrayOf(
        arrayOf(0,0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,1,2,1,0,0,0),
        arrayOf(0,0,0,0,0,2,2,1,0,0,0),
        arrayOf(0,0,1,2,3,3,3,2,0,0,0),
        arrayOf(0,0,1,3,3,3,3,3,0,0,0),
        arrayOf(0,0,0,2,3,3,3,3,0,0,0),
        arrayOf(0,0,0,1,3,3,2,1,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0,0)
    )
    fun apply(board: Board) {
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                board.state[x][y].elevation = terrain[x][y]
            }
        }
    }
}