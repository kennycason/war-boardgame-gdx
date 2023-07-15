package com.kennycason.war.core.board

import array2d
import com.badlogic.gdx.graphics.Color

data class Board(
    val width: Int = 11,
    val height: Int = 11,
    val state: Array<Array<Tile>> = array2d(width, height) { Tile() },
    var turnCount: Int = 0,
    var currentTurn: Color = Color.BLACK,
    var blackScore: Int = 0,
    var whiteScore: Int = 0
)