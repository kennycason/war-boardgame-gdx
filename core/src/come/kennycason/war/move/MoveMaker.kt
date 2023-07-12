package come.kennycason.war.move

import come.kennycason.war.board.Board

interface MoveMaker {
    fun makeMove(board: Board): Move?
}