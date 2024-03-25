package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.DiagonalMoveGenerator
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move

class Tank(
    override val player: Player,
    override var x: Int,
    override var y: Int
) : Piece() {

    override val type = PieceType.TANK

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}

private val moveGenerator = HorizontalVerticalMoveGenerator(maxDistance = 2)
//private val moveGenerator = DiagonalMoveGenerator(maxDistance = 2)
