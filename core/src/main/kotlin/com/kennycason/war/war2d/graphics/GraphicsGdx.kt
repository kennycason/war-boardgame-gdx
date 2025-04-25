package com.kennycason.war.war2d.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils

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

    fun drawDashedRect(
        x: Float, y: Float, width: Float, height: Float,
        color: Color = Color.GREEN,
        lineWidth: Float = 1f,
        dashLength: Float = 5f,
        gapLength: Float = 5f
    ) {
        // Draw top line
        drawDashedLine(x, y + height, x + width, y + height, color, lineWidth, dashLength, gapLength)
        // Draw right line
        drawDashedLine(x + width, y + height, x + width, y, color, lineWidth, dashLength, gapLength)
        // Draw bottom line
        drawDashedLine(x + width, y, x, y, color, lineWidth, dashLength, gapLength)
        // Draw left line
        drawDashedLine(x, y, x, y + height, color, lineWidth, dashLength, gapLength)
    }

    fun drawDashedLine(
        x1: Float, y1: Float, x2: Float, y2: Float,
        color: Color = Color.GREEN,
        lineWidth: Float = 1f,
        dashLength: Float = 5f,
        gapLength: Float = 5f
    ) {
        val dx = x2 - x1
        val dy = y2 - y1
        val length = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        val angle = Math.atan2(dy.toDouble(), dx.toDouble()).toFloat()

        val cos = Math.cos(angle.toDouble()).toFloat()
        val sin = Math.sin(angle.toDouble()).toFloat()

        batch().end()
        shape().begin(ShapeRenderer.ShapeType.Line)
        Gdx.gl.glLineWidth(lineWidth)
        shape().color = color

        var drawn = 0f
        while (drawn < length) {
            val dashEnd = Math.min(drawn + dashLength, length)
            shape().line(
                x1 + drawn * cos, y1 + drawn * sin,
                x1 + dashEnd * cos, y1 + dashEnd * sin
            )
            drawn += dashLength + gapLength
        }

        shape().end()
        Gdx.gl.glLineWidth(1f)
        batch().begin()
    }

    fun drawArc(
        x: Float, y: Float, radius: Float,
        startAngle: Float, endAngle: Float,
        color: Color = Color.GREEN,
        shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line,
        lineWidth: Float = 1f,
        segments: Int = 20
    ) {
        batch().end()
        shape().begin(shapeType)
        Gdx.gl.glLineWidth(lineWidth)
        shape().color = color

        val angleStep = (endAngle - startAngle) / segments
        for (i in 0 until segments) {
            val angle1 = startAngle + i * angleStep
            val angle2 = startAngle + (i + 1) * angleStep

            val x1 = x + radius * MathUtils.cosDeg(angle1)
            val y1 = y + radius * MathUtils.sinDeg(angle1)
            val x2 = x + radius * MathUtils.cosDeg(angle2)
            val y2 = y + radius * MathUtils.sinDeg(angle2)

            shape().line(x1, y1, x2, y2)
        }

        shape().end()
        Gdx.gl.glLineWidth(1f)
        batch().begin()
    }
}
