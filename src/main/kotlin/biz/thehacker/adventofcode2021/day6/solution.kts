package biz.thehacker.adventofcode2021.day6

data class Swarm(val fishCountByCounter: MutableMap<Int, Long>) {

    constructor(string: String) : this(
        string
            .split(',')
            .map { it.toInt() }
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, v) -> v.toLong() }
            .toMutableMap()
    )

    fun fishCount() = fishCountByCounter.values.sum()

    fun executeDay() {
        val newFishCountByCounter = mutableMapOf<Int, Long>()
        newFishCountByCounter[0] = (fishCountByCounter[1] ?: 0)
        newFishCountByCounter[1] = (fishCountByCounter[2] ?: 0)
        newFishCountByCounter[2] = (fishCountByCounter[3] ?: 0)
        newFishCountByCounter[3] = (fishCountByCounter[4] ?: 0)
        newFishCountByCounter[4] = (fishCountByCounter[5] ?: 0)
        newFishCountByCounter[5] = (fishCountByCounter[6] ?: 0)
        newFishCountByCounter[6] = (fishCountByCounter[7] ?: 0) + (fishCountByCounter[0] ?: 0)
        newFishCountByCounter[7] = (fishCountByCounter[8] ?: 0)
        newFishCountByCounter[8] = (fishCountByCounter[0] ?: 0)

        fishCountByCounter.clear()
        fishCountByCounter.putAll(newFishCountByCounter)
    }
}

fun simulate(outputPrefix: String, daysToSimulate: Int) {
    val swarm = Swarm(input)

    for (days in 1..daysToSimulate) {
        swarm.executeDay()

        println(
            "After ${String.format("%2d", days)} ${if (days == 1) "day " else "days"}: ${swarm.fishCount()}"
        )
    }
    println("$outputPrefix answer: After $daysToSimulate days there are ${swarm.fishCount()} fish.")
}

simulate("First", 80)
println("-".repeat(80))
simulate("Second", 256)
