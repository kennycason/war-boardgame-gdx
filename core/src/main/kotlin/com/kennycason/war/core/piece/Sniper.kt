package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.DiagonalMoveGenerator
import com.kennycason.war.core.move.Move

/**
 * Sniper - Diagonal combat mover, the diagonal counterpart to the Tank.
 *
 * Movement: 1-2 tiles diagonally (move + attack)
 * Score: 2.0 (same as Tank)
 */
class Sniper(
    override val player: Player,
    override var x: Int,
    override var y: Int
) : Piece() {

    override val type = PieceType.SNIPER

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}

private val moveGenerator = DiagonalMoveGenerator(maxDistance = 2)
