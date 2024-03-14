package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.core.piece.Missile
import com.kennycason.war.war2d.graphics.GraphicsGdx

class MissileRenderer : PieceRenderer<Missile> {
    override fun render(piece: Missile, x: Float, y: Float) {
        val color = piece.player.color
        val center = Constants.TILE_DIM / 2

        GraphicsGdx.drawLine(
            x + center - 26, y + center - 26,
            x + center + 26, y + center + 26,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        GraphicsGdx.drawLine(
            x + center - 26, y + center + 26,
            x + center + 26, y + center - 26,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        GraphicsGdx.drawCircle(
            x + center, y + center,
            23f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        GraphicsGdx.drawCircle(
            x + center, y + center,
            18f,
            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
            ShapeRenderer.ShapeType.Filled
        )

        GraphicsGdx.drawCircle(
            x + center, y + center,
            8f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}