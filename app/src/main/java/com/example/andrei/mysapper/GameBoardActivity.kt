package com.example.andrei.mysapper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

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
        m_game_model.restart()
        m_game_view.reloadGrid()
    }

    override fun onCellTapAt(point: Point) {
        val isOpened = m_game_model.game?.opened_cells?.contains(point)
        if(isOpened != null && isOpened)
            return

        val revealed_type = m_game_model.revealCellAt(point)
        m_game_view.reloadGrid()

        if(revealed_type == Cell.Type.Bomb) {
            m_game_model.endGame()
            m_game_view.reloadGrid()
            m_game_view.loseGame(point)
        }
    }

    override fun onCellLongTapAt(point: Point) {
        if(m_game_model.game == null) {
            onCellTapAt(point)
            return
        }
        val isOpened = m_game_model.game?.opened_cells?.contains(point) ?: return
        if(isOpened)
            return
        m_game_model.putFlag(point)
        m_game_view.reloadGrid()
    }

    override fun cellTypeAtCoord(point: Point): CellType {
        val cell = m_game_model.game?.field?.get(point)
        val actualType = cell?.type

        val typeToReturn = CellType()
        typeToReturn.Type = CellType.Type_t.CLOSED

        val isFlagged = m_game_model.game?.flagged_cells?.contains(cell?.location) ?:
            return typeToReturn
        if(isFlagged) {
            typeToReturn.Type = CellType.Type_t.FLAGGED
            return typeToReturn
        }

        val isOpened = m_game_model.game?.opened_cells?.contains(point)
        if(isOpened == null || !isOpened) {
            typeToReturn.Type = CellType.Type_t.CLOSED
            return typeToReturn
        }

        if(actualType == Cell.Type.Bomb) {
            typeToReturn.Type = CellType.Type_t.BOMB
        }
        else if(actualType == Cell.Type.Label) {
            typeToReturn.Type = CellType.Type_t.LABEL
            typeToReturn.labelValue = cell.labelValue
        }
        else if(actualType == Cell.Type.Empty) {
            typeToReturn.Type = CellType.Type_t.EMPTY
        }

        return typeToReturn
    }
}
