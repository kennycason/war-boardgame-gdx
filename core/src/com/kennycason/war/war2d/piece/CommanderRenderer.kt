package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.war2d.graphics.GraphicsGdx
import com.kennycason.war.core.piece.Commander

class CommanderRenderer : PieceRenderer<Commander> {
    override fun render(piece: Commander, x: Float, y: Float) {
        val color = piece.color
        val center = Constants.TILE_DIM / 2

        // hull
        GraphicsGdx.drawRect(
            x + center - 15, y + center - 20,
            30f, 40f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // main gun
        GraphicsGdx.drawRect(
            x + center - 15, y + center + 10,
            10f, 20f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        GraphicsGdx.drawRect(
            x + center + 5, y + center + 10,
            10f, 20f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // turret
        GraphicsGdx.drawTriangle(
            x + center - 21, y + center + 11,
            x + center + 21, y + center + 11,
            x + center, y + center - 26,
            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
            ShapeRenderer.ShapeType.Filled
        )
        GraphicsGdx.drawCircle(
            x + center, y + center - 3,
            8f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}