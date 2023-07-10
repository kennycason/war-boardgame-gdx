package come.kennycason.war.piece

import com.badlogic.gdx.graphics.Color
import come.kennycason.war.GameState
import come.kennycason.war.board.Board
import come.kennycason.war.board.Move

interface Piece {
    val color: Color
    val x: Int
    val y: Int
    val type: PieceType
    fun draw(gameState: GameState, x: Float, y: Float)
    fun generatePossibleMoves(board: Board): List<Move>
}