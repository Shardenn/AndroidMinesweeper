package com.example.andrei.mysapper

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game_board.view.*
import kotlinx.android.synthetic.main.fragment_game_field.view.*
import pl.polidea.view.ZoomView

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
        fun onCellTapAt(point: Point)
        fun onCellLongTapAt(point: Point)
    }

    interface GameViewDataSource {
        var size: Size
        fun cellTypeAtCoord(point: Point): CellType
    }

    fun setListener(listener: GameViewListener)
    fun setDataSource(dataSource: GameViewDataSource)
    fun reloadGrid()
    fun loseGame(bomb_clicked: Point)
    fun winGame()
    fun restart()
}

class GameViewImpl(var layoutInflater: LayoutInflater,
                   var m_data_source: GameView.GameViewDataSource?) : GameView {

    var m_board_tap_listener: GameView.GameViewListener? = null
    private var m_root_view = layoutInflater.inflate(R.layout.activity_game_board, null)

    private var m_grid = mutableListOf<CellView>()
    private var m_red_button = CellView(layoutInflater.context, Point(-1,-1))

    init {
        m_root_view.button_new_game.setOnClickListener {this.onClickNewGame()}
        setupView()
        reloadGrid()
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

    fun onClickCellAt(point: Point) {
        m_board_tap_listener?.onCellTapAt(point)
    }

    fun onLongClickCellAt(point: Point) : Boolean {
        m_board_tap_listener?.onCellLongTapAt(point)
        return true
    }

    override fun restart() {
        //m_red_button.background = layoutInflater.context.getDrawable(android.R.color.transparent)
        //m_red_button = CellView(layoutInflater.context, Point(-1,-1))
        activateGrid()
        reloadGrid()
    }

    override fun winGame() {
        deactivateGrid()
        showToastMessage("You Win!")
    }

    override fun loseGame(bomb_clicked: Point) {
        deactivateGrid()
        showToastMessage("Game Over!")
        setBombRed(bomb_clicked)
    }

    private fun activateGrid() {
        for(button in m_grid) {
            button.isEnabled = true
        }
    }

    private fun deactivateGrid() {
        for(button in m_grid) {
            button.isEnabled = false
        }
    }

    private fun showToastMessage(text: CharSequence) {
        Toast.makeText(layoutInflater.context, text, Toast.LENGTH_LONG).show()
    }

    private fun setBombRed(clicked_bomb: Point) {
        val width = m_data_source?.size?.width ?: return
        val index = width * clicked_bomb.x + clicked_bomb.y
        //m_red_button = m_grid[index]
        //m_red_button.background = layoutInflater.context.getDrawable(android.R.color.holo_red_light)
    }

    override fun reloadGrid() {
        for(cell in m_grid) {
            cell.setType(m_data_source?.cellTypeAtCoord(cell.point))
        }
    }

    private fun setupView() {
        val field_size = m_data_source?.size ?: return

        val game_field_view = layoutInflater.inflate(R.layout.fragment_game_field, null, false)
        game_field_view.grid_layout_cells.columnCount = field_size.width

        for(x in 0 until field_size.width) {
            for(y in 0 until field_size.height) {
                val cell = CellView(layoutInflater.context, Point(x,y))
                m_grid.add(cell)

                val viewType = CellType()
                viewType.Type = CellType.Type_t.CLOSED
                cell.setType(viewType)

                cell.setOnClickListener {this.onClickCellAt(Point(x,y))}
                cell.setOnLongClickListener { this.onLongClickCellAt(Point(x, y)) }

                game_field_view.grid_layout_cells.addView(cell)
            }
        }

        val zoom_view = ZoomView(layoutInflater.context)
        zoom_view.addView(game_field_view)

        m_root_view.game_field_layout.addView(zoom_view)
    }
}