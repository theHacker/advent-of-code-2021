package biz.thehacker.adventofcode2021.day2

fun part1() {
    var horizontalPosition = 0
    var depth = 0

    input
        .trimIndent()
        .lines()
        .forEach { line ->
            val (order, x) = line
                .split(' ')
                .let { it[0] to it[1].toInt() }

            when (order) {
                "forward" -> horizontalPosition += x
                "up" -> depth -= x
                "down" -> depth += x
            }
        }

    println("horizontalPosition = $horizontalPosition, depth = $depth")
    println("First answer: ${horizontalPosition * depth}")
}

fun part2() {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0

    input
        .trimIndent()
        .lines()
        .forEach { line ->
            val (order, x) = line
                .split(' ')
                .let { it[0] to it[1].toInt() }

            when (order) {
                "down" -> aim -= x
                "up" -> aim += x
                "forward" -> {
                    horizontalPosition += x
                    depth += (-aim) * x
                }
            }
        }

    println("horizontalPosition = $horizontalPosition, depth = $depth")
    println("Second answer: ${horizontalPosition * depth}")
}

part1()
println("-".repeat(80))
part2()
