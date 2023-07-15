package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.move.DiagonalMoveGenerator
import come.kennycason.war.move.HorizontalVerticalMoveGenerator
import come.kennycason.war.move.Move
import come.kennycason.war.move.MoveType

class Artillery(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {
    private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(
        maxDistance = 1,
        canAttack = false
    )
    private val diagonalMoveGenerator = DiagonalMoveGenerator(
        maxDistance = 1,
        canAttack = false
    )
    private val attackMoveGenerator = HorizontalVerticalMoveGenerator(
        startI = 2,
        maxDistance = 3,
        canAttack = true,
        requiredAttack = true,
        canGoThroughOwnPiece = true,
        canGoThroughEnemyPiece = true,
        ignoreHeight = true
    )

    var isReloading = false
    var lastAttackTurn = 0

    override val type = PieceType.ARTILLERY

    override fun generatePossibleMoves(board: Board): List<Move> {
        if (isReloading && board.turnCount - lastAttackTurn > 3) {
            isReloading = false
        }

        val moves = mutableListOf<Move>()
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(this, board))
        if (!isReloading) {
            moves.addAll(attackMoveGenerator.generatePossibleMoves(this, board))
        }
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
                board.state[move.toX][move.toY].piece = null
                isReloading = true
                lastAttackTurn = board.turnCount
            }
        }
    }

}