package com.kennycason.war.core.piece

import com.kennycason.war.Constants
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.MoveType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArtilleryTest {

    @Test
    fun `apply and undo all possible moves - artillery`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val artillery = Artillery(Player.BLACK, 3, 3)
        board.add(artillery)

        // Place a target for attack moves
        board.add(Infantry(Player.WHITE, 3, 5))

        val moves = artillery.generatePossibleMoves(board)
        for (move in moves) {
            artillery.applyMove(board, move)

            if (move.moveType == MoveType.MOVE) {
                assertNull(board[move.fromX, move.fromY].piece)
                assertEquals(artillery, board[move.toX, move.toY].piece)
                assertEquals(move.toX, artillery.x)
                assertEquals(move.toY, artillery.y)
            }

            artillery.undoMove(board, move)

            if (move.moveType == MoveType.MOVE) {
                assertNull(board[move.toX, move.toY].piece)
            }
            assertEquals(artillery, board[move.fromX, move.fromY].piece)
            assertEquals(move.fromX, artillery.x)
            assertEquals(move.fromY, artillery.y)
        }
    }

    @Test
    fun `artillery enters reloading after attack`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        board.currentPlayer = Player.BLACK
        val artillery = Artillery(Player.BLACK, 3, 3)
        board.add(artillery)
        board.add(Infantry(Player.WHITE, 3, 5))

        assertFalse(artillery.isReloading)

        val moves = artillery.generatePossibleMoves(board)
        val attackMove = moves.first { it.moveType == MoveType.ATTACK }

        artillery.applyMove(board, attackMove)
        assertTrue(artillery.isReloading)
        assertEquals(0, artillery.lastAttackTurn)
    }

    @Test
    fun `artillery cannot attack while reloading`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        board.currentPlayer = Player.BLACK
        val artillery = Artillery(Player.BLACK, 3, 3)
        board.add(artillery)
        board.add(Infantry(Player.WHITE, 3, 5))
        board.add(Infantry(Player.WHITE, 3, 6))

        // Attack to trigger reload
        val moves = artillery.generatePossibleMoves(board)
        val attackMove = moves.first { it.moveType == MoveType.ATTACK }
        artillery.applyMove(board, attackMove)

        // Switch back to black's turn
        board.currentPlayer = Player.BLACK

        // Should have no attack moves while reloading
        val reloadMoves = artillery.generatePossibleMoves(board)
        val reloadAttackMoves = reloadMoves.filter { it.moveType == MoveType.ATTACK }
        assertEquals(0, reloadAttackMoves.size)
    }

    @Test
    fun `artillery undoMove restores previous reload state`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        board.currentPlayer = Player.BLACK
        board.turnCount = 10
        val artillery = Artillery(Player.BLACK, 3, 3)
        board.add(artillery)

        // Manually set artillery to already reloading from a previous attack
        artillery.isReloading = true
        artillery.lastAttackTurn = 8

        // Place a target
        board.add(Infantry(Player.WHITE, 3, 5))

        // Force an attack (bypass reload check for test by temporarily disabling)
        artillery.isReloading = false
        val moves = artillery.generatePossibleMoves(board)
        val attackMove = moves.first { it.moveType == MoveType.ATTACK }

        // Restore reload state before applying
        artillery.isReloading = true
        artillery.lastAttackTurn = 8

        // Apply the attack - should save previous state
        artillery.applyMove(board, attackMove)
        assertTrue(artillery.isReloading)
        assertEquals(10, artillery.lastAttackTurn)

        // Undo should restore the PREVIOUS reload state (reloading=true, lastAttackTurn=8)
        artillery.undoMove(board, attackMove)
        assertTrue(artillery.isReloading, "undoMove should restore previous isReloading=true")
        assertEquals(8, artillery.lastAttackTurn, "undoMove should restore previous lastAttackTurn=8")
    }

    @Test
    fun `artillery undoMove restores non-reloading state`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        board.currentPlayer = Player.BLACK
        val artillery = Artillery(Player.BLACK, 3, 3)
        board.add(artillery)
        board.add(Infantry(Player.WHITE, 3, 5))

        // Artillery starts NOT reloading
        assertFalse(artillery.isReloading)

        val moves = artillery.generatePossibleMoves(board)
        val attackMove = moves.first { it.moveType == MoveType.ATTACK }

        artillery.applyMove(board, attackMove)
        assertTrue(artillery.isReloading)

        artillery.undoMove(board, attackMove)
        assertFalse(artillery.isReloading, "undoMove should restore previous isReloading=false")
        assertEquals(0, artillery.lastAttackTurn, "undoMove should restore previous lastAttackTurn=0")
    }

    @Test
    fun `artillery attacks at range 2-3 without moving`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        board.currentPlayer = Player.BLACK
        val artillery = Artillery(Player.BLACK, 3, 3)
        board.add(artillery)

        // Targets at various distances
        board.add(Infantry(Player.WHITE, 3, 4)) // distance 1 - too close
        board.add(Infantry(Player.WHITE, 3, 5)) // distance 2 - in range
        board.add(Infantry(Player.WHITE, 3, 6)) // distance 3 - in range

        val moves = artillery.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should attack at distance 2 and 3, not distance 1
        assertEquals(2, attackMoves.size)
        for (move in attackMoves) {
            val distance = kotlin.math.abs(move.toY - move.fromY)
            assertTrue(distance in 2..3, "Artillery should attack at range 2-3, got $distance")
        }
    }

    @Test
    fun `artillery moves 1 tile any direction without attacking`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        board.currentPlayer = Player.BLACK
        val artillery = Artillery(Player.BLACK, 4, 4)
        board.add(artillery)

        // Place enemy adjacent - artillery should NOT be able to attack it
        board.add(Infantry(Player.WHITE, 4, 5))

        val moves = artillery.generatePossibleMoves(board)
        val moveMoves = moves.filter { it.moveType == MoveType.MOVE }

        // Should have 7 moves (8 directions minus the one blocked by infantry)
        assertEquals(7, moveMoves.size)

        for (move in moveMoves) {
            val dx = kotlin.math.abs(move.toX - move.fromX)
            val dy = kotlin.math.abs(move.toY - move.fromY)
            assertTrue(dx <= 1 && dy <= 1, "Artillery should move max 1 tile")
        }
    }
}
