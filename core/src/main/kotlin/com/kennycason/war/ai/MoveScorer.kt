package com.kennycason.war.ai

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.piece.Commander
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class MoveScore(
    // base score of move
    val score: Double,

    // modifier to aware pieces for advancing towards enemy commander
    val commanderAdvance: Double,

    // random noise to add move diversity
    val noise: Double
) {
    fun totalScore() = score + commanderAdvance + noise
}

class MoveScorer(
    private val board: Board,
    private val player: Player,
    private val noise: Double = 0.0
) {
    private val whiteCommander = board.getCommander(Player.WHITE)
    private val blackCommander = board.getCommander(Player.BLACK)

    fun calculate(move: Move, depth: Int): MoveScore {
        val playerModifier = getPlayerModifier()

        return MoveScore(
            score = move.score * playerModifier,
            commanderAdvance = getCommanderAdvanceScore(move) * playerModifier,
            noise = generateNoise(depth)
        )
    }

    private fun getPlayerModifier(): Double {
        return if (player == board.currentPlayer) 1.0
        else -1.0
    }

    private fun getCommanderAdvanceScore(
        move: Move,
    ): Double {
        val enemyCommander = getEnemyCommander() ?: return 0.0

        val distanceBefore = sqrt(
            (move.fromX - enemyCommander.x).toDouble().pow(2.0) + (move.fromY - enemyCommander.y).toDouble().pow(2.0)
        )
        val distanceAfter = sqrt(
            (move.toX - enemyCommander.x).toDouble().pow(2.0) + (move.toY - enemyCommander.y).toDouble().pow(2.0)
        )

        return abs(distanceBefore - distanceAfter) / 10.0
    }

    private fun getEnemyCommander(): Commander? {
        return when (board.currentPlayer) {
            Player.WHITE -> blackCommander
            Player.BLACK -> whiteCommander
        }
    }

    // Scale the noise by intensity and inversely by depth to reduce impact on immediate moves
    private fun generateNoise(depth: Int): Double {
        if (noise == 0.0) return 0.0
        val noiseIntensity = noise / depth
        val noise = (Math.random() - 0.5) * noiseIntensity // Generate noise between -intensity and +intensity
        return noise
    }
}