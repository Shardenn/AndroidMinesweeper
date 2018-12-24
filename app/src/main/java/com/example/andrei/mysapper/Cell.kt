package com.example.andrei.mysapper

data class Cell(val location : Point = Point(0, 0)) {
    enum class Type {
        Empty,
        Bomb,
        Label
    }
    val labelValue : Int = 0
    var type: Type = Type.Empty
}