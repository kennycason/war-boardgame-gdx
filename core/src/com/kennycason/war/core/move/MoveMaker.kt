package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board

interface MoveMaker {
    fun makeMove(board: Board): Move?
}