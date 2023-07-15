package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.DiagonalMoveGenerator
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveType

class Missile(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {

    override val type = PieceType.MISSILE

    override fun generatePossibleMoves(board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(attackMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(this, board))
        return moves
    }

    override fun applyMove(board: Board, move: Move) {
        when (move.moveType) {
            MoveType.MOVE -> {
                board.state[move.fromX][move.fromY].piece = null
                board.state[move.toX][move.toY].piece = this
                x = move.toX
                y = move.toY
            }
            MoveType.ATTACK -> {
                board.state[move.fromX][move.fromY].piece = null
                board.state[move.toX][move.toY].piece = null
            }
        }
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