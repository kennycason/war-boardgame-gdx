package come.kennycason.war.move

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.board.Board
import come.kennycason.war.piece.Piece

class ComputerMonteCarloMoveMaker(private val color: Color, private val board: Board) {
    private val pieces = mutableListOf<Piece>()

    init {
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
        return null
    }

}