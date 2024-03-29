package com.kennycason.war.war2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Vector2
import com.kennycason.war.Constants
import com.kennycason.war.ai.MiniMaxCarlo2
import com.kennycason.war.ai.MiniMaxCarlo2Async
import com.kennycason.war.ai.MiniMaxCarloAsync
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.DefaultTerrainV2Generator
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.board.ValleyTerrainGenerator
import com.kennycason.war.core.move.*
import com.kennycason.war.core.piece.PieceType
import com.kennycason.war.core.piece.PrimaryFormationPiecePlacer
import com.kennycason.war.core.piece.TestAirDefenseFormationPiecePlacer
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
    private val playerBlack: MoveMaker = HumanMoveMaker(Player.BLACK, cursor)
//    private val playerBlack: MoveMaker = MiniMaxCarlo2Async(maxDepth = 2, player = Player.BLACK)
//    private val playerBlack: MoveMaker = MiniMaxCarloAsync(maxDepth = 2, player = Player.BLACK)
//    private val playerBlack: MoveMaker = MiniMaxCarlo(maxDepth = 4, player = Player.WHITE)
//    private val playerWhite: MoveMaker = MiniMaxCarlo2Async(maxDepth = 3, player = Player.WHITE)
    private val playerWhite: MoveMaker = MiniMaxCarlo2(maxDepth = 4, player = Player.WHITE)
//    private val playerWhite: MoveMaker = MiniMaxCarloAsync(maxDepth = 4, player = Player.WHITE)
//    private val playerWhite: MoveMaker = HumanMoveMaker(Player.WHITE, cursor)
    private val tileRenderer = TileRenderer(tileDim)

    private val moveHistory = mutableListOf<Move>()

    // init after GDX initialized
    private var soundManager: SoundManager? = null

    private var isStarted = false

    fun newGame() {
        ValleyTerrainGenerator.apply(board)
//        DefaultTerrainV2Generator.apply(board)
//        TerrainNoiseGenerator.apply(board)
        PrimaryFormationPiecePlacer.place(board)
//        TestAirDefenseFormationPiecePlacer.place(board)
//        RandomPiecePlacer.place(board)
//        TestPiecePlacer.place(board)

//        soundManager = SoundManager(musicVolume = 10)
//        soundManager!!.stopMusic()
//        soundManager!!.playMusic()
    }

    fun update() {
//        if (!isStarted) {
//            if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
//                isStarted = true
//            }
//            return
//        }
        clearHighlighted()
        updateCursor()
        updateTileHighlightStateForSelectedPieceOrMouseHover(board)
        handleExplosion()

        val move = when (board.currentPlayer) {
            Player.BLACK -> playerBlack.make(board)
            Player.WHITE -> playerWhite.make(board)
        }

        if (move != null) {
            moveHistory.add(0, move)
            if (move.moveType == MoveType.ATTACK) {
                explosions.add(Explosion(move.toX.toFloat(), move.toY.toFloat()))
            }
        }
    }

    fun render() {
        Fonts.VISITOR_30.color = Color.WHITE
        Fonts.VISITOR_30.draw(GraphicsGdx.batch(), "${board.currentPlayer}'s Turn", 20f, 25f)
        Fonts.VISITOR_30.draw(GraphicsGdx.batch(), "Black ${board.blackScore.toInt()}", Constants.WIDTH - 270f, 25f)
        Fonts.VISITOR_30.draw(GraphicsGdx.batch(), "White ${board.whiteScore.toInt()}", Constants.WIDTH - 130f, 25f)

        for (x in 0 until board.width) {
            Fonts.VISITOR_30.draw(GraphicsGdx.batch(), "$x", position.x + (x * tileDim) + tileDim / 2, 60f)
        }
        for (y in 0 until board.height) {
            Fonts.VISITOR_30.draw(GraphicsGdx.batch(), "$y", 50f, position.y + (y * tileDim) + tileDim / 2)
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
        drawMoveHistory()
    }

    private fun drawMoveHistory() {
        Fonts.VISITOR_30.color = Color.WHITE

        Fonts.VISITOR_30.draw(GraphicsGdx.batch(), "History", Constants.WIDTH * Constants.SCALE - 10, Constants.WIDTH * Constants.SCALE - 20f)
        for (i in 0 until moveHistory.size) {
            val move = moveHistory[i]
            Fonts.VISITOR_30.draw(GraphicsGdx.batch(),
                "${getAttackText(move)}${getPlayerText(move)} ${getPieceTypeText(move)} (${move.fromX}, ${move.fromY}) ",
                Constants.WIDTH * Constants.SCALE - 23, Constants.WIDTH * Constants.SCALE - (20f * (i + 2)))
            Fonts.VISITOR_30.draw(GraphicsGdx.batch(),
                "to (${move.toX}, ${move.toY})",
                Constants.WIDTH * Constants.SCALE + 185, Constants.WIDTH * Constants.SCALE - (20f * (i + 2)))
            if (i > 40) break
        }
    }

    private fun getAttackText(move: Move) = if (move.moveType == MoveType.ATTACK) "*" else " "
    private fun getPlayerText(move: Move) = if (move.player == Player.BLACK) "BLK" else "WHT"

    private fun getPieceTypeText(move: Move) = when (move.pieceType) {
        PieceType.INFANTRY -> "INF"
        PieceType.TANK -> "TNK"
        PieceType.ARTILLERY -> "ART"
        PieceType.MISSILE -> "MIS"
        PieceType.AIR_DEFENSE -> "DEF"
        PieceType.BOMBER -> "BOM"
        PieceType.COMMANDER -> "CMD"
        PieceType.EXCAVATOR -> "EXC"
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

    private fun updateTileHighlightStateForSelectedPieceOrMouseHover(board: Board) {
        // always highlight tiles for selected piece
        var isTileSelected = false
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                if (board.state[x][y].highlight == TileHighlight.SELECTED) {
                    isTileSelected = true
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
        if (isTileSelected) return
        if (cursor.x == -1 && cursor.y == -1) return
        if (board.state[cursor.x][cursor.y].highlight == TileHighlight.SELECTED) return // already rendered above.
        val piece = board.state[cursor.x][cursor.y].piece ?: return
        if (piece.player != board.currentPlayer) return

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