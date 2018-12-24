package com.example.andrei.mysapper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GameBoardActivity : AppCompatActivity(),
    GameView.GameViewListener,
    GameView.GameViewDataSource
{
    private lateinit var m_game_view : GameView
    private var m_game_model = GameModel()
    override var size = m_game_model.settings.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m_game_view = GameViewImpl(layoutInflater, this)
        m_game_view.setListener(this)
        setContentView(m_game_view.getRootView())
    }

    override fun onNewGameTap(){
        Log.d("Sapper", "On new game tap (clicked) called in the main activity")
    }

    override fun onCellTapAt(point: Point) {
        val pointStr = (point.x).toString() + " " + (point.y).toString()
        Log.d("SapperDebug", "Cell at " + pointStr + " is clicked in main")

        m_game_model.revealCellAt(point)
        m_game_view.reloadGrid()
    }

    override fun cellTypeAtCoord(point: Point): CellType.Type {
        val cell = m_game_model.game?.field?.get(point)
        val actualType = cell?.type

        var isOpened = m_game_model.game?.opened_cells?.contains(point)
        if(isOpened == null || !isOpened)
            return CellType.Type.CLOSED

        return when(actualType) {
            Cell.Type.Bomb -> CellType.Type.BOMB
            Cell.Type.Label -> CellType.Type.LABEL
            Cell.Type.Empty -> CellType.Type.EMPTY
            null -> CellType.Type.CLOSED
        }
    }
}
