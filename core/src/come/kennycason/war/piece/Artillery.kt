package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.board.Board
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

    private var isReloading = false
    private var lastAttackTurn = 0

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

        // show reload symbol
        DrawUtils.drawCircle(
            gameState,
            x + 4f, y + Constants.TILE_DIM - 8,
            4f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        DrawUtils.drawRect(
            gameState,
            x, y + Constants.TILE_DIM - 20,
            8f, 12f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        if (isReloading) {
            DrawUtils.drawLine(gameState,
                x, y + Constants.TILE_DIM,
                x + 8f, y + Constants.TILE_DIM - 20f,
                Color.RED
            )
            DrawUtils.drawLine(gameState,
                x + 8f, y + Constants.TILE_DIM,
                x, y + Constants.TILE_DIM - 20f,
                Color.RED
            )
        }
    }

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