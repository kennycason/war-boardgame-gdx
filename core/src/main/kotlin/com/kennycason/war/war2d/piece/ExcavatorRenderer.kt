package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.core.piece.Excavator
import com.kennycason.war.core.piece.Tank
import com.kennycason.war.war2d.graphics.GraphicsGdx

class ExcavatorRenderer : PieceRenderer<Excavator> {
    override fun render(piece: Excavator, x: Float, y: Float) {
        val color = piece.player.color
        val center = Constants.TILE_DIM / 2

        // base track
        GraphicsGdx.drawRect(
            x + center - 15, y + center - 20,
            30f, 20f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        GraphicsGdx.drawCircle(
            x + center - 15, y + center - 10,
            10f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        GraphicsGdx.drawCircle(
            x + center + 15, y + center - 10,
            10f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        GraphicsGdx.drawRect(
            x + center - 9f, y + center - 8,
            18f, 22f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        GraphicsGdx.drawLine(
            x + center, y + center ,
            x + center + 20, y + center + 20,
            color,
            ShapeRenderer.ShapeType.Filled,
            lineWidth = 5f
        )
//
//        // turret
//        GraphicsGdx.drawCircle(
//            x + center, y + center,
//            12f,
//            if (color == Color.WHITE) Color(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a)
//            else Color(color.r + 0.2f, color.g + 0.2f, color.b + 0.2f, color.a),
//            ShapeRenderer.ShapeType.Filled
//        )
    }
}