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
        board[move.fromX, move.fromY].piece = null
        board[move.fromX, move.fromY].highlight = TileHighlight.NONE

        // place piece
        if (move.airDefense == null) {
            board[move.toX, move.toY].piece = this
            x = move.toX
            y = move.toY
        }
        if (move.elevationDelta != 0) {
            board[move.toX, move.toY].elevation += move.elevationDelta
        }

        addScore(board, move)
        changeTurn(board)
    }

    open fun undoMove(board: Board, move: Move) {
        board[move.toX, move.toY].piece = move.destroyed
        board[move.toX, move.toY].highlight = TileHighlight.NONE

        board[move.fromX, move.fromY].piece = this

        x = move.fromX
        y = move.fromY

        val moveAirDefense = move.airDefense
        if (moveAirDefense != null) {
            board[moveAirDefense.x, moveAirDefense.y].piece = moveAirDefense
        }

        if (move.elevationDelta != 0) {
            board[move.toX, move.toY].elevation -= move.elevationDelta
        }

        changeTurn(board)
        subtractScore(board, move)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Piece) return false

        return type == other.type && x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + x
        result = 31 * result + y
        result = 31 * result + type.hashCode()
        return result
    }

    
    companion object {
        fun addScore(board: Board, move: Move) {
            when (board.currentPlayer) {
                Player.BLACK -> board.blackScore += move.score.toInt()
                Player.WHITE -> board.whiteScore += move.score.toInt()
            }
        }

        fun subtractScore(board: Board, move: Move) {
            when (board.currentPlayer) {
                Player.BLACK -> board.blackScore -= move.score.toInt()
                Player.WHITE -> board.whiteScore -= move.score.toInt()
            }
        }

        fun changeTurn(board: Board) {
            board.turnCount++
            board.currentPlayer = when (board.currentPlayer) {
                Player.BLACK -> Player.WHITE
                Player.WHITE -> Player.BLACK
            }
        }
    }

}
