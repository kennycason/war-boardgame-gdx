package come.kennycason.war.war2d

import array2d
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Vector2
import come.kennycason.war.Constants
import come.kennycason.war.board.Board
import come.kennycason.war.board.DefaultTerrainV2Generator
import come.kennycason.war.explosion.Explosion
import come.kennycason.war.graphics.GraphicsGdx
import come.kennycason.war.move.Cursor
import come.kennycason.war.move.HumanMoveMaker
import come.kennycason.war.move.MoveMaker
import come.kennycason.war.move.MoveType
import come.kennycason.war.piece.RandomPiecePlacer


class TwoPlayerWar(
    private val position: Vector2 = Vector2(50f, 50f),
    private val tileDim: Int = 75
) {
    private val board: Board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
    private val boardHighlight: Array<Array<TileHighlight>> = array2d(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS) { TileHighlight.NONE }
    private val explosions = mutableListOf<Explosion>()
    private val cursor = Cursor(-1, -1, -1, -1)

    private val playerBlack: MoveMaker = HumanMoveMaker(Color.BLACK, cursor)
    private val playerWhite: MoveMaker = HumanMoveMaker(Color.WHITE, cursor)

    private val tileRenderer = TileRenderer(tileDim)

    fun newGame() {
        DefaultTerrainV2Generator.apply(board)
        RandomPiecePlacer.place(board)
    }

    fun update() {
        clearHighlighted()
        updateCursor()
        generatePossibleMovesForSelectedPiece(board)
        handleExplosion()

        val move = when (board.currentTurn) {
            Color.BLACK -> playerBlack.makeMove(board)
            Color.WHITE -> playerWhite.makeMove(board)
            else -> throw IllegalStateException("invalid turn")
        }
        if (move != null) {
            board.turnCount++
            board.currentTurn = when (board.currentTurn) {
                Color.BLACK -> Color.WHITE
                Color.WHITE -> Color.BLACK
                else -> throw IllegalStateException("invalid turn")
            }
            if (move.moveType == MoveType.ATTACK) {
                explosions.add(Explosion(move.toX.toFloat(), move.toY.toFloat()))
            }
        }
    }

    fun render() {
        for (y in board.height - 1 downTo 0) {
            for (x in 0 until board.width) {
                val tile = board.state[x][y]
                tileRenderer.render(tile,
                    position.x + (x * tileDim),
                    position.y + (y * tileDim)
                )
            }
        }

        drawExplosions()
        drawCursor()
    }

    private fun clearHighlighted() {
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                if (board.state[x][y].highlight != TileHighlight.SELECTED) {
                    board.state[x][y].highlight = TileHighlight.NONE
                }
            }
        }
    }

    private fun updateCursor() {
        cursor.rawX = Gdx.input.x
        cursor.rawY = Constants.HEIGHT.toInt() - Gdx.input.y

        val tileX = clamp((cursor.rawX - position.x.toInt()) / tileDim, 0, board.width - 1)
        val tileY = clamp((cursor.rawY - position.y.toInt()) / tileDim, 0, board.height - 1)
        cursor.x = tileX
        cursor.y = tileY
    }

    fun addExplosion(explosion: Explosion) {
        explosions.add(explosion)
    }

    private fun handleExplosion() {
        val explosionIterator = explosions.iterator()
        while (explosionIterator.hasNext()) {
            val explosion = explosionIterator.next()
            if (!explosion.active) {
                explosionIterator.remove()
            }
        }
    }

    private fun drawExplosions() {
        for (explosion in explosions) {
            explosion.render(position)
        }
    }

    private fun drawCursor() {
        GraphicsGdx.drawCircle(cursor.rawX.toFloat(), cursor.rawY.toFloat(), 24f, Color.WHITE)
    }

    private fun generatePossibleMovesForSelectedPiece(board: Board) {
        // always highlight tiles for selected piece
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                if (board.state[x][y].highlight == TileHighlight.SELECTED) {
                    val possibleMoves = board.state[x][y].piece!!.generatePossibleMoves(board)
                    possibleMoves.forEach {
                        board.state[it.toX][it.toY].highlight = when (it.moveType) {
                            MoveType.MOVE -> TileHighlight.MOVE
                            MoveType.ATTACK -> TileHighlight.ATTACK
                        }
                    }
                }
            }
        }

        // also highlight possible moves for piece cursor is highlighting
        if (cursor.x == -1 && cursor.y == -1) return
        if (board.state[cursor.x][cursor.y].highlight == TileHighlight.SELECTED) return // already rendered above.
        val piece = board.state[cursor.x][cursor.y].piece ?: return
        if (piece.color != board.currentTurn) return

        // println("${cursor.x}, ${cursor.y} -> $tileX, $tileY")
        val possibleMoves = piece.generatePossibleMoves(board)
        possibleMoves.forEach {
            board.state[it.toX][it.toY].highlight = when (it.moveType) {
                MoveType.MOVE -> TileHighlight.MOVE
                MoveType.ATTACK -> TileHighlight.ATTACK
            }
        }
    }
}