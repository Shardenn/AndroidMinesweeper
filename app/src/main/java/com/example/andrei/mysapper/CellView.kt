package com.example.andrei.mysapper

import android.content.Context
import android.widget.Button

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

class CellView(context: Context, var point: Point) : Button(context) {
    private var cellType: CellType.Type_t? = CellType.Type_t.CLOSED

    fun setType(newType: CellType?) {
        cellType = newType?.Type ?: return

        val newText = when (cellType) {
            CellType.Type_t.CLOSED -> "CLOSED"
            CellType.Type_t.FLAGGED -> "FLAG"
            CellType.Type_t.BOMB -> "BOMB"
            CellType.Type_t.EMPTY -> ""
            CellType.Type_t.LABEL -> newType.labelValue.toString()
            else -> "ERR"
        }

        setText(newText)
    }
}