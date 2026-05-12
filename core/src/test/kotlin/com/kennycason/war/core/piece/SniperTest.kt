package com.kennycason.war.core.piece

import com.kennycason.war.Constants
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.MoveType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SniperTest {

    @Test
    fun `apply and undo all possible moves - sniper`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val piece = board.add(Sniper(Player.BLACK, 3, 3))

        val moves = piece.generatePossibleMoves(board)
        for (move in moves) {
            println("sniper $move")
            println(board)
            piece.applyMove(board, move)
            println(board)

            if (move.moveType == MoveType.MOVE) {
                assertNull(board[move.fromX, move.fromY].piece)
                assertEquals(piece, board[move.toX, move.toY].piece)
                assertEquals(move.toX, piece.x)
                assertEquals(move.toY, piece.y)
            }
            assertEquals(move.score, board.blackScore)

            piece.undoMove(board, move)
            println(board)

            if (move.moveType == MoveType.MOVE) {
                assertNull(board[move.toX, move.toY].piece)
            }
            assertEquals(piece, board[move.fromX, move.fromY].piece)
            assertEquals(move.fromX, piece.x)
            assertEquals(move.fromY, piece.y)
            assertEquals(0.0, board.blackScore)
        }
    }

    @Test
    fun `sniper moves diagonally 1-2 tiles`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 4, 4))

        val moves = sniper.generatePossibleMoves(board)
        val moveMoves = moves.filter { it.moveType == MoveType.MOVE }

        // Should have 8 moves: 4 diagonal at distance 1 + 4 diagonal at distance 2
        assertEquals(8, moveMoves.size)

        // Verify all moves are diagonal
        for (move in moveMoves) {
            val dx = kotlin.math.abs(move.toX - move.fromX)
            val dy = kotlin.math.abs(move.toY - move.fromY)
            assertEquals(dx, dy, "Sniper should only move diagonally, got dx=$dx, dy=$dy")
            assertTrue(dx in 1..2, "Sniper should move 1-2 tiles diagonally, got $dx")
        }
    }

    @Test
    fun `sniper attacks diagonally 1-2 tiles`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 4, 4))

        // Place enemies on different diagonals
        board.add(Infantry(Player.WHITE, 5, 5))  // distance 1
        board.add(Infantry(Player.WHITE, 2, 2))  // distance 2

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        assertEquals(2, attackMoves.size)

        for (move in attackMoves) {
            val dx = kotlin.math.abs(move.toX - move.fromX)
            val dy = kotlin.math.abs(move.toY - move.fromY)
            assertEquals(dx, dy, "Sniper attacks should be diagonal")
            assertTrue(dx in 1..2, "Sniper attack distance should be 1-2 tiles")
        }
    }

    @Test
    fun `sniper cannot move beyond 2 tiles`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        // Place enemy at diagonal distance 3
        board.add(Infantry(Player.WHITE, 3, 3))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should not be able to attack the enemy 3 tiles away
        assertEquals(0, attackMoves.size)
    }

    @Test
    fun `sniper cannot move through pieces`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        // Place blocking friendly piece at distance 1
        board.add(Infantry(Player.BLACK, 1, 1))

        val moves = sniper.generatePossibleMoves(board)

        // Should not be able to move to (1,1) or (2,2) on that diagonal
        val blockedMoves = moves.filter { it.toX == 2 && it.toY == 2 }
        assertEquals(0, blockedMoves.size)
    }
}
