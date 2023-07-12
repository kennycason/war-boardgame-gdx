package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.board.Board
import come.kennycason.war.move.Move

class Infantry(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {
    private val moveGenerator = InfantryMoveGenerator()

    override val type = PieceType.INFANTRY

    override fun draw(gameState: GameState, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2
        DrawUtils.drawCircle(
            gameState,
            x + center, y + center + 13,
            10f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        DrawUtils.drawRect(
            gameState,
            x + center - 7, y + center - 10,
            14f, 30f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
    }

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}