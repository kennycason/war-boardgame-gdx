package com.kennycason.war.util

import com.kennycason.war.core.board.Board

fun copyBoard(from: Board, to: Board = Board(from.width, from.height)): Board {
    to.turnCount = from.turnCount
    to.currentPlayer = from.currentPlayer
    to.blackScore = from.blackScore
    to.whiteScore = from.whiteScore
    for (y in 0 until from.height) {
        for (x in 0 until from.width) {
            to.state[x][y].piece = copyPiece(from.state[x][y].piece)
            to.state[x][y].elevation = from.state[x][y].elevation
            to.state[x][y].highlight = from.state[x][y].highlight
        }
    }
    return to
}