package come.kennycason.war.board

import array2d
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import come.kennycason.war.Constants
import come.kennycason.war.Dice
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.input.Cursor
import come.kennycason.war.piece.*


class Board(
    var width: Int = 11,
    val height: Int = 11,
    val tileDim: Float = 48f
) {
    val state: Array<Array<Tile>> = array2d(width, height) { Tile() }

    private val cursor = Cursor(-1, -1)

    init {
//        RandomTerrainGenerator().apply(this)
        DefaultTerrainV2Generator().apply(this)

        val pieces = mutableListOf(
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),
            Infantry(Color.BLACK, 0, 0),

            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),
            Infantry(Color.WHITE, 0, 0),

            Tank(Color.BLACK, 0, 0),
            Tank(Color.BLACK, 0, 0),

            Tank(Color.WHITE, 0, 0),
            Tank(Color.WHITE, 0, 0),

            Artillery(Color.BLACK, 0, 0),
            Artillery(Color.BLACK, 0, 0),

            Artillery(Color.WHITE, 0, 0),
            Artillery(Color.WHITE, 0, 0),

            Bomber(Color.BLACK, 0, 0),
            Bomber(Color.BLACK, 0, 0),

            Bomber(Color.WHITE, 0, 0),
            Bomber(Color.WHITE, 0, 0),

            Missile(Color.BLACK, 0, 0),
            Missile(Color.BLACK, 0, 0),

            Missile(Color.WHITE, 0, 0),
            Missile(Color.WHITE, 0, 0),

            Commander(Color.BLACK, 0, 0),

            Commander(Color.WHITE, 0, 0)
        )

        while (pieces.isNotEmpty()) {
            val x = Dice.d(width) - 1
            val y = Dice.d(width) - 1
            if (state[x][y].piece == null) {
                val piece = pieces.removeAt(0)
                piece.x = x
                piece.y = y
                state[x][y].piece = piece
            }
        }
    }

    fun render(gameState: GameState, dx: Float, dy: Float) {
        clearHighlighted(gameState)
        handleInput(gameState)
        generatePossibleMovesForSelectedPiece(gameState.board)

        for (y in height - 1 downTo 0) {
            for (x in 0 until width) {
                state[x][y].draw(gameState, x * tileDim + dx , y * tileDim + dy)
            }
        }

        drawCursor(gameState)
    }

    private fun drawCursor(gameState: GameState) {
        DrawUtils.drawCircle(gameState, cursor.x.toFloat(), cursor.y.toFloat(), 24f, Color.WHITE)
    }

    private fun handleInput(gameState: GameState) {
        cursor.x = Gdx.input.x
        cursor.y = Constants.HEIGHT.toInt() - Gdx.input.y
    }

    private fun clearHighlighted(gameState: GameState) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                gameState.board.state[x][y].highlight = TileHighlight.NONE
            }
        }
    }

    private fun generatePossibleMovesForSelectedPiece(board: Board) {
        if (cursor.x == -1 && cursor.y == -1) return

        // TODO better handle the board offset x,y
        val tileX = clamp((cursor.x - 50) / Constants.TILE_DIM.toInt(), 0, Constants.BOARD_DIMENSIONS - 1)
        val tileY = clamp((cursor.y - 50) / Constants.TILE_DIM.toInt(), 0, Constants.BOARD_DIMENSIONS - 1)

        // println("${cursor.x}, ${cursor.y} -> $tileX, $tileY")
        val possibleMoves = state[tileX][tileY].piece?.generatePossibleMoves(board)
        possibleMoves?.forEach {
            board.state[it.x][it.y].highlight = it.moveType
        }
    }
}