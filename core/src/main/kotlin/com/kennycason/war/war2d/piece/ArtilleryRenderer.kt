package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.core.piece.Artillery
import com.kennycason.war.war2d.graphics.GraphicsGdx

class ArtilleryRenderer : PieceRenderer<Artillery> {
    override fun render(piece: Artillery, x: Float, y: Float) {
        val color = piece.player.color
        val center = Constants.TILE_DIM / 2

        // axle
        GraphicsGdx.drawRect(
            x + center - 25, y + center - 3,
            50f, 6f,
            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
            ShapeRenderer.ShapeType.Filled
        )

        // left wheel
        GraphicsGdx.drawRect(
            x + center - 30, y + center - 15,
            10f, 30f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // right wheel
        GraphicsGdx.drawRect(
            x + center + 20, y + center - 15,
            10f, 30f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // gun
        GraphicsGdx.drawRect(
            x + center - 3, y + center - 16,
            6f, 45f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // gun base
        GraphicsGdx.drawCircle(
            x + center, y + center,
            10f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // show reload symbol
        if (piece.isReloading) {
            // Draw circular arrow (reload symbol)
            val reloadIconX = x + Constants.TILE_DIM - 15f
            val reloadIconY = y + Constants.TILE_DIM - 15f
            val radius = 10f

            // Draw the arc (circular part of the arrow)
            GraphicsGdx.drawArc(
                reloadIconX, reloadIconY, radius,
                0f, 270f,
                Color.RED,
                ShapeRenderer.ShapeType.Line,
                4f
            )

            // Draw the arrow head
            GraphicsGdx.drawTriangle(
                reloadIconX + radius * 0.7f, reloadIconY - radius * 0.7f,
                reloadIconX + radius * 0.7f - 5f, reloadIconY - radius * 0.7f - 5f,
                reloadIconX + radius * 0.7f + 5f, reloadIconY - radius * 0.7f - 5f,
                Color.RED,
                ShapeRenderer.ShapeType.Filled
            )
        }
    }
}
