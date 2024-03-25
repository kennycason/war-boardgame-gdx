package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.war2d.TileHighlight

abstract class Piece {
    abstract val player: Player
    abstract var x: Int
    abstract var y: Int
    abstract val type: PieceType

    abstract fun generatePossibleMoves(board: Board): List<Move>

    open fun applyMove(board: Board, move: Move) {
        board.state[move.fromX][move.fromY].piece = null
        board.state[move.fromX][move.fromY].highlight = TileHighlight.NONE

        // place piece
        if (move.airDefense == null) {
            board.state[move.toX][move.toY].piece = this
            x = move.toX
            y = move.toY
        }
        if (move.elevationDelta != 0) {
            board.state[move.toX][move.toY].elevation += move.elevationDelta
        }

        addScore(board, move)
        changeTurn(board)
    }

    open fun undoMove(board: Board, move: Move) {
        board.state[move.toX][move.toY].piece = move.destroyed
        board.state[move.toX][move.toY].highlight = TileHighlight.NONE

        board.state[move.fromX][move.fromY].piece = this

        x = move.fromX
        y = move.fromY

        move.airDefense
            ?.let {
                board.state[it.x][it.y].piece = it
            }

        if (move.elevationDelta != 0) {
            board.state[move.toX][move.toY].elevation -= move.elevationDelta
        }

        changeTurn(board)
        subtractScore(board, move)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Piece) return false

        return type == other.type && x == other.x && y == other.y
    }

}

fun addScore(board: Board, move: Move) {
    when (board.currentPlayer) {
        Player.BLACK -> board.blackScore += move.score
        Player.WHITE -> board.whiteScore += move.score
    }
}

fun subtractScore(board: Board, move: Move) {
    when (board.currentPlayer) {
        Player.BLACK -> board.blackScore -= move.score
        Player.WHITE -> board.whiteScore -= move.score
    }
}

fun changeTurn(board: Board) {
    board.turnCount++
    board.currentPlayer = when (board.currentPlayer) {
        Player.BLACK -> Player.WHITE
        Player.WHITE -> Player.BLACK
    }
}