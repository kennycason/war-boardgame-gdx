package come.kennycason.war.board

import array2d
import com.badlogic.gdx.graphics.Color

data class Board(
    var width: Int = 11,
    val height: Int = 11,
    val state: Array<Array<Tile>> = array2d(width, height) { Tile() },
    var turnCount: Int = 0,
    var currentTurn: Color = Color.BLACK
)