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
        GraphicsGdx.drawCircle(
            x + 4f, y + Constants.TILE_DIM - 8,
            4f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        GraphicsGdx.drawRect(
            x, y + Constants.TILE_DIM - 20,
            8f, 12f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
        if (piece.isReloading) {
            GraphicsGdx.drawLine(
                x, y + Constants.TILE_DIM,
                x + 8f, y + Constants.TILE_DIM - 20f,
                Color.RED
            )
            GraphicsGdx.drawLine(
                x + 8f, y + Constants.TILE_DIM,
                x, y + Constants.TILE_DIM - 20f,
                Color.RED
            )
        }
    }
}