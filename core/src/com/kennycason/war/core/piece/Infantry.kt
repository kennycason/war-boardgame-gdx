package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.InfantryMoveGenerator
import com.kennycason.war.core.move.Move

class Infantry(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {

    override val type = PieceType.INFANTRY

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}

private  val moveGenerator = InfantryMoveGenerator()