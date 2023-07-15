package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.move.HorizontalVerticalMoveGenerator
import come.kennycason.war.move.Move

class Bomber(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {
    private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(
        maxDistance = 4,
        canAttack = true,
        ignoreHeight = true,
        canGoThroughOwnPiece = true
    )

    override val type = PieceType.BOMBER

    override fun generatePossibleMoves(board: Board): List<Move> {
        return horizontalVerticalMoveGenerator.generatePossibleMoves(this, board)
    }

}