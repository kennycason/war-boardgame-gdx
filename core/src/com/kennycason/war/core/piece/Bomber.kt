package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move

class Bomber(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {

    override val type = PieceType.BOMBER

    override fun generatePossibleMoves(board: Board): List<Move> {
        return horizontalVerticalMoveGenerator.generatePossibleMoves(this, board)
    }

}

private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(
    maxDistance = 4,
    canAttack = true,
    ignoreHeight = true,
    canGoThroughOwnPiece = true
)