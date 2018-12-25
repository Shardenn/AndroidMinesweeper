package com.example.andrei.mysapper

import android.content.Context
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout

class CellType {
    enum class Type_t {
        CLOSED,
        FLAGGED,
        BOMB,
        EMPTY,
        LABEL
    }
    var Type = Type_t.CLOSED
    var labelValue = 0
}

class CellView(context: Context, var point: Point) : ImageButton(context) {

    private var cellType: CellType.Type_t? = CellType.Type_t.CLOSED

    fun setType(newType: CellType?) {
        cellType = newType?.Type ?: return

        when (cellType) {
            CellType.Type_t.CLOSED -> setImageResource(R.drawable.lock)
            CellType.Type_t.FLAGGED -> setImageResource(R.drawable.flag)
            CellType.Type_t.BOMB -> setImageResource(R.drawable.bomb)
            CellType.Type_t.EMPTY -> setImageResource(R.drawable.empty)
            CellType.Type_t.LABEL -> setLabel(newType.labelValue)
            else -> setImageResource(android.R.color.black)
        }

    }

    fun updateLayoutParams() {
        maxWidth = R.integer.maxCellSize
    }

    private fun setLabel(value: Int) {
        when(value) {
            1 -> setImageResource(R.drawable.one)
            2 -> setImageResource(R.drawable.two)
            3 -> setImageResource(R.drawable.three)
            4 -> setImageResource(R.drawable.four)
            5 -> setImageResource(R.drawable.five)
            6 -> setImageResource(R.drawable.six)
            7 -> setImageResource(R.drawable.seven)
            8 -> setImageResource(R.drawable.eight)
            else -> setImageResource(android.R.color.black)
        }

    }
}