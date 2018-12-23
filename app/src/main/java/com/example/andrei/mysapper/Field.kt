package com.example.andrei.mysapper

data class Field(var size : Size) {
    private val generator = Generator(size.width)
    private var cells = MutableList(size.area()) { i -> generator.generate(i) }

    fun pickRandomCell() : Cell? {
        return cells.pickRandomElement()
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

    fun indexForPoint(point : Point) : Int {
        return point.y * width + point.x
    }

    private fun pointForIndex(index : Int) : Point {
        val x = index % width
        val y = index / width

        return Point(x,y)
    }
}