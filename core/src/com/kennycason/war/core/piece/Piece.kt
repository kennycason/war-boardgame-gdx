package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.Move
import com.kennycason.war.war2d.TileHighlight

interface Piece {
    val color: Color // TODO use enum
    var x: Int
    var y: Int
    val type: PieceType

    fun generatePossibleMoves(board: Board): List<Move>

    fun applyMove(board: Board, move: Move) {
        board.state[move.fromX][move.fromY].piece = null
        board.state[move.fromX][move.fromY].highlight = TileHighlight.NONE

        // place piece
        board.state[move.toX][move.toY].piece = this
        x = move.toX
        y = move.toY

        addScore(board, move)
    }

}

fun addScore(board: Board, move: Move) {
    when (board.currentTurn) {
        Color.BLACK -> board.blackScore += move.score
        Color.WHITE -> board.whiteScore += move.score
        else -> throw IllegalStateException("invalid turn")
    }
}