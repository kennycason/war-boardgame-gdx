package com.kennycason.war.ai

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Pool
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveMaker
import com.kennycason.war.core.piece.*

/**
 * Perform MiniMax for n-layers, then apply monte-carlo from random/promising leaf nodes.
 */
class MiniMaxCarlo(
    private val maxDepth: Int = 1,
    private val color: Color
) : MoveMaker {

    private val boardPool = BoardPool(100, 50_000)

    private var maxNode: MiniMaxNode? = null

    override fun make(board: Board): Move? {
        println("Minimax start")
        maxNode = null
        val rootNode = MiniMaxNode(board)
        val maxNode = evaluate(rootNode, 0)
        val firstMove = getFirstMove(maxNode) // walk back up the tree to the original move
        if (firstMove != null) {
            board.state[firstMove.fromX][firstMove.fromY].piece!!.applyMove(board, firstMove)
            println("move: ${firstMove.pieceType.name.substring(0, 3)} " +
                    "[${firstMove.fromX}, ${firstMove.fromY}] to [${firstMove.toX}, ${firstMove.toY}], " +
                    "score ${getBoardScore(board, color)}")
        }
        return firstMove
    }

    // breadth-first
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
//                        val newBoard = boardPool.obtain()
                        val newBoard = Board(width = board.width, height = board.height)
                        copyBoard(board, newBoard)
                        val newPiece = newBoard.state[x][y].piece!!
                        newPiece.applyMove(newBoard, move)
                        val score = getBoardScore(newBoard, color)
//                        println("${depth}: ${move.pieceType.name.substring(0, 3)} [${move.fromX}, ${move.fromY}] to [${move.toX}, ${move.toY}], score: $score")


                        val childNode = MiniMaxNode(
                            board = newBoard,
                            move = move,
                            previousNode = node
                        )
                        if (maxNode == null) {
                            maxNode = childNode
                        }
                        else {
                            if (score > getBoardScore(maxNode!!.board, color)) {
                                maxNode = childNode
                                println("max: ${depth}: ${move.pieceType.name.substring(0, 3)} [${move.fromX}, ${move.fromY}] to [${move.toX}, ${move.toY}], score: $score")
                            }
                        }

                      //  boardPool.free(newBoard)
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

    private fun getFirstMove(node: MiniMaxNode?): Move? {
        if (node == null) return null

//        println("-- ${node.move?.pieceType?.name?.substring(0, 3)} [${node.move?.fromX}, ${node.move?.fromY}] to [${node.move?.toX}, ${node.move?.toY}], score: ${getBoardScore(node.board, color)}, " +
//                "turn: ${node.board.turnCount}")

        if (node.previousNode == null) return node.move
        if (node.previousNode.move == null) return node.move

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
        PieceType.INFANTRY -> Infantry(piece.color, piece.x, piece.y)
        PieceType.TANK -> Tank(piece.color, piece.x, piece.y)
        PieceType.ARTILLERY -> Artillery(
                color = piece.color,
                x = piece.x,
                y = piece.y
            ).also {
                it.isReloading = (piece as Artillery).isReloading
                it.lastAttackTurn = piece.lastAttackTurn
            }
        PieceType.MISSILE -> Missile(piece.color, piece.x, piece.y)
        PieceType.BOMBER -> Bomber(piece.color, piece.x, piece.y)
        PieceType.COMMANDER -> Commander(piece.color, piece.x, piece.y)
        null -> null
    }
}

private fun getBoardScore(board: Board, color: Color): Int {
    return when (color) {
        Color.BLACK -> board.blackScore - board.whiteScore
        Color.WHITE -> board.whiteScore - board.blackScore
        else -> throw IllegalStateException("invalid turn")
    }
}