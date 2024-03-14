package com.kennycason.war.ai

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.core.piece.*
import com.kennycason.war.util.copyBoard

/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MiniMaxCarlo2(
    private val maxDepth: Int = 1,
    private val player: Player,
    private val noise: Double = 0.5
) : MoveMaker {

    override fun make(board: Board): Move? {
        val boardCopy = copyBoard(board)
        val move = evaluate(boardCopy)
        if (move != null) {
            board.state[move.fromX][move.fromY].piece!!.applyMove(board, move)
        }
        return move
    }

    fun evaluate(board: Board): Move? {
        println("Minimax start turn: ${board.currentPlayer.name}")

        val initialState = MiniMaxNode2()
        val maxState = evaluate(board, initialState, 1)

        println("Minimax finish turn: ${board.currentPlayer.name}")
        return maxState.move
    }

    private fun evaluate(board: Board, node: MiniMaxNode2, depth: Int): MiniMaxNode2 {
        // println("eval depth=$depth")
        val children = mutableListOf<MiniMaxNode2>()

        val piecesForColor = getPiecesForColor(board) // consider in-lining array iteration for performance
        for (piece in piecesForColor) {
            val moves = piece.generatePossibleMoves(board)
            for (move in moves) {

                piece.applyMove(board, move)

                val childNode = MiniMaxNode2(
                    move = move,
                    previousNode = node,
                    score = getMoveScore(board, move) + generateNoise(depth)
                )
                children.add(childNode)

                if (depth < maxDepth && !board.isFinished()) {
                    val miniMaxNode = evaluate(board, childNode, depth + 1)
                    childNode.score += miniMaxNode.score
                }

                piece.undoMove(board, move)

//                println(
//                    "$depth ${board.currentPlayer}: " +
//                            "score: ${childNode.score}, " +
//                            "${move.pieceType.name.substring(0, 3)} [${move.fromX}, ${move.fromY}] to [${move.toX}, ${move.toY}]"
//                )

            }
        }
        // apply simple min / max
        if (children.isEmpty()) return MiniMaxNode2()
        return if (player == board.currentPlayer) children.minBy { it.score }
        else children.maxBy { it.score }
    }

    // Scale the noise by intensity and inversely by depth to reduce impact on immediate moves
    private fun generateNoise(depth: Int): Double {
        val noiseIntensity = noise / depth
        val noise = (Math.random() - 0.5) * noiseIntensity // Generate noise between -intensity and +intensity
        return noise
    }


    private fun getMoveScore(board: Board, move: Move): Double {
        return if (player == board.currentPlayer) move.score
        else -move.score
    }

    private fun getPiecesForColor(board: Board): List<Piece> {
        val pieces = mutableListOf<Piece>()
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val piece = board.state[x][y].piece
                if (piece != null && piece.player == board.currentPlayer) {
                    pieces.add(piece)
                }
            }
        }
        return pieces
    }

}

class MiniMaxNode2(
    val move: Move? = null, // root node is null
    val previousNode: MiniMaxNode2? = null,
    val destroyedPiece: Piece? = null,
    var score: Double = 0.0
)

