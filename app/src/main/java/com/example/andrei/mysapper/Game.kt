package com.example.andrei.mysapper

data class Game(var size: Size, var initial_location : Point, var bomb_count : Int) {
    var field = Field(size)
    var opened_cells = mutableSetOf<Point>()
    var flagged_cells = mutableSetOf<Point>()

    init {
        putBombs()
        field.generateLabels()
    }

    fun revealCellAt(point: Point) {
        opened_cells.add(point)

        val curr_cell = field[point] ?: return
        if(curr_cell.type == Cell.Type.Empty) {
            val neighbours = field.getNeighbourCells(curr_cell)
            for(neighbour in neighbours) {
                if(!opened_cells.contains(neighbour.location))
                    revealCellAt(neighbour.location)
            }
        }
    }

    fun putFlag(point: Point) {
        flagged_cells.add(point)
    }

    fun removeFlag(point: Point) {
        flagged_cells.remove(point)
    }

    fun openBombedCells() {
        for(bomb in field.getBombsLocations()) {
            opened_cells.add(bomb)
        }
    }

    private fun generateBombsLocation() : MutableList<Point> {
        val temp_field = this.field.copy()

        val initialCell = temp_field[initial_location]
        if(initialCell != null) {
            for(neighbour in temp_field.getNeighbourCells(initialCell)) {
                temp_field[neighbour.location] = null
            }
        }
        temp_field[initial_location] = null

        val bombs_list = mutableListOf<Point>()

        var i = 1
        while(i <= bomb_count) {
            val pickedCell = temp_field.pickRandomCell() ?: continue
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