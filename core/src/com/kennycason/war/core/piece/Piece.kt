package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.war2d.TileHighlight

interface Piece {
    val player: Player
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
        changeTurn(board)
    }

}

fun addScore(board: Board, move: Move) {
    when (board.currentPlayer) {
        Player.BLACK -> board.blackScore += move.score
        Player.WHITE -> board.whiteScore += move.score
    }
}

fun changeTurn(board: Board) {
    board.turnCount++
    board.currentPlayer = when (board.currentPlayer) {
        Player.BLACK -> Player.WHITE
        Player.WHITE -> Player.BLACK
    }
}