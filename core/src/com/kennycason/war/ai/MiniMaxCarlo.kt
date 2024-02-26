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
    private val player: Player,
    private val noise: Double = 0.5
) : MoveMaker {

//    private val boardPool = BoardPool(100, 50_000)

    override fun make(board: Board): Move? {
        val move = evaluate(board)
        if (move != null) {
            board.state[move.fromX][move.fromY].piece!!.applyMove(board, move)
        }
        return move
    }

    fun evaluate(board: Board): Move? {
        println("Minimax start turn: ${board.currentPlayer.color}")

        val initialState = MiniMaxNode(board)
        val maxState = evaluate(initialState, 1)

        println("Minimax finish turn: ${board.currentPlayer.color}")
        return maxState.move
    }

    private fun evaluate(node: MiniMaxNode, depth: Int): MiniMaxNode {
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
                    childNode.score = getMoveScore(board, move)
                    val miniMaxNode = evaluate(childNode, depth + 1)
                    val noise = generateNoise(depth)
                    childNode.score += miniMaxNode.score + noise
                }

//                println(
//                    "$depth ${board.currentPlayer}: " +
//                            "score: ${childNode.score}, " +
//                            "${move.pieceType.name.substring(0, 3)} [${move.fromX}, ${move.fromY}] to [${move.toX}, ${move.toY}]"
//                )

            }
        }
        // apply simple min / max
        return if (player == board.currentPlayer) children.maxBy { it.score }
        else children.minBy { it.score }
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

// the board state and the last move
class MiniMaxNode(
    val board: Board,
    val move: Move? = null,
    val previousNode: MiniMaxNode? = null,
    var score: Double = 0.0
)

class BoardPool(init: Int, max: Int) : Pool<Board>(init, max) {
    override fun newObject(): Board {
        return Board()
    }
}

fun copyBoard(from: Board, to: Board) {
    to.turnCount = from.turnCount
    to.currentPlayer = from.currentPlayer
    to.blackScore = from.blackScore
    to.whiteScore = from.whiteScore
    for (y in 0 until from.height) {
        for (x in 0 until from.width) {
            to.state[x][y].piece = copyPiece(to , from.state[x][y].piece)
            to.state[x][y].elevation = from.state[x][y].elevation
            to.state[x][y].highlight = from.state[x][y].highlight
        }
    }
}

// most pieces don't hold state and don't need to be copied
fun copyPiece(board: Board, piece: Piece?): Piece? {
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
            it.handleReloading(board)
        }

        PieceType.MISSILE -> Missile(piece.player, piece.x, piece.y)
        PieceType.AIR_DEFENSE -> AirDefense(piece.player, piece.x, piece.y)
        PieceType.BOMBER -> Bomber(piece.player, piece.x, piece.y)
        PieceType.COMMANDER -> Commander(piece.player, piece.x, piece.y)
        null -> null
    }
}