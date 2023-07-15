package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.war2d.TileHighlight
import come.kennycason.war.move.Move

interface Piece {
    val color: Color
    var x: Int
    var y: Int
    val type: PieceType
    fun generatePossibleMoves(board: Board): List<Move>
    fun applyMove(board: Board, move: Move) {
        board.state[move.fromX][move.fromY].piece = null
        board.state[move.fromX][move.fromY].highlight = TileHighlight.NONE

        board.state[move.toX][move.toY].piece = this
        x = move.toX
        y = move.toY
    }
}