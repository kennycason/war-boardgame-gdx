package com.kennycason.war.core.board

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.Constants
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.core.piece.PieceType
import com.kennycason.war.util.array2d

data class Board(
    val width: Int = Constants.BOARD_DIMENSIONS,
    val height: Int = Constants.BOARD_DIMENSIONS,
    val state: Array<Array<Tile>> = array2d(width, height) { Tile() },
    var turnCount: Int = 0,
    var currentPlayer: Player = Player.BLACK,
    var blackScore: Double = 0.0,
    var whiteScore: Double = 0.0
) {
    fun isFinished() = isBlackWin() || isWhiteWin()
    fun isBlackWin() = blackScore >= PieceType.COMMANDER.score
    fun isWhiteWin() = whiteScore >= PieceType.COMMANDER.score
    fun add(piece: Piece): Piece {
        state[piece.x][piece.y].piece = piece
        return piece
    }
    override fun toString(): String {
        val sb = StringBuilder()
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
        }
    }
}

enum class Player(val color: Color) {
    BLACK(Color.BLACK),
    WHITE(Color.WHITE)
}