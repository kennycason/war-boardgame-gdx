package come.kennycason.war.graphics

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

object DrawUtils {

    fun drawRect(
        x: Float, y: Float, width: Float, height: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        GraphicsGdx.batch().end()
        GraphicsGdx.shape().begin(shapeType)
        GraphicsGdx.shape().color = color
        GraphicsGdx.shape().rect(x, y, width, height)
        GraphicsGdx.shape().end()
        GraphicsGdx.batch().begin()
    }

    fun drawCircle(
        x: Float, y: Float, radius: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        GraphicsGdx.batch().end()
        GraphicsGdx.shape().begin(shapeType)
        GraphicsGdx.shape().color = color
        GraphicsGdx.shape().circle(x, y, radius)
        GraphicsGdx.shape().end()
        GraphicsGdx.batch().begin()
    }

    fun drawLine(
        x: Float, y: Float, x2: Float, y2: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        GraphicsGdx.batch().end()
        GraphicsGdx.shape().begin(shapeType)
        GraphicsGdx.shape().color = color
        GraphicsGdx.shape().line(x, y, x2, y2)
        GraphicsGdx.shape().end()
        GraphicsGdx.batch().begin()
    }

    fun drawTriangle(
        x: Float, y: Float, x2: Float, y2: Float, x3: Float, y3: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        GraphicsGdx.batch().end()
        GraphicsGdx.shape().begin(shapeType)
        GraphicsGdx.shape().color = color
        GraphicsGdx.shape().triangle(x, y, x2, y2, x3, y3)
        GraphicsGdx.shape().end()
        GraphicsGdx.batch().begin()
    }

}
