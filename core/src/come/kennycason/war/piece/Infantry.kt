package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.move.InfantryMoveGenerator
import come.kennycason.war.move.Move

class Infantry(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {
    private val moveGenerator = InfantryMoveGenerator()

    override val type = PieceType.INFANTRY

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}