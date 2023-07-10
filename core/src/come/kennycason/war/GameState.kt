package come.kennycason.war

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.board.Board

data class GameState(
    val board: Board = Board(
        Constants.BOARD_DIMENSIONS,
        Constants.BOARD_DIMENSIONS,
        Constants.TILE_DIM
    ),
    val batch: SpriteBatch = SpriteBatch(),
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
)