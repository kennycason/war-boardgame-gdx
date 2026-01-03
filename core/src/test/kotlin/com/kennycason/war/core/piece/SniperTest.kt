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
    fun `sniper can only move 1 tile horizontally or vertically`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 4, 4))

        val moves = sniper.generatePossibleMoves(board)
        val moveMoves = moves.filter { it.moveType == MoveType.MOVE }

        // Should only have 4 moves: up, down, left, right (1 tile each)
        assertEquals(4, moveMoves.size)

        // Verify all moves are exactly 1 tile away horizontally or vertically
        for (move in moveMoves) {
            val dx = kotlin.math.abs(move.toX - move.fromX)
            val dy = kotlin.math.abs(move.toY - move.fromY)
            assertTrue(
                (dx == 1 && dy == 0) || (dx == 0 && dy == 1),
                "Sniper should only move 1 tile H/V, got dx=$dx, dy=$dy"
            )
        }
    }

    @Test
    fun `sniper attacks diagonally 1-3 tiles`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 4, 4))

        // Place enemies on DIFFERENT diagonals so they don't block each other
        // Enemy at diagonal distance 1 (upper-right)
        board.add(Infantry(Player.WHITE, 5, 5))
        // Enemy at diagonal distance 2 (lower-left)
        board.add(Infantry(Player.WHITE, 2, 2))
        // Enemy at diagonal distance 3 (upper-left)
        board.add(Infantry(Player.WHITE, 1, 7))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should be able to attack all three enemies
        assertEquals(3, attackMoves.size)

        // Verify all attacks are diagonal
        for (move in attackMoves) {
            val dx = kotlin.math.abs(move.toX - move.fromX)
            val dy = kotlin.math.abs(move.toY - move.fromY)
            assertEquals(dx, dy, "Sniper attacks should be diagonal")
            assertTrue(dx in 1..3, "Sniper attack distance should be 1-3 tiles")
        }
    }

    @Test
    fun `sniper cannot attack beyond 3 tiles`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        // Place enemy at diagonal distance 4
        board.add(Infantry(Player.WHITE, 4, 4))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should not be able to attack the enemy 4 tiles away
        assertEquals(0, attackMoves.size)
    }

    @Test
    fun `sniper cannot shoot through pieces without elevation advantage`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        // Place blocking piece at distance 1
        board.add(Infantry(Player.WHITE, 1, 1))
        // Place target at distance 2 (behind the blocker)
        board.add(Infantry(Player.WHITE, 2, 2))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should only be able to attack the first enemy (blocker), not the one behind
        assertEquals(1, attackMoves.size)
        assertEquals(1, attackMoves[0].toX)
        assertEquals(1, attackMoves[0].toY)
    }

    @Test
    fun `sniper can shoot over pieces with elevation advantage`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        
        // Give sniper high ground
        board[0, 0].elevation = 3
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        // Place blocking piece at low elevation at distance 1
        board[1, 1].elevation = 1
        board.add(Infantry(Player.WHITE, 1, 1))
        
        // Place target at distance 2 (behind the blocker) at low elevation
        board[2, 2].elevation = 1
        board.add(Infantry(Player.WHITE, 2, 2))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should be able to attack both enemies - sniper is at elevation 3, obstacles at 1
        assertEquals(2, attackMoves.size)
    }

    @Test
    fun `sniper cannot shoot over higher terrain`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        
        // Sniper at elevation 1
        board[0, 0].elevation = 1
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        // Blocking terrain HIGHER than sniper (a hill in the way)
        board[1, 1].elevation = 2
        
        // Target at distance 2 behind the blocking terrain
        board.add(Infantry(Player.WHITE, 2, 2))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should not be able to attack - intermediate terrain is higher than sniper
        assertEquals(0, attackMoves.size)
    }

    @Test
    fun `sniper can shoot over same elevation terrain`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        
        // Sniper at elevation 2
        board[0, 0].elevation = 2
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        // Terrain at same elevation (no piece, just same level ground)
        board[1, 1].elevation = 2
        
        // Target at distance 2, also at elevation 2
        board[2, 2].elevation = 2
        board.add(Infantry(Player.WHITE, 2, 2))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }

        // Should be able to attack - sniper can see across same-level terrain
        assertEquals(1, attackMoves.size)
    }

    @Test
    fun `sniper does not move when attacking`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val sniper = board.add(Sniper(Player.BLACK, 3, 3))
        board.add(Infantry(Player.WHITE, 4, 4))

        val moves = sniper.generatePossibleMoves(board)
        val attackMoves = moves.filter { it.moveType == MoveType.ATTACK }
        
        assertEquals(1, attackMoves.size)
        val attackMove = attackMoves[0]

        // Apply the attack
        sniper.applyMove(board, attackMove)

        // Sniper should still be at original position
        assertEquals(3, sniper.x)
        assertEquals(3, sniper.y)
        assertEquals(sniper, board[3, 3].piece)
        
        // Target should be destroyed
        assertNull(board[4, 4].piece)
    }
}

