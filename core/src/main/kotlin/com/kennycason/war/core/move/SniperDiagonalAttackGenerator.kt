package com.kennycason.war.core.move

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.util.copyPiece

/**
 * Diagonal attack generator for the Sniper piece.
 * 
 * The Sniper can attack diagonally 1-3 tiles, but cannot shoot through/over
 * obstacles (pieces or terrain) UNLESS it has an elevation advantage.
 * 
 * Elevation advantage means the sniper's tile elevation is greater than
 * all intermediate tiles and pieces between it and the target.
 */
class SniperDiagonalAttackGenerator(
    private val maxDistance: Int = 3,
    private val startI: Int = 1
) {

    fun generatePossibleMoves(piece: Piece, board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        attackLeftUp(piece, board, moves)
        attackRightUp(piece, board, moves)
        attackLeftDown(piece, board, moves)
        attackRightDown(piece, board, moves)
        return moves
    }

    private fun getPieceScore(board: Board, x: Int, y: Int) = board[x, y].piece?.type?.score ?: 0.0

    /**
     * Check if the sniper has line of sight to the target.
     * Returns true if the sniper can see the target (no blocking obstacles,
     * or sniper has elevation advantage over all intermediate tiles).
     * 
     * Blocking conditions:
     * 1. If terrain is HIGHER than the sniper, it blocks (can't see over a hill)
     * 2. If there's a PIECE in the way, sniper needs to be strictly higher to shoot over it
     * 
     * Empty terrain at the same level or lower does NOT block line of sight.
     */
    private fun hasLineOfSight(
        piece: Piece,
        board: Board,
        targetX: Int,
        targetY: Int,
        dx: Int,
        dy: Int,
        distance: Int
    ): Boolean {
        val sniperElevation = board[piece.x, piece.y].elevation
        
        // Check all intermediate tiles (not including the target tile)
        for (i in 1 until distance) {
            val checkX = piece.x + (dx * i)
            val checkY = piece.y + (dy * i)
            
            val tileElevation = board[checkX, checkY].elevation
            val blockingPiece = board[checkX, checkY].piece
            
            // If terrain is higher than the sniper, it blocks line of sight (can't see over a hill)
            if (tileElevation > sniperElevation) {
                return false
            }
            
            // If there's a piece in the way, sniper needs to be strictly higher to shoot over it
            if (blockingPiece != null && sniperElevation <= tileElevation) {
                return false
            }
        }
        
        return true
    }

    private fun attackLeftUp(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        
        for (i in startI..maxDistance) {
            val targetX = x - i
            val targetY = y + i
            
            if (targetX < 0 || targetY >= board.height) break
            
            // Check line of sight
            if (!hasLineOfSight(piece, board, targetX, targetY, -1, 1, i)) break
            
            val targetPiece = board[targetX, targetY].piece
            if (targetPiece != null && targetPiece.player != piece.player) {
                moves.add(
                    Move(
                        piece.player, piece.type, MoveType.ATTACK,
                        x, y, targetX, targetY,
                        score = getPieceScore(board, targetX, targetY),
                        destroyed = copyPiece(targetPiece)
                    )
                )
            }
        }
    }

    private fun attackRightUp(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        
        for (i in startI..maxDistance) {
            val targetX = x + i
            val targetY = y + i
            
            if (targetX >= board.width || targetY >= board.height) break
            
            if (!hasLineOfSight(piece, board, targetX, targetY, 1, 1, i)) break
            
            val targetPiece = board[targetX, targetY].piece
            if (targetPiece != null && targetPiece.player != piece.player) {
                moves.add(
                    Move(
                        piece.player, piece.type, MoveType.ATTACK,
                        x, y, targetX, targetY,
                        score = getPieceScore(board, targetX, targetY),
                        destroyed = copyPiece(targetPiece)
                    )
                )
            }
        }
    }

    private fun attackLeftDown(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        
        for (i in startI..maxDistance) {
            val targetX = x - i
            val targetY = y - i
            
            if (targetX < 0 || targetY < 0) break
            
            if (!hasLineOfSight(piece, board, targetX, targetY, -1, -1, i)) break
            
            val targetPiece = board[targetX, targetY].piece
            if (targetPiece != null && targetPiece.player != piece.player) {
                moves.add(
                    Move(
                        piece.player, piece.type, MoveType.ATTACK,
                        x, y, targetX, targetY,
                        score = getPieceScore(board, targetX, targetY),
                        destroyed = copyPiece(targetPiece)
                    )
                )
            }
        }
    }

    private fun attackRightDown(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        
        for (i in startI..maxDistance) {
            val targetX = x + i
            val targetY = y - i
            
            if (targetX >= board.width || targetY < 0) break
            
            if (!hasLineOfSight(piece, board, targetX, targetY, 1, -1, i)) break
            
            val targetPiece = board[targetX, targetY].piece
            if (targetPiece != null && targetPiece.player != piece.player) {
                moves.add(
                    Move(
                        piece.player, piece.type, MoveType.ATTACK,
                        x, y, targetX, targetY,
                        score = getPieceScore(board, targetX, targetY),
                        destroyed = copyPiece(targetPiece)
                    )
                )
            }
        }
    }
}

