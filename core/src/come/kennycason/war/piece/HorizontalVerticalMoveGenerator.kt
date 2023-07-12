package come.kennycason.war.piece

import come.kennycason.war.board.Board
import come.kennycason.war.move.Move
import come.kennycason.war.move.MoveType
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

    private fun moveLeft(piece: Piece, board: Board, moves: MutableList<Move>) {
        val x = piece.x
        val y = piece.y
        val state = board.state
        var i = startI
        while (true) {
            if (x - i < 0) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x - i][y].elevation - state[x - i + 1][y].elevation) > 1) break

            if (state[x - i][y].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x, y, x - i, y, MoveType.MOVE))
                }
            }
            else {
                val isEnemyPiece = canAttack && state[x - i][y].piece!!.color != piece.color
                if (isEnemyPiece) {
                    moves.add(Move(x, y, x - i, y, MoveType.ATTACK))
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
        val state = board.state
        var i = startI
        while (true) {
            if (x + i >= state.size) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x + i][y].elevation - state[x + i - 1][y].elevation) > 1) break

            if (state[x + i][y].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x, y, x + i, y, MoveType.MOVE))
                }
            }
            else {
                val isEnemyPiece = canAttack && state[x + i][y].piece!!.color != piece.color
                if (isEnemyPiece) {
                    moves.add(Move(x, y, x + i, y, MoveType.ATTACK))
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
        val state = board.state
        var i = startI
        while (true) {
            if (y - i < 0) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x][y - i].elevation - state[x][y - i + 1].elevation) > 1) break

            if (state[x][y - i].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x, y, x, y - i, MoveType.MOVE))
                }
            }
            else {
                val isEnemyPiece = canAttack && state[x][y - i].piece!!.color != piece.color
                if (isEnemyPiece) {
                    moves.add(Move(x, y, x, y - i, MoveType.ATTACK))
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
        val state = board.state
        var i = startI
        while (true) {
            if (y + i >= state.size) break
            if (i > maxDistance) break
            if (!ignoreHeight && abs(state[x][y + i].elevation - state[x][y + i - 1].elevation) > 1) break

            if (state[x][y + i].piece == null) {
                if (!requiredAttack) {
                    moves.add(Move(x, y, x, y + i, MoveType.MOVE))
                }
            }
            else {
                val isEnemyPiece = canAttack && state[x][y + i].piece!!.color != piece.color
                if (isEnemyPiece) {
                    moves.add(Move(x, y, x, y + i, MoveType.ATTACK))
                    if (!canGoThroughEnemyPiece) break
                } else {
                    if (!canGoThroughOwnPiece) break
                }
            }
            i++
        }
    }
}