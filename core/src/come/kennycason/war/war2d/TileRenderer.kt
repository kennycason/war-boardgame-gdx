package come.kennycason.war.war2d

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.board.Tile
import come.kennycason.war.graphics.GraphicsGdx
import come.kennycason.war.piece.*
import come.kennycason.war.war2d.piece.*

class TileRenderer(
    private val tileDim: Int
) {

    private val infantryRenderer = InfantryRenderer()
    private val tankRenderer = TankRenderer()
    private val artilleryRenderer = ArtilleryRenderer()
    private val bomberRenderer = BomberRenderer()
    private val missileRenderer = MissileRenderer()
    private val commanderRenderer = CommanderRenderer()

    fun render(tile: Tile, x: Float, y: Float) {
        // draw top tile
        val tileElevationHeight = tile.elevation * 10f
        val tileY = y + tileElevationHeight
        GraphicsGdx.drawRect(
            x, tileY,
            tileDim.toFloat(), tileDim.toFloat(),
            getColor(tile),
            ShapeRenderer.ShapeType.Filled
        )

        // draw piece if needed
        val piece = tile.piece
        if (piece != null) {
            when (piece.type) {
                PieceType.INFANTRY -> infantryRenderer.render(piece as Infantry, x, tileY)
                PieceType.TANK -> tankRenderer.render(piece as Tank, x, tileY)
                PieceType.ARTILLERY -> artilleryRenderer.render(piece as Artillery, x, tileY)
                PieceType.BOMBER -> bomberRenderer.render(piece as Bomber, x, tileY)
                PieceType.MISSILE -> missileRenderer.render(piece as Missile, x, tileY)
                PieceType.COMMANDER -> commanderRenderer.render(piece as Commander, x, tileY)
            }
        }

        // draw wall if needed.
        if (tile.elevation > 0) {
            GraphicsGdx.drawRect(
                x, y,
                tileDim.toFloat(), tileElevationHeight,
                WALL,
                ShapeRenderer.ShapeType.Filled
            )
        }
    }

    private fun getColor(tile: Tile): Color {
        return when (tile.highlight) {
            TileHighlight.MOVE -> HIGHLIGHTED_MOVE
            TileHighlight.ATTACK -> HIGHLIGHTED_ATTACK
            TileHighlight.SELECTED -> Color.BROWN
            TileHighlight.NONE -> when (tile.elevation) {
                0 -> GROUND0
                1 -> GROUND1
                2 -> GROUND2
                3 -> GROUND3
                else -> throw IllegalStateException("No color for elevation: ${tile.elevation}")
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