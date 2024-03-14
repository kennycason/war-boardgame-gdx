package com.kennycason.war.war2d.piece

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.Constants
import com.kennycason.war.core.piece.AirDefense
import com.kennycason.war.core.piece.Tank
import com.kennycason.war.war2d.graphics.GraphicsGdx

class AirDefenseRenderer : PieceRenderer<AirDefense> {
    override fun render(piece: AirDefense, x: Float, y: Float) {
        val color = piece.player.color
        val center = Constants.TILE_DIM / 2

        // hull
        GraphicsGdx.drawRect(
            x + center - 20, y + center - 25,
            36f, 25f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // left gun
        GraphicsGdx.drawRect(
            x + center - 20, y + center - 25,
            8f, 40f,
            color,
            ShapeRenderer.ShapeType.Filled
        )

        // right gun
        GraphicsGdx.drawRect(
            x + center + 16, y + center - 25,
            8f, 40f,
            color,
            ShapeRenderer.ShapeType.Filled
        )
    }
}