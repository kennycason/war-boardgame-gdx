package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.AirDefenseDetector
import com.kennycason.war.core.move.DiagonalMoveGenerator
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveType

class Missile(
    override val player: Player,
    override var x: Int,
    override var y: Int,
    private val attackThreshold: Int = 1
) : Piece() {

    override val type = PieceType.MISSILE

    override fun generatePossibleMoves(board: Board): List<Move> {
        val moves = mutableListOf<Move>()

        val attackMoves = attackMoveGenerator.generatePossibleMoves(this, board)
        attackMoves.forEach {
            AirDefenseDetector.updateScoreBaseOnAirDefense(board, this, it)
            if (it.destroyed != null) {
                it.score -= PieceType.MISSILE.score - attackThreshold
            }
        }

        moves.addAll(attackMoves)
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(this, board))
        return moves
    }

    override fun applyMove(board: Board, move: Move) {
        when (move.moveType) {
            MoveType.MOVE -> {
                board[move.fromX, move.fromY].piece = null
                board[move.toX, move.toY].piece = this
                x = move.toX
                y = move.toY
            }

            MoveType.ATTACK -> {
                board[move.fromX, move.fromY].piece = null
                board[move.toX, move.toY].piece = null
                addScore(board, move)
                // air defense affects missile during attack
                val airDefense = AirDefenseDetector.getNeighborAirDefense(board, this, move)
                if (airDefense != null) {
                    board[airDefense.x, airDefense.y].piece = null
                    board[airDefense.x, airDefense.y].piece = null
                    board[move.toX, move.toY].piece = move.destroyed
                }
            }
        }
        changeTurn(board)
    }

}

private val attackMoveGenerator = DiagonalMoveGenerator(
    maxDistance = 5,
    startI = 2,
    canGoThroughPieces = true,
    requiredAttack = true,
    canAttack = true,
    ignoreHeight = true
)
private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(
    maxDistance = 1,
    canAttack = false
)
private val diagonalMoveGenerator = DiagonalMoveGenerator(
    maxDistance = 1,
    canAttack = false
)