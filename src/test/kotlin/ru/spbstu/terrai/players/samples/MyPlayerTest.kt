package ru.spbstu.terrai.players.samples

import org.junit.Assert
import org.junit.Test
import ru.spbstu.terrai.lab.Controller
import ru.spbstu.terrai.lab.Labyrinth

class MyPlayerTest : AbstractPlayerTest() {

    override fun createPlayer() = MyPlayer()

    fun doTest() {
        var won = 0
        var lose = 0
        for (j in 1..7) {
            println("Labyrinth №$j:")
            for (i in 1..1000) {
                val lab = Labyrinth.createFromFile("labyrinths/lab$j.txt")
                val player = MyPlayer()
                val controller = Controller(lab, player)
                val result = controller.makeMoves(1000)
                if (result.exitReached) {
                    ++won
                } else {
                    ++lose
                }
            }
            println("Won - $won times")
            println("Lose - $lose times\n")
            won = 0
            lose = 0
        }
    }


}


fun main(args: Array<String>) {

    val myPlayer: MyPlayerTest = MyPlayerTest()
    myPlayer.doTest()
}