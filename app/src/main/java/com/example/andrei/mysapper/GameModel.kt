package com.example.andrei.mysapper

class GameModel {
    data class Settings(var size: Size, var bombs_count : Int) {}

    val DebugSetting = Settings(Size(3,3), 3)
    val Begginer = Settings(Size(9,9), 10)

    private var game : Game? = null
    var settings : Settings = Begginer

    fun revealCellAt(point: Point) {
        if(game == null) {
            game = Game(settings.size, point, settings.bombs_count)
        }

        //game.revealCellAt(point) // TODO
    }

    fun restart() {
        game = null
    }
}