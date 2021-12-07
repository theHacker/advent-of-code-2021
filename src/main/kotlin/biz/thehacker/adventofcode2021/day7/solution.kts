package biz.thehacker.adventofcode2021.day7

import kotlin.math.abs

val crabs = input
    .split(",")
    .map { it.toInt() }

fun calcFuel(fuelFunction: (Int, Int) -> Int): Pair<Int, Int> =
    (crabs.minOrNull()!!..crabs.maxOrNull()!!)
        .map { x -> x to crabs.sumOf { fuelFunction(x, it) } }
        .minByOrNull { it.second }!!

fun part1() {
    val (bestPosition, fuel) = calcFuel { x, it ->
        abs(it - x)
    }

    println("Best position: $bestPosition")
    println("First answer: $fuel")
}

fun part2() {
    val (bestPosition, fuel) = calcFuel { x, it ->
        (abs(it - x) + 1) * abs(it - x) / 2 // Gauss FTW!
    }

    println("Best position: $bestPosition")
    println("Second answer: $fuel")
}

part1()
println("-".repeat(80))
part2()
