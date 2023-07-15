package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.move.HorizontalVerticalMoveGenerator
import come.kennycason.war.move.Move

class Tank(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {
    private val moveGenerator = HorizontalVerticalMoveGenerator(maxDistance = 2)

    override val type = PieceType.TANK

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}