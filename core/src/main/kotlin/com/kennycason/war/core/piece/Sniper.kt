package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveType
import com.kennycason.war.core.move.SniperDiagonalAttackGenerator

/**
 * Sniper - Long-range diagonal attack with limited movement.
 * 
 * Movement: 1 tile horizontally or vertically only (slow, careful positioning)
 * Attack: 1-3 tiles diagonally (long-range precision)
 * 
 * The Sniper cannot shoot through/over obstacles (pieces or terrain) UNLESS
 * it has an elevation advantage (sniper's elevation > all intermediate tiles).
 * This makes positioning on high ground crucial for snipers.
 */
class Sniper(
    override val player: Player,
    override var x: Int,
    override var y: Int
) : Piece() {

    override val type = PieceType.SNIPER

    override fun generatePossibleMoves(board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(movementGenerator.generatePossibleMoves(this, board))
        moves.addAll(attackGenerator.generatePossibleMoves(this, board))
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
                // Sniper attacks at range without moving
                board[move.toX, move.toY].piece = null
                addScore(board, move)
            }
        }
        changeTurn(board)
    }

}

// Movement: 1 tile horizontally or vertically, cannot attack while moving
private val movementGenerator = HorizontalVerticalMoveGenerator(
    maxDistance = 1,
    canAttack = false
)

// Attack: 1-3 tiles diagonally with line of sight checks
private val attackGenerator = SniperDiagonalAttackGenerator(
    maxDistance = 3,
    startI = 1
)

