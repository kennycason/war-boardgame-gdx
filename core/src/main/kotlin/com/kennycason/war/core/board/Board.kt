package com.kennycason.war.core.board

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.Constants
import com.kennycason.war.core.piece.Commander
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.core.piece.PieceType
import com.kennycason.war.util.array2d
import com.kennycason.war.war2d.TileHighlight

data class Board(
    val width: Int = Constants.BOARD_DIMENSIONS,
    val height: Int = Constants.BOARD_DIMENSIONS,
    private val state: Array<Array<Tile>> = array2d(width, height) { Tile() },
    var turnCount: Int = 0,
    var currentPlayer: Player = Player.BLACK,
    var blackScore: Double = 0.0,
    var whiteScore: Double = 0.0
) {
    fun isFinished() = isBlackWin() || isWhiteWin()

    fun isBlackWin() = blackScore >= PieceType.COMMANDER.score

    fun isWhiteWin() = whiteScore >= PieceType.COMMANDER.score

    operator fun get(x: Int, y: Int): Tile = state[x][y]

    // Optionally, if you want to be able to set tiles using the [x, y] syntax
    operator fun set(x: Int, y: Int, tile: Tile) {
        state[x][y] = tile
    }

    fun add(piece: Piece): Piece {
        state[piece.x][piece.y].piece = piece
        return piece
    }

    fun getCommander(player: Player): Commander? {
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (state[x][y].piece?.type == PieceType.COMMANDER
                    && state[x][y].piece?.player == player) {
                    return state[x][y].piece as Commander
                }
            }
        }
        return null
    }

    fun paintElevation(elevation: Int) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                state[x][y].elevation = elevation
            }
        }
    }

    fun reset() {
        turnCount = 0
        whiteScore = 0.0
        blackScore = 0.0
        for (x in 0 until width) {
            for (y in 0 until height) {
                state[x][y].piece = null
                state[x][y].highlight = TileHighlight.NONE
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
//        sb.append(hashCode()).append("\n")
        for (y in 0 until height) {
            sb.append("|")
            for (x in 0 until width) {
                sb.append("${printTile(state[x][height - 1 - y])}|")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    private fun printTile(tile: Tile): String {
        val piece = tile.piece
        return if (piece == null) tile.elevation.toString()
            else when (piece.type) {
            PieceType.INFANTRY -> "I"
            PieceType.TANK -> "T"
            PieceType.ARTILLERY -> "A"
            PieceType.MISSILE -> "M"
            PieceType.AIR_DEFENSE -> "D"
            PieceType.BOMBER -> "B"
            PieceType.COMMANDER -> "C"
            // optional
            PieceType.EXCAVATOR -> "E"
        }
    }

}

enum class Player(val color: Color) {
    BLACK(Color.BLACK),
    WHITE(Color.WHITE)
}
