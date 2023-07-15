package com.kennycason.war.core.piece

enum class PieceType(val score: Int) {
    INFANTRY(1),
    TANK(3),
    ARTILLERY(4),
    MISSILE(5),
    BOMBER(6),
    COMMANDER(100)
}


