package com.kennycason.war.util

import com.kennycason.war.core.piece.*

// most pieces don't hold state and don't need to be copied
fun copyPiece(piece: Piece?): Piece? {
    return when (piece?.type) {
        PieceType.INFANTRY -> Infantry(piece.player, piece.x, piece.y)
        PieceType.TANK -> Tank(piece.player, piece.x, piece.y)
        PieceType.ARTILLERY -> Artillery(player = piece.player, x = piece.x, y = piece.y)
            .also {
                it.isReloading = (piece as Artillery).isReloading
                it.lastAttackTurn = piece.lastAttackTurn
            }
        PieceType.MISSILE -> Missile(piece.player, piece.x, piece.y)
        PieceType.AIR_DEFENSE -> AirDefense(piece.player, piece.x, piece.y)
        PieceType.BOMBER -> Bomber(piece.player, piece.x, piece.y)
        PieceType.COMMANDER -> Commander(piece.player, piece.x, piece.y)
        null -> null
    }
}