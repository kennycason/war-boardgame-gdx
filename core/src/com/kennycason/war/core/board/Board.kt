package com.kennycason.war.core.board

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.piece.PieceType
import com.kennycason.war.util.array2d

data class Board(
    val width: Int = 11,
    val height: Int = 11,
    val state: Array<Array<Tile>> = array2d(width, height) { Tile() },
    var turnCount: Int = 0,
    var currentPlayer: Player = Player.BLACK,
    var blackScore: Int = 0,
    var whiteScore: Int = 0
) {
    fun isFinished() = isBlackWin() || isWhiteWin()
    fun isBlackWin() = blackScore >= PieceType.COMMANDER.score
    fun isWhiteWin() = whiteScore >= PieceType.COMMANDER.score
}

enum class Player(val color: Color) {
    BLACK(Color.BLACK),
    WHITE(Color.WHITE)
}