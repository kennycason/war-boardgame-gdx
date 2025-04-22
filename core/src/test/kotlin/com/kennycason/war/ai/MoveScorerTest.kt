package com.kennycason.war.ai

import com.kennycason.war.Constants
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.piece.Commander
import com.kennycason.war.core.piece.Infantry
import com.kennycason.war.core.piece.Tank
import org.junit.jupiter.api.Test

class MoveScorerTest {

    @Test
    fun `move towards commander`() {
        val board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
        board.currentPlayer = Player.BLACK

        val blackTank = board.add(Tank(Player.BLACK, 0, 0))
        val whiteCommander = board.add(Commander(Player.WHITE, board.width - 1, board.height - 1))
        val whiteInfantry = board.add(Infantry(Player.WHITE, 0, 2))

        val moves = blackTank.generatePossibleMoves(board)

        val moveScorer = MoveScorer(board, board.currentPlayer)
        moves.forEach {
            val score = moveScorer.calculate(it, 1)
            it.score = score.totalScore()
            println(it)
            println(score)
        }
    }

}
