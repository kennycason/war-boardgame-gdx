package com.kennycason.war.core.board

import com.kennycason.war.core.piece.Piece
import com.kennycason.war.war2d.TileHighlight

data class Tile(
    var elevation: Int = 0,
    var piece: Piece? = null,
    var highlight: TileHighlight = TileHighlight.NONE
)