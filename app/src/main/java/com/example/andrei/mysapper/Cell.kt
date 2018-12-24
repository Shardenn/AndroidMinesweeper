package com.example.andrei.mysapper

data class Cell(val location : Point = Point(0, 0)) {
    enum class Type {
        Empty,
        Bomb,
        Label
    }
    var labelValue = 0
    var type: Type = Type.Empty

    fun isBomb() : Boolean {
        return type == Type.Bomb
    }
}