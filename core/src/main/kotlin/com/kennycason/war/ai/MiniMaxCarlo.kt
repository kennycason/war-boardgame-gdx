package com.kennycason.war.ai

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveEvaluator
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.util.Dice
import com.kennycason.war.util.copyBoard


/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MiniMaxCarlo(
    private val maxDepth: Int = 1,
    private val player: Player,
    private val noise: Double = 0.0,
    private val log: Boolean = false,
    private val randomWalkProbability: Double = 0.05,
    private val maxRandomWalkDepth: Int = maxDepth + 1
) : MoveMaker, MoveEvaluator {

    init {
        if (maxRandomWalkDepth < maxDepth) throw IllegalStateException("maxRandomWalkDepth must be >= maxDepth")
    }

    override fun make(board: Board): Move? {
        val boardCopy = copyBoard(board)
        val move = evaluate(boardCopy)
        if (move != null) {
            board[move.fromX, move.fromY].piece!!.applyMove(board, move)
        }
        return move
    }

    override fun evaluate(board: Board): Move? {
        val startTime = System.currentTimeMillis()
        println("Minimax start turn: ${board.currentPlayer.name}")

        val moveScorer = MoveScorer(board, player, noise)
        val initialState = MiniMaxNode()
        val maxState = evaluate(board, initialState, 1, moveScorer)

        val timeElapsed = (System.currentTimeMillis() - startTime)
        println("Minimax finish turn: ${board.currentPlayer.name}, time ${timeElapsed}ms")
        return maxState.move
    }

    // depth-first apply/unapply
    private fun evaluate(
        board: Board,
        node: MiniMaxNode,
        depth: Int,
        moveScorer: MoveScorer
    ): MiniMaxNode {
        val children = mutableListOf<MiniMaxNode>()

        val piecesForColor = getPiecesForColor(board) // consider in-lining array iteration for performance
        for (piece in piecesForColor) {
            val moves = piece.generatePossibleMoves(board)
            for (move in moves) {
                val moveScore = moveScorer.calculate(move, depth)

                if (log) {
                    val padding = if (depth == 0) "" else "  ".repeat(depth)
                    //@formatter:off
                    println("$padding$depth ${board.currentPlayer} ${move.displayText()}, score: ${"%.3f".format(moveScore.totalScore())}")
//                    println("$padding$depth ${board.currentPlayer} " +
//                            "${move.displayText()}, " +
//                            "score: ${moveScore.totalScore()}, move: ${moveScore.score}, noise: ${moveScore.noise}, advance: ${moveScore.commanderAdvance}"
//                    )
                }

                piece.applyMove(board, move)

                val childNode = MiniMaxNode(
                    previous = node,
                    move = move,
                    score = moveScore.totalScore()
                )

                children.add(childNode)

                // TODO only walk promising paths?
                val shouldMonteCarloWalk = depth >= maxDepth && Dice.double() < randomWalkProbability && depth < maxRandomWalkDepth
                //println("carlo: $shouldMonteCarloWalk ${depth >= maxDepth} ${Dice.double() < randomWalkProbability} ${depth < maxRandomWalkDepth}")
                val continueEvaluation = depth < maxDepth || shouldMonteCarloWalk
                if (continueEvaluation && !board.isFinished()) {
                    val miniMaxNode = evaluate(board, childNode, depth + 1, moveScorer)
                    childNode.score += miniMaxNode.score
                }

                piece.undoMove(board, move)
            }
        }
        // apply simple min / max
        if (children.isEmpty()) return MiniMaxNode()
        return if (player == board.currentPlayer) children.maxBy { it.score }
        else children.minBy { it.score }
    }

    private fun getPiecesForColor(board: Board): List<Piece> {
        val pieces = mutableListOf<Piece>()
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val piece = board[x, y].piece
                if (piece != null && piece.player == board.currentPlayer) {
                    pieces.add(piece)
                }
            }
        }
        return pieces
    }

}

class MiniMaxNode(
    val previous: MiniMaxNode? = null,
    val move: Move? = null, // root node is null
    var score: Double = 0.0
)


