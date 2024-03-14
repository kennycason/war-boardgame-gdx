package com.kennycason.war

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.utils.ScreenUtils
import com.kennycason.war.war2d.TwoPlayerWar
import com.kennycason.war.war2d.graphics.GraphicsGdx

// The graphical interface to War.
class WarGdx : ApplicationAdapter() {
    private val twoPlayerWar = TwoPlayerWar()

    override fun create() {
        twoPlayerWar.newGame()
    }

    override fun render() {
        twoPlayerWar.update()

        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f)
        GraphicsGdx.batch().begin()
        twoPlayerWar.render()
        GraphicsGdx.batch().end()
    }

    override fun dispose() {
        GraphicsGdx.batch().dispose()
    }
}

