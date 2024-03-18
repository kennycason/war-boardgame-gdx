package com.kennycason.war.ai

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveMaker
import java.util.concurrent.Executors
import java.util.concurrent.Future

enum class AsyncMoveState2 {
    WAITING, START_THINKING, THINKING
}

/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MiniMaxCarlo2Async(
    maxDepth: Int = 1,
    player: Player,
    noise: Double = 0.4
) : MoveMaker {
    private val executorService = Executors.newSingleThreadExecutor()
    private val miniMaxCarlo = MiniMaxCarlo2(maxDepth = maxDepth, player = player, noise = noise)
    @Volatile
    private var state = AsyncMoveState2.WAITING
    private var moveFuture: Future<Move?>? = null

//    private val boardPool = BoardPool(100, 50_000)

    override fun make(board: Board): Move? {
        return synchronized(state) {
            when (state) {
                AsyncMoveState2.WAITING -> {
//                    println("AI WAITING")
                    state = AsyncMoveState2.START_THINKING
                    moveFuture = null
                    null
                }

                AsyncMoveState2.START_THINKING -> {
//                    println("AI START_THINKING")
                    state = AsyncMoveState2.THINKING
                    moveFuture = executorService.submit<Move> {
                        miniMaxCarlo.evaluate(board)
                    }
                    null
                }

                AsyncMoveState2.THINKING -> {
                    println("AI THINKING")
                    if (moveFuture!!.isDone) {
                        state = AsyncMoveState2.WAITING
                        val move = moveFuture!!.get()
                        if (move != null) {
                            board.state[move.fromX][move.fromY].piece!!.applyMove(board, move)
                        }
                        moveFuture = null
                        println("AI FINISHED")
                        move
                    } else {
                        println(board)
                        println("AI THINKING NOT DONE")
                        null
                    }
                }
            }
        }
    }

}
