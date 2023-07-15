package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move

class Tank(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {

    override val type = PieceType.TANK

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}

private val moveGenerator = HorizontalVerticalMoveGenerator(maxDistance = 2)
