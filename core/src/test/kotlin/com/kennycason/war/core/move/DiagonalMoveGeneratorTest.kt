package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.piece.Sniper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DiagonalMoveGeneratorTest {

    @Test
    fun `diagonal moves respect board height boundary on non-square board`() {
        // Width=10, Height=6 - piece near top should NOT move beyond row 5
        val board = Board(width = 10, height = 6)
        val sniper = board.add(Sniper(Player.BLACK, 3, 4))

        val moves = sniper.generatePossibleMoves(board)

        // No move should go outside bounds
        for (move in moves) {
            assertTrue(move.toX in 0 until board.width, "toX=${move.toX} out of bounds [0, ${board.width})")
            assertTrue(move.toY in 0 until board.height, "toY=${move.toY} out of bounds [0, ${board.height})")
        }

        // Specifically, moveLeftUp (x-1, y+1) from (3,4) should only go to (2,5), NOT (1,6)
        val upLeftMoves = moves.filter { it.toX < sniper.x && it.toY > sniper.y }
        for (move in upLeftMoves) {
            assertTrue(move.toY < board.height, "moveLeftUp generated toY=${move.toY} >= board.height=${board.height}")
        }
    }

    @Test
    fun `diagonal moves respect board width boundary on non-square board`() {
        // Width=6, Height=10 - piece near right edge should NOT move beyond col 5
        val board = Board(width = 6, height = 10)
        val sniper = board.add(Sniper(Player.BLACK, 4, 3))

        val moves = sniper.generatePossibleMoves(board)

        for (move in moves) {
            assertTrue(move.toX in 0 until board.width, "toX=${move.toX} out of bounds [0, ${board.width})")
            assertTrue(move.toY in 0 until board.height, "toY=${move.toY} out of bounds [0, ${board.height})")
        }
    }

    @Test
    fun `diagonal moves on square board near corner`() {
        val board = Board(width = 8, height = 8)
        val sniper = board.add(Sniper(Player.BLACK, 0, 0))

        val moves = sniper.generatePossibleMoves(board)

        // From (0,0) only rightUp should work: (1,1) and (2,2)
        val moveMoves = moves.filter { it.moveType == MoveType.MOVE }
        assertEquals(2, moveMoves.size)
        assertTrue(moveMoves.any { it.toX == 1 && it.toY == 1 })
        assertTrue(moveMoves.any { it.toX == 2 && it.toY == 2 })
    }
}
