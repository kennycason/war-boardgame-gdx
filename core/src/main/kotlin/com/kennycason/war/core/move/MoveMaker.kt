package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.piece.Commander
import com.kennycason.war.core.piece.Piece

interface MoveMaker {
    fun make(board: Board): Move?
}