package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.board.Board
import come.kennycason.war.board.Move

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

    override fun draw(gameState: GameState, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2

        DrawUtils.drawTriangle(
            gameState,
            x + center - 18, y + center - 18,
            x + center + 18, y + center + - 18,
            x + center, y + center + 16,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        DrawUtils.drawRect(
            gameState,
            x + center - 3, y + center - 25,
            6f, 14f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

    }

    override fun generatePossibleMoves(board: Board): List<Move> {
        return horizontalVerticalMoveGenerator.generatePossibleMoves(this, board)
    }

}