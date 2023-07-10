package come.kennycason.war

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class RectangleBatch(private val gameState: GameState) {

    private var isBatchDrawing = false

    fun begin(shapeType: ShapeRenderer.ShapeType) {
        isBatchDrawing = gameState.batch.isDrawing
        if (isBatchDrawing) { // temporarily pause
            gameState.batch.end()
        }

        gameState.shapeRenderer.begin(shapeType)
    }

    fun draw(x: Float, y: Float, width: Float, height: Float, color: Color = Color.GREEN) {
        gameState.shapeRenderer.color = color
        gameState.shapeRenderer.rect(x, y, width, height)
    }

    fun end() {
        gameState.shapeRenderer.end()
        if (isBatchDrawing) { // resume
            gameState.batch.begin()
        }
    }

}
