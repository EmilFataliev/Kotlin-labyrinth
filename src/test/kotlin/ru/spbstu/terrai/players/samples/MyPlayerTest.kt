package ru.spbstu.terrai.players.samples

import org.junit.Assert
import org.junit.Test
import ru.spbstu.terrai.lab.Controller
import ru.spbstu.terrai.lab.Labyrinth

class MyPlayerTest : AbstractPlayerTest() {

    override fun createPlayer() = MyPlayer()

    private val fileName = "labyrinths/lab2.txt"

    init {
        doTest()
    }

    fun doTest() {
        val lab = Labyrinth.createFromFile(fileName)
        val player = createPlayer()
        val controller = Controller(lab, player)
        val actualResult = controller.makeMoves(500)
        println(actualResult)

    }


}
fun main(args: Array<String>) {
    val player = MyPlayerTest()

}