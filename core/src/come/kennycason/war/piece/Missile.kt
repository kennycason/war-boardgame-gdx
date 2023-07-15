package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.move.DiagonalMoveGenerator
import come.kennycason.war.move.HorizontalVerticalMoveGenerator
import come.kennycason.war.move.Move
import come.kennycason.war.move.MoveType

class Missile(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {
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