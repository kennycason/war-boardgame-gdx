package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.util.copyPiece
import kotlin.math.abs

class HorizontalVerticalMoveGenerator(
    private val maxDistance: Int,
    private val startI: Int = 1,
    private val canGoThroughOwnPiece: Boolean = false,
    private val canGoThroughEnemyPiece: Boolean = false,
    private val canAttack: Boolean = true,
    private val requiredAttack: Boolean = false,
    private val ignoreHeight: Boolean = false
) {

    fun generatePossibleMoves(piece: Piece, board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moveLeft(piece, board, moves)
        moveRight(piece, board, moves)
        moveUp(piece, board, moves)
        moveDown(piece, board, moves)
        return moves
    }

    private fun getPieceScore(board: Board, x: Int, y: Int) = board[x, y].piece?.type?.score ?: 0.0

    private fun moveLeft(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        var i = startI
        while (true) {
            if (x - i < 0) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(board[x - i, y].elevation - board[x - i + 1, y].elevation) > 1) break

            val targetPiece = board[x - i, y].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x - i, y,
                            score = getPieceScore(board, x - i, y)
                        )
                    )
                }
            } else {
                val isEnemyPiece = canAttack && targetPiece.player != piece.player
                if (isEnemyPiece) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x - i, y,
                            score = getPieceScore(board, x - i, y),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
                    if (!canGoThroughEnemyPiece) break
                } else {
                    if (!canGoThroughOwnPiece) break
                }
            }
            i++
        }
    }

    private fun moveRight(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        var i = startI
        while (true) {
            if (x + i >= board.width) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(board[x + i, y].elevation - board[x + i - 1, y].elevation) > 1) break

            val targetPiece = board[x + i, y].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x + i, y,
                            score = getPieceScore(board, x + i, y)
                        )
                    )
                }
            } else {
                val isEnemyPiece = canAttack && targetPiece.player != piece.player
                if (isEnemyPiece) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x + i, y,
                            score = getPieceScore(board, x + i, y),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
                    if (!canGoThroughEnemyPiece) break
                } else {
                    if (!canGoThroughOwnPiece) break
                }
            }
            i++
        }
    }


    private fun moveDown(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        var i = startI
        while (true) {
            if (y - i < 0) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(board[x, y - i].elevation - board[x, y - i + 1].elevation) > 1) break

            val targetPiece = board[x, y - i].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x, y - i,
                            score = getPieceScore(board, x, y - i)
                        )
                    )
                }
            } else {
                val isEnemyPiece = canAttack && targetPiece.player != piece.player
                if (isEnemyPiece) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x, y - i,
                            score = getPieceScore(board, x, y - i),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
                    if (!canGoThroughEnemyPiece) break
                } else {
                    if (!canGoThroughOwnPiece) break
                }
            }
            i++
        }
    }

    private fun moveUp(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        var i = startI
        while (true) {
            if (y + i >= board.height) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(board[x, y + i].elevation - board[x, y + i - 1].elevation) > 1) break

            val targetPiece = board[x, y + i].piece
            if (targetPiece == null) {
                if (!requiredAttack) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.MOVE,
                            x, y, x, y + i,
                            score = getPieceScore(board, x, y + i)
                        )
                    )
                }
            } else {
                val isEnemyPiece = canAttack && targetPiece.player != piece.player
                if (isEnemyPiece) {
                    moves.add(
                        Move(
                            piece.player, piece.type, MoveType.ATTACK,
                            x, y, x, y + i,
                            score = getPieceScore(board, x, y + i),
                            destroyed = copyPiece(targetPiece)
                        )
                    )
                    if (!canGoThroughEnemyPiece) break
                } else {
                    if (!canGoThroughOwnPiece) break
                }
            }
            i++
        }
    }
}