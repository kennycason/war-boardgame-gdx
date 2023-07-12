package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.board.Board
import come.kennycason.war.move.Move

class Tank(
    override val color: Color,
    override var x: Int,
    override var y: Int
) : Piece {
    private val moveGenerator = HorizontalVerticalMoveGenerator(maxDistance = 2)

    override val type = PieceType.TANK

    override fun draw(gameState: GameState, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2

        // hull
        DrawUtils.drawRect(
            gameState,
            x + center - 15, y + center - 20,
            30f, 40f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // main gun
        DrawUtils.drawRect(
            gameState,
            x + center - 5, y + center + 10,
            10f, 20f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // turret
        DrawUtils.drawCircle(
            gameState,
            x + center, y + center,
            12f,
            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
            ShapeRenderer.ShapeType.Filled
        )

    }

    override fun generatePossibleMoves(board: Board): List<Move> {
        return moveGenerator.generatePossibleMoves(this, board)
    }

}