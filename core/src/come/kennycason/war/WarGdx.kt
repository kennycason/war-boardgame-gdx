package come.kennycason.war

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.utils.ScreenUtils

// The graphical interface to War.
class WarGdx : ApplicationAdapter() {
    private var gameState: GameState? = null
//    private var img: Texture? = null


    override fun create() {
        gameState = GameState()
//        img = Texture("badlogic.jpg")
    }

    override fun render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f)
        gameState!!.batch.begin()
//        gameState.batch.draw(img, 100f, 0f)
        gameState!!.board.render(gameState!!, 50f, 50f)

        gameState!!.batch.end()
    }

    override fun dispose() {
        gameState!!.batch.dispose()
//        img?.dispose()
    }
}