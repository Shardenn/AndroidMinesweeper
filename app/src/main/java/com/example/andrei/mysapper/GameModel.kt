package com.example.andrei.mysapper

class GameModel {
    data class Settings(var size: Size, var bombs_count : Int)

    val DebugSetting = Settings(Size(6,6), 5)
    val Begginer = Settings(Size(9,9), 10)

    var game : Game? = null
    var settings : Settings = DebugSetting

    fun revealCellAt(point: Point): Cell.Type? {
        if(game == null) {
            if(settings.bombs_count >= settings.size.area())
                settings.bombs_count = settings.size.area() - 1
            game = Game(settings.size, point, settings.bombs_count)
        }

        game?.revealCellAt(point)
        return game?.field?.get(point)?.type
    }

    fun putFlag(point: Point) {
        game?.putFlag(point)
    }

    fun restart() {
        game = null
    }

    fun endGame() {
        game?.openBombedCells()
    }
}