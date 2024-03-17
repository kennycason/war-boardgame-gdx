package com.kennycason.war.core.piece

import com.kennycason.war.Constants
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class InfantryTest {

    @Test
    fun `apply and undo move - infantry`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val piece = board.add(Infantry(Player.BLACK, 0, 0))

        val move = Move(
            player = Player.BLACK,
            pieceType = piece.type,
            moveType = MoveType.MOVE,
            fromX = piece.x,
            fromY = piece.y,
            toX = piece.x + 1,
            toY = piece.y,
            score = 1.0
        )
        piece.applyMove(board, move)

        assertNull(board.state[move.fromX][move.fromY].piece)
        assertEquals(piece, board.state[move.toX][move.toY].piece)
        assertEquals(move.toX, piece.x)
        assertEquals(move.toY, piece.y)
        assertEquals(move.score, board.blackScore)


        piece.undoMove(board, move)

        assertNull(board.state[move.toX][move.toY].piece)
        assertEquals(piece, board.state[move.fromX][move.fromY].piece)
        assertEquals(move.fromX, piece.x)
        assertEquals(move.fromY, piece.y)
        assertEquals(0.0, board.blackScore)
    }

    @Test
    fun `apply and undo all possible moves - infantry`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val piece = board.add(Infantry(Player.BLACK, 2, 2))

        val moves = piece.generatePossibleMoves(board)
        for (move in moves) {
            println("infantry: $move")
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