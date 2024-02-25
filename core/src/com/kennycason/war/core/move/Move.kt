package com.kennycason.war.core.move

import com.kennycason.war.core.board.Player
import com.kennycason.war.core.piece.PieceType

data class Move(
    val player: Player,
    val pieceType: PieceType,
    val moveType: MoveType,
    val fromX: Int,
    val fromY: Int,
    val toX: Int,
    val toY: Int,
    val score: Double = 0.0
)