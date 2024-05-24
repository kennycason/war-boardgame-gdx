package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board

interface MoveEvaluator {
    fun evaluate(board: Board): Move?
}