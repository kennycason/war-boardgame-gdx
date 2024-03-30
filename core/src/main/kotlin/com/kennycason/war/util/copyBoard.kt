package com.kennycason.war.util

import com.kennycason.war.core.board.Board

fun copyBoard(from: Board, to: Board = Board(from.width, from.height)): Board {
    to.turnCount = from.turnCount
    to.currentPlayer = from.currentPlayer
    to.blackScore = from.blackScore
    to.whiteScore = from.whiteScore
    for (y in 0 until from.height) {
        for (x in 0 until from.width) {
            to[x, y].piece = copyPiece(from[x, y].piece)
            to[x, y].elevation = from[x, y].elevation
            to[x, y].highlight = from[x, y].highlight
        }
    }
    return to
}