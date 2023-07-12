package come.kennycason.war.move

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.board.TileHighlight
import come.kennycason.war.piece.Piece

class HumanMoveMaker(private val color: Color, private val board: Board) {
    private val pieces = mutableListOf<Piece>()
    private var lastClicked = 0L
    private var selectedPiece: Piece? = null

    private fun init() {
        // aggregate player piece for efficiency to avoid re-scans
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val piece = board.state[x][y].piece
                if (piece != null && piece.color == color) {
                    pieces.add(piece)
                }
            }
        }
    }

    fun makeMove(board: Board): Move? {
        if (pieces.isEmpty()) init()

        if (board.cursor.x == -1 && board.cursor.y == -1) return null

        val tile = board.state[board.cursor.x][board.cursor.y]
        if (Gdx.input.justTouched() && System.currentTimeMillis() - lastClicked > 500L) {
            lastClicked = System.currentTimeMillis()

            if (selectedPiece == null) {
                when (tile.highlight) {
                    TileHighlight.SELECTED -> {
                        tile.highlight = TileHighlight.NONE
                        selectedPiece = tile.piece
                    }
                    else -> {
                        val piece = tile.piece ?: return null
                        if (piece.color != color) return null
                        clearSelected()
                        tile.highlight = TileHighlight.SELECTED
                        selectedPiece = tile.piece
                    }
                }
            }
            else {
                val possibleMoves = selectedPiece?.generatePossibleMoves(board).orEmpty()
                for (move in possibleMoves) {
                    if (move.toX == board.cursor.x && move.toY == board.cursor.y) {
                        board.state[move.fromX][move.fromY].piece = null
                        board.state[move.fromX][move.fromY].highlight = TileHighlight.NONE

                        board.state[move.toX][move.toY].piece = selectedPiece
                        selectedPiece!!.x = move.toX
                        selectedPiece!!.y = move.toY
                        selectedPiece = null
                        return move
                    }
                }
            }
        }

        return null
    }

    private fun clearSelected() {
        // aggregate player piece for efficiency to avoid re-scans
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                if (board.state[x][y].highlight == TileHighlight.SELECTED) {
                    board.state[x][y].highlight = TileHighlight.NONE
                }
            }
        }
    }
}