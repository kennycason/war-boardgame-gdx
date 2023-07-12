package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.board.Board
import come.kennycason.war.move.Move

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

    override fun draw(gameState: GameState, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2

        DrawUtils.drawLine(
            gameState,
            x + center - 26, y + center - 26,
            x + center + 26, y + center + 26,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        DrawUtils.drawLine(
            gameState,
            x + center - 26, y + center + 26,
            x + center + 26, y + center - 26,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        DrawUtils.drawCircle(
            gameState,
            x + center, y + center,
            23f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        DrawUtils.drawCircle(
            gameState,
            x + center, y + center,
            18f,
            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
            ShapeRenderer.ShapeType.Filled
        )

        DrawUtils.drawCircle(
            gameState,
            x + center, y + center,
            8f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

    }

    override fun generatePossibleMoves(board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(attackMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(this, board))
        return moves
    }

}