package com.example.andrei.mysapper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GameBoardActivity : AppCompatActivity(),
    GameView.GameViewListener,
    GameView.GameViewDataSource
{
    private lateinit var m_game_view : GameView
    private val m_game_model = GameModel()
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

    override fun onCellTapWithId(id: Int) {
        Log.d("Sapper", "Cell num " + id.toString() + " is clicked in main")
    }

    override fun cellTypeAtCoord(point: Point): CellType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
