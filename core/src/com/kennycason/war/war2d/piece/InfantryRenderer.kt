package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.war2d.graphics.GraphicsGdx
import com.kennycason.war.core.piece.Infantry

class InfantryRenderer : PieceRenderer<Infantry> {
    override fun render(piece: Infantry, x: Float, y: Float) {
        val center = Constants.TILE_DIM / 2
        GraphicsGdx.drawCircle(
            x + center, y + center + 13,
            10f,
            piece.color,
            ShapeRenderer.ShapeType.Filled
        )
        GraphicsGdx.drawRect(
            x + center - 7, y + center - 10,
            14f, 30f,
            piece.color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}