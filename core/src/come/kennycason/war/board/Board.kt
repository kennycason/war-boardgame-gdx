package come.kennycason.war.board

import array2d
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import come.kennycason.war.Constants
import come.kennycason.war.Dice
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.explosion.Explosion
import come.kennycason.war.input.Cursor
import come.kennycason.war.move.HumanMoveMaker
import come.kennycason.war.move.MoveType
import come.kennycason.war.piece.*


class Board(
    var width: Int = 11,
    val height: Int = 11,
    val tileDim: Float = 48f
) {
    val explosions = mutableListOf<Explosion>()
    val state: Array<Array<Tile>> = array2d(width, height) { Tile() }
    var turnCount = 0

    val cursorRaw = Cursor(-1, -1)
    val cursor = Cursor(-1, -1)

    private val playerBlack = HumanMoveMaker(Color.BLACK, this)
    private val playerWhite = HumanMoveMaker(Color.WHITE, this)
    private var currentTurn = Color.BLACK

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
        generatePossibleMovesForSelectedPiece()

        val move = when (currentTurn) {
            Color.BLACK -> playerBlack.makeMove(this)
            Color.WHITE -> playerWhite.makeMove(this)
            else -> throw IllegalStateException("invalid turn")
        }
        if (move != null) {
            turnCount++
            currentTurn = when (currentTurn) {
                Color.BLACK -> Color.WHITE
                Color.WHITE -> Color.BLACK
                else -> throw IllegalStateException("invalid turn")
            }
        }


        for (y in height - 1 downTo 0) {
            for (x in 0 until width) {
                state[x][y].draw(gameState, x * tileDim + dx, y * tileDim + dy)
            }
        }

        handleExplosion(gameState)
        drawCursor(gameState)
    }

    private fun handleExplosion(gameState: GameState) {
        val explosionIterator = explosions.iterator()
        while (explosionIterator.hasNext()) {
            val explosion = explosionIterator.next()
            explosion.render(gameState)
            if (!explosion.active) {
                explosionIterator.remove()
            }
        }
    }

    private fun drawCursor(gameState: GameState) {
        DrawUtils.drawCircle(gameState, cursorRaw.x.toFloat(), cursorRaw.y.toFloat(), 24f, Color.WHITE)
    }

    private fun handleInput(gameState: GameState) {
        cursorRaw.x = Gdx.input.x
        cursorRaw.y = Constants.HEIGHT.toInt() - Gdx.input.y

        // TODO better handle the board offset x,y
        val tileX = clamp((cursorRaw.x - 50) / Constants.TILE_DIM.toInt(), 0, Constants.BOARD_DIMENSIONS - 1)
        val tileY = clamp((cursorRaw.y - 50) / Constants.TILE_DIM.toInt(), 0, Constants.BOARD_DIMENSIONS - 1)
        cursor.x = tileX
        cursor.y = tileY
    }

    private fun clearHighlighted(gameState: GameState) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (gameState.board.state[x][y].highlight != TileHighlight.SELECTED) {
                    gameState.board.state[x][y].highlight = TileHighlight.NONE
                }
            }
        }
    }

    private fun generatePossibleMovesForSelectedPiece() {
        // always highlight tiles for selected piece
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (state[x][y].highlight == TileHighlight.SELECTED) {
                    val possibleMoves = state[x][y].piece!!.generatePossibleMoves(this)
                    possibleMoves.forEach {
                        state[it.toX][it.toY].highlight = when (it.moveType) {
                            MoveType.MOVE -> TileHighlight.MOVE
                            MoveType.ATTACK -> TileHighlight.ATTACK
                        }
                    }
                }
            }
        }

        // also highlight possible moves for piece cursor is highlighting
        if (cursor.x == -1 && cursor.y == -1) return
        if (state[cursor.x][cursor.y].highlight == TileHighlight.SELECTED) return // already rendered above.
        val piece = state[cursor.x][cursor.y].piece ?: return
        if (piece.color != currentTurn) return

        // println("${cursor.x}, ${cursor.y} -> $tileX, $tileY")
        val possibleMoves = piece.generatePossibleMoves(this)
        possibleMoves.forEach {
            state[it.toX][it.toY].highlight = when (it.moveType) {
                MoveType.MOVE -> TileHighlight.MOVE
                MoveType.ATTACK -> TileHighlight.ATTACK
            }
        }
    }
}