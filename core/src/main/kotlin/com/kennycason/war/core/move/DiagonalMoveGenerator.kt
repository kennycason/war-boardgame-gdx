package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.core.piece.PieceType
import com.kennycason.war.util.copyPiece
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

    private fun getPieceScore(board: Board, x: Int, y: Int, moveType: MoveType = MoveType.MOVE): Double {
        val piece = board.state[x][y].piece ?: return 0.0
        return when (piece.type) {
            PieceType.MISSILE -> {
                when (moveType) {
                    MoveType.MOVE -> piece.type.score
                    MoveType.ATTACK -> piece.type.score - PieceType.MISSILE.score + 1.0
                }
            }

            else -> piece.type.score
        }
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

            val targetPiece = state[x - i][y + i].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x - i, y + i,
                            score = getPieceScore(board, x - i, y + i)
                        )
                    )
                }
            } else {
                if (canAttack && targetPiece.player != piece.player) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x - i, y + i,
                            score = getPieceScore(board, x - i, y + i, MoveType.ATTACK),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
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

            val targetPiece = state[x + i][y + i].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x + i, y + i,
                            score = getPieceScore(board, x + i, y + i)
                        )
                    )
                }
            } else {
                if (canAttack && targetPiece.player != piece.player) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x + i, y + i,
                            score = getPieceScore(board, x + i, y + i, MoveType.ATTACK),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
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

            val targetPiece = state[x - i][y - i].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x - i, y - i,
                            score = getPieceScore(board, x - i, y - i)
                        )
                    )
                }
            } else {
                if (canAttack && targetPiece.player != piece.player) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x - i, y - i,
                            score = getPieceScore(board, x - i, y - i, MoveType.ATTACK),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
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

            val targetPiece = state[x + i][y - i].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x + i, y - i,
                            score = getPieceScore(board, x + i, y - i)
                        )
                    )
                }
            } else {
                if (canAttack && targetPiece.player != piece.player) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x + i, y - i,
                            score = getPieceScore(board, x + i, y - i, MoveType.ATTACK),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
                }
                if (!canGoThroughPieces) break
            }
            i++
        }
    }

}