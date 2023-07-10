package come.kennycason.war

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

object DrawUtils {

    fun drawRect(
        gameState: GameState,
        x: Float, y: Float, width: Float, height: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        gameState.batch.end()
        gameState.shapeRenderer.begin(shapeType)
        gameState.shapeRenderer.color = color
        gameState.shapeRenderer.rect(x, y, width, height)
        gameState.shapeRenderer.end()
        gameState.batch.begin()
    }

    fun drawCircle(
        gameState: GameState,
        x: Float, y: Float, radius: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        gameState.batch.end()
        gameState.shapeRenderer.begin(shapeType)
        gameState.shapeRenderer.color = color
        gameState.shapeRenderer.circle(x, y, radius)
        gameState.shapeRenderer.end()
        gameState.batch.begin()
    }

    fun drawLine(
        gameState: GameState,
        x: Float, y: Float, x2: Float, y2: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        gameState.batch.end()
        gameState.shapeRenderer.begin(shapeType)
        gameState.shapeRenderer.color = color
        gameState.shapeRenderer.line(x, y, x2, y2)
        gameState.shapeRenderer.end()
        gameState.batch.begin()
    }

    fun drawTriangle(
        gameState: GameState,
        x: Float, y: Float, x2: Float, y2: Float, x3: Float, y3: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        gameState.batch.end()
        gameState.shapeRenderer.begin(shapeType)
        gameState.shapeRenderer.color = color
        gameState.shapeRenderer.triangle(x, y, x2, y2, x3, y3)
        gameState.shapeRenderer.end()
        gameState.batch.begin()
    }


}
