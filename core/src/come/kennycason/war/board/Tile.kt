package come.kennycason.war.board

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.DrawUtils
import come.kennycason.war.GameState
import come.kennycason.war.piece.Piece

enum class TileHighlight {
    NONE,
    MOVE,
    ATTACK,
    SELECTED
}

class Tile(
    var elevation: Int = 0,
    var piece: Piece? = null,
    var highlight: TileHighlight = TileHighlight.NONE
) {
    fun draw(gameState: GameState, x: Float, y: Float) {
        // draw top tile
        val tileElevationHeight = elevation * 10f
        val tileY = y + tileElevationHeight
        DrawUtils.drawRect(
            gameState,
            x, tileY,
            Constants.TILE_DIM, Constants.TILE_DIM,
            getColor(elevation),
            ShapeRenderer.ShapeType.Filled
        )

        // draw piece if needed
        piece?.draw(gameState, x, tileY)

        // draw wall if needed.
        if (elevation > 0) {
            DrawUtils.drawRect(
                gameState,
                x, y,
                Constants.TILE_DIM, tileElevationHeight,
                WALL,
                ShapeRenderer.ShapeType.Filled
            )
        }
    }

    private fun getColor(elevation: Int): Color {
        return when (highlight) {
            TileHighlight.MOVE -> HIGHLIGHTED_MOVE
            TileHighlight.ATTACK -> HIGHLIGHTED_ATTACK
            TileHighlight.SELECTED -> Color.BROWN
            TileHighlight.NONE -> when (elevation) {
                0 -> GROUND0
                1 -> GROUND1
                2 -> GROUND2
                3 -> GROUND3
                else -> throw IllegalStateException("No color for elevation: $elevation")
            }
        }
    }

    companion object {
        val GROUND3 = Color(0.9f, 0.85f, 0.75f, 1f)
        val GROUND2 = Color(0.9f, 0.8f, 0.7f, 1f)
        val GROUND1 = Color(0.9f, 0.75f, 0.65f, 1f)
        val GROUND0 = Color(0.8f, 0.7f, 0.7f, 1f)
//        val GROUND0 = Color(0.9f, 0.7f, 0.6f, 1f)

        val WALL = Color(0.7f, 0.6f, 0.5f, 1f)

        val HIGHLIGHTED_MOVE = Color(0.4f, 0.7f, 0.4f, 1f)
        val HIGHLIGHTED_ATTACK = Color(0.7f, 0.4f, 0.4f, 1f)
    }
}