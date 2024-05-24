package com.kennycason.war.ai

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveEvaluator
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.util.copyBoard


/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MiniMaxCarlo(
    private val maxDepth: Int = 1,
    private val player: Player,
    private val noise: Double = 0.0,
    private val log: Boolean = false
) : MoveMaker, MoveEvaluator {

    override fun make(board: Board): Move? {
        val boardCopy = copyBoard(board)
        val move = evaluate(boardCopy)
        if (move != null) {
            board[move.fromX, move.fromY].piece!!.applyMove(board, move)
        }
        return move
    }

    override fun evaluate(board: Board): Move? {
        println("Minimax start turn: ${board.currentPlayer.name}")

        val moveScorer = MoveScorer(board, player, noise)
        val initialState = MiniMaxNodeV2()
        val maxState = evaluate(board, initialState, 1, moveScorer)

        println("Minimax finish turn: ${board.currentPlayer.name}")
        return maxState.move
    }

    // depth-first apply/unapply
    private fun evaluate(
        board: Board,
        node: MiniMaxNodeV2,
        depth: Int,
        moveScorer: MoveScorer
    ): MiniMaxNodeV2 {
        val children = mutableListOf<MiniMaxNodeV2>()

        val piecesForColor = getPiecesForColor(board) // consider in-lining array iteration for performance
        for (piece in piecesForColor) {
            val moves = piece.generatePossibleMoves(board)
            for (move in moves) {
                val moveScore = moveScorer.calculate(move, depth)

                if (log) {
                    //@formatter:off
                    println("$depth ${board.currentPlayer} " +
                            "${move.displayText()}, " +
                            "score: ${moveScore.totalScore()}, move: ${moveScore.score}, noise: ${moveScore.noise}, advance: ${moveScore.commanderAdvance}"
                    )
                }

                piece.applyMove(board, move)

                val childNode = MiniMaxNodeV2(
                    previous = node,
                    move = move,
                    score = moveScore.totalScore()
                )

                children.add(childNode)

                if (depth < maxDepth && !board.isFinished()) {
                    val miniMaxNode = evaluate(board, childNode, depth + 1, moveScorer)
                    childNode.score += miniMaxNode.score
                }

                piece.undoMove(board, move)
            }
        }
        // apply simple min / max
        if (children.isEmpty()) return MiniMaxNodeV2()
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

class MiniMaxNodeV2(
    val previous: MiniMaxNodeV2? = null,
    val move: Move? = null, // root node is null
    var score: Double = 0.0
)


