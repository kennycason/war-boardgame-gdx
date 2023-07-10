package come.kennycason.war.board

data class Move(
    val x: Int,
    val y: Int,
    val moveType: TileHighlight = TileHighlight.MOVE,
    val score: Int = 0
)