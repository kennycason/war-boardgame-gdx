package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.war2d.graphics.GraphicsGdx
import com.kennycason.war.core.piece.Bomber

class BomberRenderer : PieceRenderer<Bomber> {
    override fun render(piece: Bomber, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2

        GraphicsGdx.drawTriangle(
            x + center - 18, y + center - 18,
            x + center + 18, y + center + - 18,
            x + center, y + center + 16,
            piece.color,
            ShapeRenderer.ShapeType.Filled
        )

        GraphicsGdx.drawRect(
            x + center - 3, y + center - 25,
            6f, 14f,
            piece.color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}