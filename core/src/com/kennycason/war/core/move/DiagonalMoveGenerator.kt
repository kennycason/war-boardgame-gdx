package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.util.orDefault
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

    private fun getScore(board: Board, x: Int, y: Int) = board.state[x][y].piece?.type?.score.orDefault(0)

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
                    moves.add(Move(piece.type, MoveType.MOVE, x, y, x - i, y + i, getScore(board, x - i, y + i)))
                }
            }
            else {
                if (canAttack && state[x - i][y + i].piece!!.player != piece.player) {
                    moves.add(Move(piece.type, MoveType.ATTACK, x, y, x - i, y + i, getScore(board, x - i, y + i)))
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
                    moves.add(Move(piece.type, MoveType.MOVE, x, y, x + i, y + i, getScore(board, x + i, y + i)))
                }
            }
            else {
                if (canAttack && state[x + i][y + i].piece!!.player != piece.player) {
                    moves.add(Move(piece.type, MoveType.ATTACK, x, y, x + i, y + i, getScore(board, x + i, y + i)))
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
                    moves.add(Move(piece.type, MoveType.MOVE, x, y, x - i, y - i, getScore(board, x - i, y - i)))
                }
            }
            else {
                if (canAttack && state[x - i][y - i].piece!!.player != piece.player) {
                    moves.add(Move(piece.type, MoveType.ATTACK, x, y, x - i, y - i, getScore(board, x - i, y - i)))
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
                    moves.add(Move(piece.type, MoveType.MOVE, x, y, x + i, y - i, getScore(board, x + i, y - i)))
                }
            }
            else {
                if (canAttack && state[x + i][y - i].piece!!.player != piece.player) {
                    moves.add(Move(piece.type, MoveType.ATTACK, x, y, x + i, y - i, getScore(board, x + i, y - i)))
                }
                if (!canGoThroughPieces) break
            }
            i++
        }
    }

}