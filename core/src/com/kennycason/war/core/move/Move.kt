package com.kennycason.war.core.move

data class Move(
    val fromX: Int,
    val fromY: Int,
    val toX: Int,
    val toY: Int,
    val moveType: MoveType = MoveType.MOVE,
    val score: Int = 0
)