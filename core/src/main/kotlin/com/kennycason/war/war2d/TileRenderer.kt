package com.kennycason.war.war2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.kennycason.war.core.board.Tile
import com.kennycason.war.war2d.graphics.GraphicsGdx
import com.kennycason.war.core.piece.*
import com.kennycason.war.war2d.piece.*

class TileRenderer(
    private val tileDim: Int
) {

    private val infantryRenderer = InfantryRenderer()
    private val tankRenderer = TankRenderer()
    private val artilleryRenderer = ArtilleryRenderer()
    private val bomberRenderer = BomberRenderer()
    private val airDefenseRenderer = AirDefenseRenderer()
    private val missileRenderer = MissileRenderer()
    private val commanderRenderer = CommanderRenderer()
    private val excavatorRenderer = ExcavatorRenderer()

    fun render(tile: Tile, x: Float, y: Float) {
        // draw top tile
        val tileElevationHeight = tile.elevation * 10f
        val tileY = y + tileElevationHeight

        if (tile.highlight == TileHighlight.MOVE || tile.highlight == TileHighlight.ATTACK || tile.highlight == TileHighlight.POTENTIAL_ATTACK) {
            GraphicsGdx.drawRect(
                x, tileY,
                tileDim.toFloat(), tileDim.toFloat(),
                getElevationColor(tile.elevation),
                ShapeRenderer.ShapeType.Filled
            )
            GraphicsGdx.drawRect(
                x, tileY,
                tileDim.toFloat(), tileDim.toFloat(),
                getColor(tile),
                ShapeRenderer.ShapeType.Filled
            )
        } else {
            GraphicsGdx.drawRect(
                x, tileY,
                tileDim.toFloat(), tileDim.toFloat(),
                getColor(tile),
                ShapeRenderer.ShapeType.Filled
            )
            GraphicsGdx.drawLine(
                x + tileDim.toFloat() / 2f, tileY,
                x + tileDim.toFloat(), tileY,
                GROUND_BORDER,
                ShapeRenderer.ShapeType.Line
            )
            GraphicsGdx.drawLine(
                x + tileDim.toFloat(), tileY,
                x + tileDim.toFloat(), tileY + tileDim.toFloat() / 3.0f,
                GROUND_BORDER,
                ShapeRenderer.ShapeType.Line
            )
        }

        // draw piece if needed
        val piece = tile.piece
        if (piece != null) {
            when (piece.type) {
                PieceType.INFANTRY -> infantryRenderer.render(piece as Infantry, x, tileY)
                PieceType.TANK -> tankRenderer.render(piece as Tank, x, tileY)
                PieceType.ARTILLERY -> artilleryRenderer.render(piece as Artillery, x, tileY)
                PieceType.BOMBER -> bomberRenderer.render(piece as Bomber, x, tileY)
                PieceType.AIR_DEFENSE -> airDefenseRenderer.render(piece as AirDefense, x, tileY)
                PieceType.MISSILE -> missileRenderer.render(piece as Missile, x, tileY)
                PieceType.COMMANDER -> commanderRenderer.render(piece as Commander, x, tileY)
                PieceType.EXCAVATOR -> excavatorRenderer.render(piece as Excavator, x, tileY)
            }
        }

        // draw wall if needed.
        if (tile.elevation > 0) {
            GraphicsGdx.drawRect(
                x, y,
                tileDim.toFloat(), tileElevationHeight,
                WALL,
                ShapeRenderer.ShapeType.Filled
            )
        }
    }

    private fun getColor(tile: Tile): Color {
        return when (tile.highlight) {
            TileHighlight.MOVE -> HIGHLIGHTED_MOVE
            TileHighlight.ATTACK -> HIGHLIGHTED_ATTACK
            TileHighlight.POTENTIAL_ATTACK -> HIGHLIGHTED_POTENTIAL_ATTACK
            TileHighlight.SELECTED -> Color.BROWN
            TileHighlight.NONE -> getElevationColor(tile.elevation)
        }
    }

    private fun getElevationColor(elevation: Int): Color {
        return when (elevation) {
            0 -> GROUND0
            1 -> GROUND1
            2 -> GROUND2
            3 -> GROUND3
            4 -> GROUND4
            5 -> GROUND5
            else -> throw IllegalStateException("No color for elevation: $elevation")
        }
    }

    companion object {
        val GROUND_BORDER = Color(0.7f, 0.7f, 0.7f, 0.8f)

        val GROUND5 = Color(0.9f, 0.95f, 0.85f, 1f)
        val GROUND4 = Color(0.9f, 0.90f, 0.8f, 1f)
        val GROUND3 = Color(0.9f, 0.85f, 0.75f, 1f)
        val GROUND2 = Color(0.9f, 0.8f, 0.7f, 1f)
        val GROUND1 = Color(0.9f, 0.75f, 0.65f, 1f)
        val GROUND0 = Color(0.8f, 0.7f, 0.7f, 1f)
//        val GROUND0 = Color(0.9f, 0.7f, 0.6f, 1f)

        val WALL = Color(0.7f, 0.6f, 0.5f, 1f)

        val HIGHLIGHTED_MOVE = Color(0.4f, 0.7f, 0.4f, 0.5f)
        val HIGHLIGHTED_ATTACK = Color(0.7f, 0.4f, 0.4f, 0.5f)
        val HIGHLIGHTED_POTENTIAL_ATTACK = Color(0.9f, 0.6f, 0.3f, 0.5f)
    }
}
