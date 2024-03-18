package com.kennycason.war.core.piece

import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.move.DiagonalMoveGenerator
import com.kennycason.war.core.move.HorizontalVerticalMoveGenerator
import com.kennycason.war.core.move.Move
import com.kennycason.war.core.move.MoveType

// excavator
class Excavator(
    override val player: Player,
    override var x: Int,
    override var y: Int
) : Piece() {
    private val minElevation = 0
    private val maxElevation = 4
    override val type = PieceType.EXCAVATOR

    override fun generatePossibleMoves(board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(horizontalVerticalMoveGenerator.generatePossibleMoves(this, board))
        moves.addAll(diagonalMoveGenerator.generatePossibleMoves(this, board))

        val excavateMoves = mutableListOf<Move>()
        for (move in moves) {
            if (board.state[move.toX][move.toY].piece != null) continue
            // can raise or lower
            if (board.state[move.toX][move.toY].elevation > minElevation) {
                excavateMoves.add(Move(
                    player = player,
                    pieceType = type,
                    moveType = MoveType.ATTACK,
                    fromX = x,
                    fromY = y,
                    toX = move.toX,
                    toY = move.toY,
                    score = 0.01,
                    terrainDelta = -1
                ))
            }
            if (board.state[move.toX][move.toY].elevation < maxElevation) {
                excavateMoves.add(Move(
                    player = player,
                    pieceType = type,
                    moveType = MoveType.ATTACK,
                    fromX = x,
                    fromY = y,
                    toX = move.toX,
                    toY = move.toY,
                    score = 0.01,
                    terrainDelta = +1
                ))
            }
        }
        moves.addAll(excavateMoves)

        return moves
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