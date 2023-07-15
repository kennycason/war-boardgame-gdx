package come.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import come.kennycason.war.Constants
import come.kennycason.war.graphics.GraphicsGdx
import come.kennycason.war.piece.Tank

class TankRenderer : PieceRenderer<Tank> {
    override fun render(piece: Tank, x: Float, y: Float) {
        val color = piece.color
        val center = Constants.TILE_DIM / 2

        // hull
        GraphicsGdx.drawRect(
            x + center - 15, y + center - 20,
            30f, 40f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // main gun
        GraphicsGdx.drawRect(
            x + center - 5, y + center + 10,
            10f, 20f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // turret
        GraphicsGdx.drawCircle(
            x + center, y + center,
            12f,
            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
            ShapeRenderer.ShapeType.Filled
        )
    }
}