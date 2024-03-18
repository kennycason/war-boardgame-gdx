package com.kennycason.war.war2d.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

object GraphicsGdx {
    private var batch: SpriteBatch? = null
    private var shapeRenderer: ShapeRenderer? = null

    fun batch(): SpriteBatch {
        if (batch == null) {
            batch = SpriteBatch()
        }
        return batch!!
    }

    fun shape(): ShapeRenderer {
        if (shapeRenderer == null) {
            shapeRenderer = ShapeRenderer()
        }
        return shapeRenderer!!
    }

    fun drawRect(
        x: Float, y: Float, width: Float, height: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {

        batch().end()
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        shape().begin(shapeType)
        shape().color = color
        shape().rect(x, y, width, height)
        shape().end()
        batch().begin()
    }

    fun drawCircle(
        x: Float, y: Float, radius: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line,
        lineWidth: Float = 1f
    ) {
        batch().end()
        shape().begin(shapeType)
        Gdx.gl.glLineWidth(lineWidth)
        shape().color = color
        shape().circle(x, y, radius)
        shape().end()
        Gdx.gl.glLineWidth(1f)
        batch().begin()
    }

    fun drawLine(
        x: Float, y: Float, x2: Float, y2: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line,
        lineWidth: Float = 1f
    ) {
        batch().end()
        shape().begin(shapeType)
        Gdx.gl.glLineWidth(lineWidth)
        shape().color = color
        shape().line(x, y, x2, y2)
        shape().end()
        Gdx.gl.glLineWidth(1f)
        batch().begin()
    }

    fun drawTriangle(
        x: Float, y: Float, x2: Float, y2: Float, x3: Float, y3: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line
    ) {
        batch().end()
        shape().begin(shapeType)
        shape().color = color
        shape().triangle(x, y, x2, y2, x3, y3)
        shape().end()
        batch().begin()
    }
}