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
import come.kennycason.war.piece.Bomber
import come.kennycason.war.piece.Infantry
import come.kennycason.war.piece.Missile
import come.kennycason.war.piece.Tank


class Board(
    val width: Int = 11,
    val height: Int = 11,
    val tileDim: Float = 48f
) {
    val state: Array<Array<Tile>> = array2d(width, height) { Tile() }

    private val cursor = Cursor(-1, -1)

    init {
//        RandomTerrainGenerator().apply(this)
        DefaultTerrainV2Generator().apply(this)

        for (i in 1 until 10) {
            val wx = Dice.d(width) - 1
            val wy = Dice.d(width) - 1
            val bx = Dice.d(width) - 1
            val by = Dice.d(width) - 1
            state[wx][wy].piece = Missile(Color.BLACK, wx, wy)
            state[bx][by].piece = Missile(Color.WHITE, bx, by)
        }
        for (i in 1 until 10) {
            val wx = Dice.d(width) - 1
            val wy = Dice.d(width) - 1
            val bx = Dice.d(width) - 1
            val by = Dice.d(width) - 1
            state[wx][wy].piece = Bomber(Color.BLACK, wx, wy)
            state[bx][by].piece = Bomber(Color.WHITE, bx, by)
        }
        for (i in 1 until 10) {
            val wx = Dice.d(width) - 1
            val wy = Dice.d(width) - 1
            val bx = Dice.d(width) - 1
            val by = Dice.d(width) - 1
            state[wx][wy].piece = Tank(Color.BLACK, wx, wy)
            state[bx][by].piece = Tank(Color.WHITE, bx, by)
        }
        for (i in 1 until 10) {
            val wx = Dice.d(width) - 1
            val wy = Dice.d(width) - 1
            val bx = Dice.d(width) - 1
            val by = Dice.d(width) - 1
            state[wx][wy].piece = Infantry(Color.BLACK, wx, wy)
            state[bx][by].piece = Infantry(Color.WHITE, bx, by)
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