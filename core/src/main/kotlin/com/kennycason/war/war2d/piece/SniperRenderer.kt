package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.core.piece.Sniper
import com.kennycason.war.war2d.graphics.GraphicsGdx

/**
 * Renders a Sniper piece as a scope crosshair:
 * A circle with 4 lines extending in N, E, S, W directions.
 */
class SniperRenderer : PieceRenderer<Sniper> {
    override fun render(piece: Sniper, x: Float, y: Float) {
        val color = piece.player.color
        val center = Constants.TILE_DIM / 2
        val centerX = x + center
        val centerY = y + center

        val outerRadius = 24f
        val innerRadius = 20f
        val lineLength = 15f
        val lineThickness = 3f

        // Outer circle (scope ring)
        GraphicsGdx.drawCircle(
            centerX, centerY,
            outerRadius,
            color,
            ShapeRenderer.ShapeType.Line,
            lineWidth = 10f
        )

        // Inner circle (cutout effect - slightly darker/lighter)
//        val innerColor = if (color == Color.WHITE)
//            Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
//        else
//            Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a)
//
//        GraphicsGdx.drawCircle(
//            centerX, centerY,
//            innerRadius,
//            innerColor,
//            ShapeRenderer.ShapeType.Filled
//        )

        // Small center dot
        GraphicsGdx.drawCircle(
            centerX, centerY,
            4f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // North line (up)
        GraphicsGdx.drawRect(
            centerX - lineThickness / 2, centerY + innerRadius - 2,
            lineThickness, lineLength,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // South line (down)
        GraphicsGdx.drawRect(
            centerX - lineThickness / 2, centerY - innerRadius - lineLength + 2,
            lineThickness, lineLength,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // East line (right)
        GraphicsGdx.drawRect(
            centerX + innerRadius - 2, centerY - lineThickness / 2,
            lineLength, lineThickness,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // West line (left)
        GraphicsGdx.drawRect(
            centerX - innerRadius - lineLength + 2, centerY - lineThickness / 2,
            lineLength, lineThickness,
            color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}
