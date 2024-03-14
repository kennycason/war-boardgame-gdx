package com.kennycason.war.war2d.piece

import com.kennycason.war.core.piece.Piece

interface PieceRenderer<T : Piece> {
    fun render(piece: T, x: Float, y: Float)
}