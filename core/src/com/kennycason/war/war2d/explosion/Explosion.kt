package com.kennycason.war.war2d.explosion

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.kennycason.war.Constants
import com.kennycason.war.util.Dice
import com.kennycason.war.war2d.graphics.GraphicsGdx

class Explosion(
    val x: Float,
    val y: Float,
    var active: Boolean = true
) {

    private val startTime = System.currentTimeMillis()

    fun render(position: Vector2) {
        if (System.currentTimeMillis() - startTime > 500) {
            active = false
            return
        }

        GraphicsGdx.drawCircle(
            position.x + (x * Constants.TILE_DIM) + Dice.float() * Constants.TILE_DIM,
            position.y + (y * Constants.TILE_DIM) + Dice.float() * Constants.TILE_DIM,
            Dice.float() * 24,
            color = when (Dice.d(3)) {
                1 -> Color.RED
                2 -> Color.ORANGE
                else -> Color.YELLOW
            },
            shapeType = ShapeRenderer.ShapeType.Filled
        )
    }

}