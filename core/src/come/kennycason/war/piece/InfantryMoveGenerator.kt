package come.kennycason.war.piece

import come.kennycason.war.board.Board
import come.kennycason.war.board.Move

class InfantryMoveGenerator {
    private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(maxDistance = 1, canAttack = false)
    private val diagonalMoveGenerator = DiagonalMoveGenerator(maxDistance = 1, requiredAttack = true)

    fun generatePossibleMoves(piece: Piece, board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(piece, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(piece, board))
        return moves
    }

}