package com.kennycason.war.core.piece

import com.badlogic.gdx.graphics.Color
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.move.Move
import com.kennycason.war.war2d.TileHighlight

interface Piece {
    val color: Color // TODO use enum
    var x: Int
    var y: Int
    val type: PieceType

    fun generatePossibleMoves(board: Board): List<Move>

    fun applyMove(board: Board, move: Move) {
        board.state[move.fromX][move.fromY].piece = null
        board.state[move.fromX][move.fromY].highlight = TileHighlight.NONE

        scoreMove(board, move)

        // place piece
        board.state[move.toX][move.toY].piece = this
        x = move.toX
        y = move.toY
    }

}

fun scoreMove(board: Board, move: Move) {
    val targetPiece = board.state[move.toX][move.toY].piece
    if (targetPiece != null) {
        move.score = PieceScore.score(targetPiece.type)
    }
}

object PieceScore {
    fun score(pieceType: PieceType) = when (pieceType) {
        PieceType.INFANTRY -> 1
        PieceType.TANK -> 2
        PieceType.ARTILLERY -> 3
        PieceType.MISSILE -> 4
        PieceType.BOMBER -> 5
        PieceType.COMMANDER -> 1000
    }
}