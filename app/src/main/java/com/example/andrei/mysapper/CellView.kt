package com.example.andrei.mysapper

import android.content.Context
import android.widget.Button

class CellType {
    enum class Type {
        CLOSED,
        FLAGGED,
        BOMB,
        EMPTY,
        LABEL
    }
    var labelValue = -1
}

class CellView(context: Context, var point: Point) : Button(context) {
    private var cellType: CellType.Type? = CellType.Type.CLOSED

    fun setType(newType: CellType.Type?) {
        cellType = newType

        var newText = when (cellType) {
            CellType.Type.CLOSED -> "CLOSED"
            CellType.Type.FLAGGED -> "FLAG"
            CellType.Type.BOMB -> "BOMB"
            CellType.Type.EMPTY -> ""
            CellType.Type.LABEL -> "LABEL"
            else -> "ERR"
        }

        setText(newText)
    }
}