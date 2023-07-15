package com.kennycason.war.ai

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Pool
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.core.piece.Artillery
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.core.piece.PieceType

/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MiniMaxCarlo(
    private val maxDepth: Int = 1,
    private val color: Color
) : MoveMaker {

    private val boardPool = BoardPool(100, 50_000)

    private var maxNode: MiniMaxNode? = null

    override fun makeMove(board: Board): Move? {
        maxNode = null
        val rootNode = MiniMaxNode(board)
        val maxNode = evaluate(rootNode, 0)
        return getFirstMove(maxNode)
    }

    private fun evaluate(node: MiniMaxNode, depth: Int): MiniMaxNode? {
        val children = mutableListOf<MiniMaxNode>()

        val board = node.board
        // consider create list of pieces in board for faster iteration over pieces
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val piece = board.state[x][y].piece
                if (piece != null && piece.color == color) {
                    val moves = piece.generatePossibleMoves(board)
                    for (move in moves) {
                        val newBoard = boardPool.obtain()
                        copyBoard(board, newBoard)
                        piece.applyMove(newBoard, move)
                        val childNode = MiniMaxNode(
                            board = newBoard,
                            move = move,
                            previousNode = node
                        )
                        println("${depth}: ${board.state[move.fromX][move.fromY].piece?.type?.name?.substring(0, 3)} to [${move.toX}, ${move.toY}], score: ${getScore(childNode)}")

                        if (maxNode == null) {
                            maxNode = childNode
                        }
                        else {
                            if (getScore(childNode) > getScore(maxNode!!)) {
                                maxNode = childNode
                            }
                        }
                        children.add(childNode)
                    }
                }
            }
        }

        if (children.isEmpty()) {
            return maxNode
        }

        if (depth == maxDepth) {
            return maxNode
        }

        for (child in children) {
            return evaluate(child, depth + 1)
        }

        return maxNode
    }

    private fun getScore(node: MiniMaxNode): Int {
        return when (node.board.currentTurn) {
            Color.BLACK -> node.board.blackScore - node.board.whiteScore
            Color.WHITE -> node.board.whiteScore - node.board.blackScore
            else -> throw IllegalStateException("invalid turn")
        }
    }

    private fun getFirstMove(node: MiniMaxNode?): Move? {
        node ?: return null

//        val move = node.move
//        val piece = node.board.state[move.to]
//        println("${move. .name.substring(0, 3)} [${node.fromX + 1}, ${maxNode.fromY + 1}] -> [${maxNode.toX + 1}, ${maxNode.toY + 1}]")
        if (node.previousNode == null) return node.move

        return getFirstMove(node.previousNode)
    }
}

// the board state and the last move
class MiniMaxNode(
    val board: Board,
    val move: Move? = null,
    val previousNode: MiniMaxNode? = null
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
    to.currentTurn = from.currentTurn
    to.blackScore = from.blackScore
    to.whiteScore = from.whiteScore
}

// most pieces don't hold state and don't need to be copied
fun copyPiece(piece: Piece?): Piece? {
    return when (piece?.type) {
        PieceType.ARTILLERY -> Artillery(
            color = piece.color,
            x = piece.x,
            y = piece.y
        ).also {
            it.isReloading = (piece as Artillery).isReloading
            it.lastAttackTurn = piece.lastAttackTurn
        }
        else -> piece
    }
}