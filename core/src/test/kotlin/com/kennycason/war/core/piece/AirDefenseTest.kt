package com.kennycason.war.core.piece

import com.kennycason.war.Constants
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test


class AirDefenseTest {

    @Test
    fun `attack infantry protected by air defense`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        val blackBomber = board.add(Bomber(Player.BLACK, 3, 3))
        val whiteInfantry = board.add(Infantry(Player.WHITE, 5, 3))
        val whiteAirDefense = board.add(AirDefense(Player.WHITE, 6, 3))

        val moves = blackBomber.generatePossibleMoves(board)
        val attackInfantryMove = moves.first { it.toX == whiteInfantry.x && it.toY == whiteInfantry.y }

//        assertEquals(whiteInfantry, attackInfantryMove.destroyed) // TODO should not be destroyed
        assertEquals(whiteAirDefense, attackInfantryMove.airDefense)

        println("air defense $attackInfantryMove")
        println("before apply\n$board")
        blackBomber.applyMove(board, attackInfantryMove)
        println("after apply\n$board")

        assertNull(board.state[attackInfantryMove.fromX][attackInfantryMove.fromY].piece) // bomber no longer in place
        assertEquals(whiteInfantry, board.state[attackInfantryMove.toX][attackInfantryMove.toY].piece) // should still be white infantry because air defense
        assertNull(board.state[whiteAirDefense.x][whiteAirDefense.x].piece) // air defense used to attack bomber

        blackBomber.undoMove(board, attackInfantryMove)
        println("after undo\n$board")

        assertEquals(whiteInfantry, board.state[attackInfantryMove.toX][attackInfantryMove.toY].piece)
        assertEquals(whiteAirDefense, board.state[whiteAirDefense.x][whiteAirDefense.y].piece)
        assertEquals(blackBomber, board.state[attackInfantryMove.fromX][attackInfantryMove.fromY].piece)
    }

}