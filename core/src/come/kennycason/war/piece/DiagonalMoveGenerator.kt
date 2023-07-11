package come.kennycason.war.piece

import come.kennycason.war.board.Board
import come.kennycason.war.board.Move
import come.kennycason.war.board.TileHighlight
import kotlin.math.abs

class DiagonalMoveGenerator(
    private val maxDistance: Int,
    private val startI: Int = 1,
    private val canGoThroughPieces: Boolean = false,
    private val canAttack: Boolean = true,
    private val requiredAttack: Boolean = false,
    private val ignoreHeight: Boolean = false
) {

    fun generatePossibleMoves(piece: Piece, board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moveLeftUp(piece, board, moves)
        moveRightUp(piece, board, moves)
        moveLeftDown(piece, board, moves)
        moveRightDown(piece, board, moves)
        return moves
    }

    private fun moveLeftUp(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        val state = board.state
        var i = startI
        while (true) {
            if (x - i < 0 || y + i >= state.size) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x - i][y + i].elevation - state[x - i + 1][y + i - 1].elevation) > 1) break

            if (state[x - i][y + i].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x - i, y + i, TileHighlight.MOVE))
                }
            }
            else {
                if (canAttack && state[x - i][y + i].piece!!.color != piece.color) {
                    moves.add(Move(x - i, y + i, TileHighlight.ATTACK))
                }
                if (!canGoThroughPieces) break
            }
            i++
        }
    }

    private fun moveRightUp(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        val state = board.state
        var i = startI
        while (true) {
            if (x + i >= state.size || y + i >= state.size) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x + i][y + i].elevation - state[x + i - 1][y + i - 1].elevation) > 1) break

            if (state[x + i][y + i].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x + i, y + i, TileHighlight.MOVE))
                }
            }
            else {
                if (canAttack && state[x + i][y + i].piece!!.color != piece.color) {
                    moves.add(Move(x + i, y + i, TileHighlight.ATTACK))
                }
                if (!canGoThroughPieces) break
            }
            i++
        }
    }

    private fun moveLeftDown(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        val state = board.state
        var i = startI
        while (true) {
            if (x - i < 0 || y - i < 0) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x - i][y - i].elevation - state[x - i + 1][y - i + 1].elevation) > 1) break

            if (state[x - i][y - i].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x - i, y - i, TileHighlight.MOVE))
                }
            }
            else {
                if (canAttack && state[x - i][y - i].piece!!.color != piece.color) {
                    moves.add(Move(x - i, y - i, TileHighlight.ATTACK))
                }
                if (!canGoThroughPieces) break
            }
            i++
        }
    }

    private fun moveRightDown(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        val state = board.state
        var i = startI
        while (true) {
            if (x + i >= state.size || y - i < 0) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x + i][y - i].elevation - state[x + i - 1][y - i + 1].elevation) > 1) break

            if (state[x + i][y - i].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x + i, y - i, TileHighlight.MOVE))
                }
            }
            else {
                if (canAttack && state[x + i][y - i].piece!!.color != piece.color) {
                    moves.add(Move(x + i, y - i, TileHighlight.ATTACK))
                }
                if (!canGoThroughPieces) break
            }
            i++
        }
    }

}