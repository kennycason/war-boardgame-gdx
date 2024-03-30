package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.AirDefenseDetector
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveType

class Bomber(
    override val player: Player,
    override var x: Int,
    override var y: Int
) : Piece() {

    override val type = PieceType.BOMBER

    override fun generatePossibleMoves(board: Board): List<Move> {
        val moves = horizontalVerticalMoveGenerator.generatePossibleMoves(this, board)
        moves.forEach { AirDefenseDetector.updateScoreBaseOnAirDefense(board, this, it) }
        return moves
    }

    override fun applyMove(board: Board, move: Move) {
        // air defense affects flight, move or attack
        val airDefense = AirDefenseDetector.getNeighborAirDefense(board, this, move)
        if (airDefense != null) {
            board[airDefense.x, airDefense.y].piece = null
        }
        super.applyMove(board, move)

        if (airDefense != null) {
            board[x, y].piece = null
        }
    }

}

private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(
    maxDistance = 4,
    canAttack = true,
    ignoreHeight = true,
    canGoThroughOwnPiece = true
)