package com.kennycason.war.core.board

object DefaultTerrainV2Generator {
    private val terrain = arrayOf(
        arrayOf(2,2,1,0,3,3,0,0,0,0,0),
        arrayOf(2,1,1,0,0,0,0,0,0,0,0),
        arrayOf(1,1,1,1,0,1,2,1,0,0,0),
        arrayOf(0,1,1,1,1,2,2,1,0,0,0),
        arrayOf(0,0,1,2,3,3,3,2,0,0,1),
        arrayOf(0,0,1,3,3,3,3,3,0,0,2),
        arrayOf(0,0,0,2,3,3,3,3,0,0,0),
        arrayOf(0,0,0,1,3,3,2,1,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,1,2),
        arrayOf(0,0,0,0,0,0,0,0,1,1,1),
        arrayOf(0,0,0,0,0,3,0,0,1,2,2)
    )
    fun apply(board: Board) {
        for (y in 0 until terrain.size) {
            for (x in 0 until terrain[y].size) {
                board.state[x][y].elevation = terrain[x][y]
            }
        }
    }
}
