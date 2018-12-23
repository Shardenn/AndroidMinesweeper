package com.example.andrei.mysapper

data class Point(var x : Int = 0, var y : Int = 0) {
    operator fun plus(p: Point) = Point(this.x + p.x, this.y + p.y)
}

data class Size(var width : Int = 0, var height : Int = 0){
    fun area() : Int {
        return width * height
    }
}
