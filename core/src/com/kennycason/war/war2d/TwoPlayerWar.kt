package com.kennycason.war.war2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Vector2
import com.kennycason.war.Constants
import com.kennycason.war.ai.MiniMaxCarlo
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.DefaultTerrainV2Generator
import com.kennycason.war.core.move.Cursor
import com.kennycason.war.core.move.HumanMoveMaker
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.core.move.MoveType
import com.kennycason.war.core.piece.RandomPiecePlacer
import com.kennycason.war.font.Fonts
import com.kennycason.war.sound.SoundManager
import com.kennycason.war.war2d.explosion.Explosion
import com.kennycason.war.war2d.graphics.GraphicsGdx


class TwoPlayerWar(
    private val position: Vector2 = Vector2(75f, 75f),
    private val tileDim: Int = 75
) {
    private val board: Board = Board(Constants.BOARD_DIMENSIONS, Constants.BOARD_DIMENSIONS)
    private val explosions = mutableListOf<Explosion>()
    private val cursor = Cursor(-1, -1, -1, -1)
    private val playerBlack: MoveMaker = HumanMoveMaker(Color.BLACK, cursor)
    private val playerWhite: MoveMaker = MiniMaxCarlo(maxDepth = 3, color = Color.WHITE)
//    private val playerWhite: MoveMaker = HumanMoveMaker(Color.WHITE, cursor)
    private val tileRenderer = TileRenderer(tileDim)

    // init after GDX initialized
    private var soundManager: SoundManager? = null

    fun newGame() {
        DefaultTerrainV2Generator.apply(board)
        RandomPiecePlacer.place(board)
//        TestPiecePlacer.place(board)

//        soundManager = SoundManager(musicVolume = 10)
//        soundManager!!.stopMusic()
//        soundManager!!.playMusic()
    }

    fun update() {
        clearHighlighted()
        updateCursor()
        generatePossibleMovesForSelectedPiece(board)
        handleExplosion()

        val move = when (board.currentTurn) {
            Color.BLACK -> playerBlack.make(board)
            Color.WHITE -> playerWhite.make(board)
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
        Fonts.VISITOR_24.color = Color.WHITE
        Fonts.VISITOR_24.draw(GraphicsGdx.batch(), "${if (board.currentTurn == Color.WHITE) "White's" else "Black's"} Turn", 20f, 25f)
        Fonts.VISITOR_24.draw(GraphicsGdx.batch(), "Black ${board.blackScore}  White ${board.whiteScore}", Constants.WIDTH - 200f, 25f)

        for (x in 0 until board.width) {
            Fonts.VISITOR_24.draw(GraphicsGdx.batch(), "$x", position.x + (x * tileDim) + tileDim / 2, 60f)
        }
        for (y in 0 until board.height) {
            Fonts.VISITOR_24.draw(GraphicsGdx.batch(), "$y", 50f, position.y + (y * tileDim) + tileDim / 2)
        }

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