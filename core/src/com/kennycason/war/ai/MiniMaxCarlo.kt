package com.kennycason.war.ai

import com.badlogic.gdx.utils.Pool
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.core.piece.*

/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MiniMaxCarlo(
    private val maxDepth: Int = 1,
    private val player: Player
) : MoveMaker {

//    private val boardPool = BoardPool(100, 50_000)

    override fun make(board: Board): Move? {
        println("Minimax start")
        val initialState = MiniMaxNode(board)
        val maxState = evaluate(initialState, 0)

        if (maxState != null) {
            val move = maxState.move!!
            board.state[move.fromX][move.fromY].piece!!.applyMove(board, move)
        }

        return maxState?.move
    }

    private fun evaluate(node: MiniMaxNode, depth: Int): MiniMaxNode? {
        val children = mutableListOf<MiniMaxNode>()

        val board = node.board
        val piecesForColor = getPiecesForColor(board) // consider in-lining array iteration for performance
        for (piece in piecesForColor) {
            val moves = piece.generatePossibleMoves(board)
            for (move in moves) {
                val newBoard = Board(width = board.width, height = board.height)
                copyBoard(board, newBoard)

                val newPiece = newBoard.state[move.fromX][move.fromY].piece!!
                newPiece.applyMove(newBoard, move)

                val childNode = MiniMaxNode(
                    board = newBoard,
                    move = move,
                    previousNode = node
                )
                children.add(childNode)

                if (depth < maxDepth && !board.isFinished()) {
                    childNode.score = getScore(board, move)
                    val miniMaxNode = evaluate(childNode, depth + 1)
                    childNode.score += miniMaxNode?.score ?: 0
                }

//                println(
//                    "$depth ${board.currentPlayer}: " +
//                            "score: ${childNode.score}, " +
//                            "${move.pieceType.name.substring(0, 3)} [${move.fromX}, ${move.fromY}] to [${move.toX}, ${move.toY}]"
//                )

            }
        }

        // apply min / max
        return if (player == board.currentPlayer) children.maxBy { it.score }
        else children.minBy { it.score }

    }

    private fun getScore(board: Board, move: Move): Int {
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

// the board state and the last move
class MiniMaxNode(
    val board: Board,
    val move: Move? = null,
    val previousNode: MiniMaxNode? = null,
    var score: Int = 0
)

class BoardPool(init: Int, max: Int) : Pool<Board>(init, max) {
    override fun newObject(): Board {
        return Board()
    }
}

fun copyBoard(from: Board, to: Board) {
    for (y in 0 until from.height) {
        for (x in 0 until from.width) {
            to.state[x][y].piece = copyPiece(from.state[x][y].piece)
            to.state[x][y].elevation = from.state[x][y].elevation
            to.state[x][y].highlight = from.state[x][y].highlight
        }
    }
    to.turnCount = from.turnCount
    to.currentPlayer = from.currentPlayer
    to.blackScore = from.blackScore
    to.whiteScore = from.whiteScore
}

// most pieces don't hold state and don't need to be copied
fun copyPiece(piece: Piece?): Piece? {
    return when (piece?.type) {
        PieceType.INFANTRY -> Infantry(piece.player, piece.x, piece.y)
        PieceType.TANK -> Tank(piece.player, piece.x, piece.y)
        PieceType.ARTILLERY -> Artillery(
            player = piece.player,
            x = piece.x,
            y = piece.y
        ).also {
            it.isReloading = (piece as Artillery).isReloading
            it.lastAttackTurn = piece.lastAttackTurn
        }
        PieceType.MISSILE -> Missile(piece.player, piece.x, piece.y)
        PieceType.BOMBER -> Bomber(piece.player, piece.x, piece.y)
        PieceType.COMMANDER -> Commander(piece.player, piece.x, piece.y)
        null -> null
    }
}