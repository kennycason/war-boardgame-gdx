package com.kennycason.war.core.piece

import com.kennycason.war.Constants
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BomberTest {
    @Test
    fun `apply and undo all possible moves - bomber`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val piece = board.add(Bomber(Player.BLACK, 3, 3))

        val moves = piece.generatePossibleMoves(board)
        for (move in moves) {
            println("bomber $move")
            println(board)
            piece.applyMove(board, move)
            println(board)

            assertNull(board[move.fromX, move.fromY].piece)
            assertEquals(piece, board[move.toX, move.toY].piece)
            assertEquals(move.toX, piece.x)
            assertEquals(move.toY, piece.y)
            assertEquals(move.score, board.blackScore)

            piece.undoMove(board, move)
            println(board)

            assertNull(board[move.toX, move.toY].piece)
            assertEquals(piece, board[move.fromX, move.fromY].piece)
            assertEquals(move.fromX, piece.x)
            assertEquals(move.fromY, piece.y)
            assertEquals(0.0, board.blackScore)
        }
    }
}
