package com.kennycason.war.ai

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveEvaluator
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.util.copyBoard
import java.util.concurrent.Executors
import java.util.concurrent.Future

enum class AsyncMoveState {
    WAITING, START_THINKING, THINKING
}

/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MoveMakerAsync(
    private val moveEvaluator: MoveEvaluator
) : MoveMaker {
    private val executorService = Executors.newSingleThreadExecutor()

    @Volatile
    private var state = AsyncMoveState.WAITING
    private var moveFuture: Future<Move?>? = null

    override fun make(board: Board): Move? {
        return synchronized(state) {
            when (state) {
                AsyncMoveState.WAITING -> {
                    println("AI WAITING -> START_THINKING")
                    state = AsyncMoveState.START_THINKING
                    moveFuture = null
                    null
                }

                AsyncMoveState.START_THINKING -> {
                    println("AI START_THINKING -> THINKING")
                    state = AsyncMoveState.THINKING
                    moveFuture = executorService.submit<Move> {
                        println("AI EXECUTOR START EVALUATION")
                        val boardCopy = copyBoard(board)
                        moveEvaluator.evaluate(boardCopy)
                    }
                    null
                }

                AsyncMoveState.THINKING -> {
                    println("AI THINKING")
                    if (moveFuture!!.isDone) {
                        state = AsyncMoveState.WAITING
                        val move = moveFuture!!.get()
                        if (move != null) {
                            board[move.fromX, move.fromY].piece!!.applyMove(board, move)
                            println("AI MOVE: ${move.displayText()}")
                        }
                        moveFuture = null
                        println("AI FINISHED THINKING")
                        move
                    } else null
                }
            }
        }
    }

}
