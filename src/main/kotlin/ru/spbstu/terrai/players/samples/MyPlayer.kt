package ru.spbstu.terrai.players.samples

import ru.spbstu.terrai.core.*
import ru.spbstu.terrai.lab.Controller
import ru.spbstu.terrai.lab.Labyrinth
import java.util.*

class MyPlayer : AbstractPlayer() {

    private lateinit var currentLocation: Location

    private val roomMap = mutableMapOf<Location, Room>()

    override fun setStartLocationAndSize(location: Location, width: Int, height: Int) {
        super.setStartLocationAndSize(location, width, height)
        currentLocation = location
        roomMap[currentLocation] = Entrance
    }

    private var treasureFound = false

    private var exitFound = false

    private var lastMove: Move = WaitMove

    private val decisions = mutableListOf<Direction>()

    private var wormholes = 0

    private var nextDecision = Direction.NORTH

    override fun getNextMove(): Move {
        nextDecision
        decisions.add(nextDecision)

        lastMove = WalkMove(nextDecision)

        return lastMove
    }


    override fun setMoveResult(result: MoveResult) {
        val newLocation = (lastMove as? WalkMove)?.let { it.direction + currentLocation } ?: currentLocation
        val room = result.room
        roomMap[newLocation] = room
        if (result.successful) {
            when (room) {
                is Exit -> {
                    exitFound = true
                }
                is Wormhole -> {
                    decisions.clear()
                    wormholes++
                    currentLocation = Location(wormholes * 1000, wormholes * 1000)
                    roomMap[currentLocation] = room
                }
                is WithContent -> {
                    if (!treasureFound && result.condition.hasTreasure) {
                        decisions.clear()
                        roomMap.clear()
                        treasureFound = true
                    }
                }
                else -> currentLocation = newLocation
            }
        } else {
            decisions.removeAt(decisions.size - 1)
        }

        nextDecision = when (Random().nextInt(3)) {
            0 -> nextDecision.turnLeft()
            1 -> nextDecision
            2 -> nextDecision.turnRight()
            else -> Direction.values()[Random().nextInt(4)]
        }
    }

}



fun main(args: Array<String>) {
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