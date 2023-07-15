package come.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.graphics.GraphicsGdx
import come.kennycason.war.piece.Infantry

class InfantryRenderer : PieceRenderer<Infantry> {
    override fun render(piece: Infantry, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2
        GraphicsGdx.drawCircle(
            x + center, y + center + 13,
            10f,
            piece.color,
            ShapeRenderer.ShapeType.Filled
        )
        GraphicsGdx.drawRect(
            x + center - 7, y + center - 10,
            14f, 30f,
            piece.color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}