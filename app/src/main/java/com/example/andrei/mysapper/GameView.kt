package com.example.andrei.mysapper

import android.app.ActionBar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_game_board.view.*

sealed class CellType {
    class Closed : CellType()
    class Flagged : CellType()
    class Bomb : CellType()
    class Empty :CellType()
    class Label(val value : Int) : CellType()
}

interface GameView {

    /*
    * Returns root view, the inflated layout file
    * */
    fun getRootView() : View
    interface GameViewListener {
        /*
    Will call the listener - controller - when a user taps on board
    Thing to change? What if we tap on "new game?"
     */
        fun onNewGameTap()
        fun onCellTapWithId(id: Int)
    }

    interface GameViewDataSource {
        var size: Size
        fun cellTypeAtCoord(point: Point): CellType
    }

    fun setListener(listener: GameViewListener)
    fun setDataSource(dataSource: GameViewDataSource)
}

class GameViewImpl(var layoutInflater: LayoutInflater,
                   var m_data_source: GameView.GameViewDataSource?) : GameView {

    var m_board_tap_listener: GameView.GameViewListener? = null
    private var m_root_view = layoutInflater.inflate(R.layout.activity_game_board, null)

    init {
        // m_RootView.any_cell ??? .setOnClickListener(this)
        m_root_view.button_new_game.setOnClickListener {this.onClickNewGame()}
        setupView()
    }

    override fun getRootView() = m_root_view

    override fun setListener(listener: GameView.GameViewListener){
        m_board_tap_listener = listener
    }

    override fun setDataSource(dataSource: GameView.GameViewDataSource) {
        m_data_source = dataSource
    }

    fun onClickNewGame(){
        Log.d("Sapper", "On click called in the GameViewImpl")
        m_board_tap_listener?.onNewGameTap()
    }

    fun onClickCellNum(id: Int) {
        Log.d("Sapper", "On click cell called in the gameViewImpl")
        m_board_tap_listener?.onCellTapWithId(id)
    }

    private fun setupView() {
        val field_size = m_data_source?.size ?: return
        val linearLayout = m_root_view.requestLayout()

        m_root_view.grid_layout_cells.columnCount = field_size.width


        for(x in 0 until field_size.width) {
            for(y in 0 until field_size.height) {
                val cell = Button(layoutInflater.context)
                var id = x*field_size.width + y

                cell.setOnClickListener {this.onClickCellNum(id)}
                cell.text =(id).toString()
                
                m_root_view.grid_layout_cells.addView(cell)
            }
        }
    }
}