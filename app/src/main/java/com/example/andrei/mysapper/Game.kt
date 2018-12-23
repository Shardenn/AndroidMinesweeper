package com.example.andrei.mysapper

data class Game(var size: Size, var initial_location : Point, var bomb_count : Int) {
    var field = Field(size)

    init {
        putBombs()
    }

    fun revealCellAt(point: Point) {

    }

    private fun generateBombsLocation() : MutableList<Point> {
        var field = this.field
        field[initial_location] = null

        var bombs_list = MutableList<Point>(bomb_count) { _ -> Point(-1, -1)}

        for(i in 1..bomb_count) {
            bombs_list.add(field.pickRandomCell()!!.location) // TODO how !! works?
        }

        return bombs_list
    }

    private fun putBombs(){
        for (location in generateBombsLocation()) {
            field[location]?.type = Cell.Type.Bomb
        }
    }
}