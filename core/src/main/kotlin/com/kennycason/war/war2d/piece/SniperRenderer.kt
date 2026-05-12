package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.core.piece.Sniper
import com.kennycason.war.war2d.graphics.GraphicsGdx

/**
 * Renders a Sniper piece as a rifle crosshair/scope reticle:
 * A thin circle with horizontal and vertical cross lines extending
 * beyond the circle, plus a small center dot.
 */
class SniperRenderer : PieceRenderer<Sniper> {
    override fun render(piece: Sniper, x: Float, y: Float) {
        val color = piece.player.color
        val center = Constants.TILE_DIM / 2
        val centerX = x + center
        val centerY = y + center

        val radius = 18f
        val crossExtend = 14f
        val lineWidth = 3f

        // Circle (thin outline)
        GraphicsGdx.drawCircle(
            centerX, centerY,
            radius,
            color,
            ShapeRenderer.ShapeType.Line,
            lineWidth = lineWidth
        )

        // Horizontal cross line (extends beyond circle on both sides)
        GraphicsGdx.drawLine(
            centerX - radius - crossExtend, centerY,
            centerX + radius + crossExtend, centerY,
            color,
            ShapeRenderer.ShapeType.Line,
            lineWidth = lineWidth
        )

        // Vertical cross line (extends beyond circle on both sides)
        GraphicsGdx.drawLine(
            centerX, centerY - radius - crossExtend,
            centerX, centerY + radius + crossExtend,
            color,
            ShapeRenderer.ShapeType.Line,
            lineWidth = lineWidth
        )

        // Center dot
        GraphicsGdx.drawCircle(
            centerX, centerY,
            3f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}
