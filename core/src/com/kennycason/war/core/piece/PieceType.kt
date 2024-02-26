package com.kennycason.war.core.piece

enum class PieceType(val score: Double) {
    INFANTRY(1.0),
    TANK(2.0),
    ARTILLERY(3.0),
    MISSILE(4.0),
    AIR_DEFENSE(4.0),
    BOMBER(5.0),
    COMMANDER(1000.0)
}


