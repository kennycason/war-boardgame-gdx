package com.kennycason.war.core.move

import com.kennycason.war.core.board.Player
import com.kennycason.war.core.piece.AirDefense
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.core.piece.PieceType

data class Move(
    val player: Player,
    val pieceType: PieceType,
    val moveType: MoveType,
    val fromX: Int,
    val fromY: Int,
    val toX: Int,
    val toY: Int,
    var score: Double = 0.0,
    var destroyed: Piece? = null,
    var airDefense: AirDefense? = null // if this move results in an air defense being used.
)