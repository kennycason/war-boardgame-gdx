package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.piece.Piece

class InfantryMoveGenerator {
    private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(
        maxDistance = 1,
        canAttack = false
    )
    private val diagonalMoveGenerator = DiagonalMoveGenerator(
        maxDistance = 1,
        requiredAttack = true
    )

    fun generatePossibleMoves(piece: Piece, board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(piece, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(piece, board))
        return moves
    }

}