package biz.thehacker.adventofcode2021.day1

val measurements = input
    .trimIndent()
    .lines()
    .map { it.toInt() }

measurements
    .zipWithNext()
    .map { it.second - it.first }
    .count { it > 0 }
    .also { println("First answer: $it") }

val m1 = measurements
val m2 = measurements.subList(1, m1.size)
val m3 = measurements.subList(2, m1.size)

m1
    .zip(m2)
    .zip(m3) { a, b -> Triple(a.first, a.second, b) }
    .map { it.first + it.second + it.third }
    .zipWithNext()
    .map { it.second - it.first }
    .count { it > 0 }
    .also { println("Second answer: $it") }
