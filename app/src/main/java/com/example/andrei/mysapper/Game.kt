package com.example.andrei.mysapper

data class Game(var size: Size, var initial_location : Point, var bomb_count : Int) {
    var field = Field(size)
    var opened_cells = mutableSetOf<Point>()

    init {
        putBombs()
    }

    fun revealCellAt(point: Point) {
        opened_cells.add(point)
    }

    private fun generateBombsLocation() : MutableList<Point> {
        val field = Field(size) // temporary
        field[initial_location] = null

        val bombs_list = mutableListOf<Point>()

        var i = 1
        while(i <= bomb_count) {
            val pickedCell = field.pickRandomCell() ?: continue
            bombs_list.add(pickedCell.location)
            i++
        }

        return bombs_list
    }

    private fun putBombs(){
        for (location in generateBombsLocation()) {
            field[location]?.type = Cell.Type.Bomb
        }
    }
}