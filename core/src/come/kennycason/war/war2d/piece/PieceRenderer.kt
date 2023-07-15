package come.kennycason.war.war2d.piece

import come.kennycason.war.piece.Piece

interface PieceRenderer<T : Piece> {
    fun render(piece: T, x: Float, y: Float)
}