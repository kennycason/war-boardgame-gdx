package come.kennycason.war.board

import come.kennycason.war.piece.Piece
import come.kennycason.war.war2d.TileHighlight

data class Tile(
    var elevation: Int = 0,
    var piece: Piece? = null,
    var highlight: TileHighlight = TileHighlight.NONE
)