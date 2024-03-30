package com.kennycason.war.core.move

import com.badlogic.gdx.Gdx
import com.kennycason.war.core.board.Board
import com.kennycason.war.core.board.Player
import com.kennycason.war.core.piece.Piece
import com.kennycason.war.war2d.TileHighlight

class HumanMoveMaker(
    private val player: Player,
    private val cursor: Cursor
) : MoveMaker {
    private var lastClicked = 0L
    private var selectedPiece: Piece? = null

//    private fun init() {
//        // aggregate player piece for efficiency to avoid re-scans
//        // todo handle removing off pieces
//        for (y in 0 until board.height) {
//            for (x in 0 until board.width) {
//                val piece = board[x, y].piece
//                if (piece != null && piece.color == color) {
//                    pieces.add(piece)
//                }
//            }
//        }
//    }

    override fun make(board: Board): Move? {
        if (cursor.x == -1 && cursor.y == -1) return null

        val tile = board[cursor.x, cursor.y]
        if (Gdx.input.justTouched() && System.currentTimeMillis() - lastClicked > 500L) {
            lastClicked = System.currentTimeMillis()

            if (tile.highlight == TileHighlight.SELECTED) {
                tile.highlight = TileHighlight.NONE
                selectedPiece = null
                return null
            }

            if (selectedPiece == null) {
                when (tile.highlight) {
                    TileHighlight.SELECTED -> {
                        tile.highlight = TileHighlight.NONE
                        selectedPiece = tile.piece
                    }
                    else -> {
                        val piece = tile.piece ?: return null
                        if (piece.player != player) return null
                        clearSelected(board)
                        tile.highlight = TileHighlight.SELECTED
                        selectedPiece = tile.piece
                    }
                }
            }
            else {
                val possibleMoves = selectedPiece?.generatePossibleMoves(board).orEmpty()
                for (move in possibleMoves) {
                    if (move.toX == cursor.x && move.toY == cursor.y) {
                        board[move.fromX, move.fromY].piece!!.applyMove(board, move)
                        selectedPiece = null
                        board[move.fromX, move.fromY].highlight = TileHighlight.NONE
                        return move
                    }
                }
            }
        }

        return null
    }

    private fun clearSelected(board: Board) {
        // aggregate player piece for efficiency to avoid re-scans
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                if (board[x, y].highlight == TileHighlight.SELECTED) {
                    board[x, y].highlight = TileHighlight.NONE
                }
            }
        }
    }
}