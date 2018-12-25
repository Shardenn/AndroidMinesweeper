package com.example.andrei.mysapper

data class Field(var size : Size) {
    private val generator = Generator(size.width)
    private var cells = MutableList(size.area()) { i -> generator.generate(i) }

    fun pickRandomCell() : Cell? {
        return cells.pickRandomElement()
    }

    fun generateLabels() {
        val bombs = mutableListOf<Cell>()
        for(cell in cells) {
            if(cell != null && cell.isBomb()) {
                bombs.add(cell)
            }
        }

        for(bomb in bombs) {
            for(bomb_neighbour in getNeighbourCells(bomb)) {
                //val index = generator.indexForPoint(bomb_neighbour.location)
                //var currCell = cells[index]!!
                if(bomb_neighbour.type == Cell.Type.Bomb)
                    continue

                if(bomb_neighbour.type != Cell.Type.Label) {
                    bomb_neighbour.type = Cell.Type.Label
                    bomb_neighbour.labelValue = 0
                }
                bomb_neighbour.labelValue++
            }
        }
    }

    fun getNeighbourCells(cell: Cell): MutableList<Cell> {
        val neighbours = mutableListOf<Cell>()

        for(i in -1..1) {
            for(j in -1..1) {
                val supposed_point = Point(cell.location.x + i, cell.location.y + j)
                if(!(0 until size.width).contains(supposed_point.x) ||
                    !(0 until size.width).contains(supposed_point.y))
                    continue

                val supposed_neighbour = cells[generator.indexForPoint(supposed_point)]

                if( supposed_neighbour == null ||
                    !(0 until size.width).contains(supposed_neighbour.location.x) ||
                    !(0 until size.height).contains(supposed_neighbour.location.y) ||
                    supposed_neighbour.location == cell.location)
                    continue

                neighbours.add(supposed_neighbour) // TODO do we have the neighbours by reference?
            }
        }

        return  neighbours
    }

    // operator[]
    operator fun get(point: Point) : Cell? {
        if(!isAvailable(point))
            return null

        return cells[generator.indexForPoint(point)]
    }

    // operator=
    operator fun set(point : Point, newValue : Cell?) {
        if(!isAvailable(point))
            return

        val index = generator.indexForPoint(point)
        cells[index] = newValue
    }

    private fun isAvailable(point : Point) : Boolean {
        val index = generator.indexForPoint(point)

        return (0 until size.area()).contains(index)
    }
}

private data class Generator(val width : Int) {

    fun generate(index : Int) : Cell? {
        val point = pointForIndex(index)
        return Cell(point)
    }

    fun indexForPoint(point : Point) : Int { // TODO make sure formula is ok
        return point.x * width + point.y
    }

    private fun pointForIndex(index : Int) : Point { // TODO make sure formula is ok
        val y = index % width
        val x = index / width

        return Point(x,y)
    }
}