package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.DiagonalMoveGenerator
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveType

class Artillery(
    override val player: Player,
    override var x: Int,
    override var y: Int
) : Piece() {
    var isReloading = false
    var lastAttackTurn = 0

    override val type = PieceType.ARTILLERY

    override fun generatePossibleMoves(board: Board): List<Move> {
        handleReloading(board)

        val moves = mutableListOf<Move>()
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(this, board))
        if (!isReloading) {
            moves.addAll(attackMoveGenerator.generatePossibleMoves(this, board))
        }
        return moves
    }

    override fun applyMove(board: Board, move: Move) {
        when (move.moveType) {
            MoveType.MOVE -> {
                board.state[move.fromX][move.fromY].piece = null
                board.state[move.toX][move.toY].piece = this
                x = move.toX
                y = move.toY
            }
            MoveType.ATTACK -> {
                board.state[move.toX][move.toY].piece = null
                isReloading = true
                lastAttackTurn = board.turnCount
                addScore(board, move)
            }
        }
        changeTurn(board)
    }

    override fun undoMove(board: Board, move: Move) {
        super.undoMove(board, move)
        if (move.moveType == MoveType.ATTACK) {
            isReloading = false
        }
    }

    fun handleReloading(board: Board) {
        if (isReloading && board.turnCount - lastAttackTurn > 3) {
            isReloading = false
        }
    }

}

private val horizontalVerticalMoveGenerator = HorizontalVerticalMoveGenerator(
    maxDistance = 1,
    canAttack = false
)
private val diagonalMoveGenerator = DiagonalMoveGenerator(
    maxDistance = 1,
    canAttack = false
)
private val attackMoveGenerator = HorizontalVerticalMoveGenerator(
    startI = 2,
    maxDistance = 3,
    canAttack = true,
    requiredAttack = true,
    canGoThroughOwnPiece = true,
    canGoThroughEnemyPiece = true,
    ignoreHeight = true
)