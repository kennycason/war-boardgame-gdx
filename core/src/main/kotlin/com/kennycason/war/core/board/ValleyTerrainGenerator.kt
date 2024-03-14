package com.kennycason.war.core.board

object ValleyTerrainGenerator {
    private val terrain = arrayOf(
        arrayOf(3,3,3,3,1,0,1,2,2,3,3),
        arrayOf(3,3,3,2,0,0,2,2,2,3,3),
        arrayOf(1,2,3,1,0,1,2,1,2,3,3),
        arrayOf(1,2,3,0,0,0,0,1,2,2,3),
        arrayOf(2,3,2,0,0,1,1,1,1,3,3),
        arrayOf(1,2,2,0,0,0,1,0,2,3,3),
        arrayOf(3,2,2,1,1,0,2,1,2,3,3),
        arrayOf(3,1,1,1,1,1,1,2,2,3,3),
        arrayOf(2,0,0,0,0,1,1,2,3,3,3),
        arrayOf(0,0,0,0,0,0,2,3,3,3,3),
        arrayOf(0,0,0,1,1,2,2,2,3,3,3)
    )
    fun apply(board: Board) {
        for (y in 0 until terrain.size) {
            for (x in 0 until terrain[y].size) {
                board.state[x][terrain[y].size - 1 - y].elevation = terrain[y][x]
            }
        }
    }
}
