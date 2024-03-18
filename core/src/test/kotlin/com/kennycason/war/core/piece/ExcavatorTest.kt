package com.kennycason.war.core.piece

import com.kennycason.war.Constants
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ExcavatorTest {

    @Test
    fun `apply and undo all possible moves - excavator`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val piece = board.add(Excavator(Player.BLACK, 3, 3))

        val moves = piece.generatePossibleMoves(board)
        for (move in moves) {
            println("excavator $move")
            println(board)
            piece.applyMove(board, move)
            println(board)

            assertNull(board.state[move.fromX][move.fromY].piece)
            assertEquals(piece, board.state[move.toX][move.toY].piece)
            assertEquals(move.toX, piece.x)
            assertEquals(move.toY, piece.y)
            assertEquals(move.score, board.blackScore)

            piece.undoMove(board, move)
            println(board)

            assertNull(board.state[move.toX][move.toY].piece)
            assertEquals(piece, board.state[move.fromX][move.fromY].piece)
            assertEquals(move.fromX, piece.x)
            assertEquals(move.fromY, piece.y)
            assertEquals(0.0, board.blackScore)
        }
    }

}