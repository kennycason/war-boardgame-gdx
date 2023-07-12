package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.board.Board
import come.kennycason.war.move.Move

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
        canGoThroughEnemyPiece = true
    )

    private var isReloading = false

    override val type = PieceType.ARTILLERY

    override fun draw(gameState: GameState, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2

        // axle
        DrawUtils.drawRect(
            gameState,
            x + center - 25, y + center - 3,
            50f, 6f,
            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
            ShapeRenderer.ShapeType.Filled
        )

        // left wheel
        DrawUtils.drawRect(
            gameState,
            x + center - 30, y + center - 15,
            10f, 30f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // right wheel
        DrawUtils.drawRect(
            gameState,
            x + center + 20, y + center - 15,
            10f, 30f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // gun
        DrawUtils.drawRect(
            gameState,
            x + center - 3, y + center - 16,
            6f, 45f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // gun base
        DrawUtils.drawCircle(
            gameState,
            x + center, y + center,
            10f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
    }

    override fun generatePossibleMoves(board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(attackMoveGenerator.generatePossibleMoves(this, board))
        return moves
    }

}