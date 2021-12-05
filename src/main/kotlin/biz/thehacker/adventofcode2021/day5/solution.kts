package biz.thehacker.adventofcode2021.day5

import kotlin.math.abs
import kotlin.math.sign

data class Point(val x: Int, val y: Int)

data class Line(val start: Point, val end: Point) {

    val horizontal: Boolean
        get() = start.y == end.y

    val vertical: Boolean
        get() = start.x == end.x

    fun coveredPoints(): List<Point> {
        // Kotlin bug... KT-50061, has to be defined here -.-
        fun IntRange.ordered() = if (start <= endInclusive) this else endInclusive..start

        return if (horizontal) {
            (start.x..end.x).ordered().map { x -> Point(x, start.y) }
        } else if (vertical) {
            (start.y..end.y).ordered().map { y -> Point(start.x, y) }
        } else {
            val dx = end.x - start.x
            val dy = end.y - start.y

            // guarantee: exactly 45 degrees
            assert(abs(dx) == abs(dy))

            val result = mutableListOf<Point>()
            for (i in 0..abs(dx)) {
                val x = start.x + dx.sign * i
                val y = start.y + dy.sign * i
                result += Point(x, y)
            }
            assert(result.last() == end)

            result
        }
    }

    companion object {
        private val stringRegex = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")

        fun fromString(string: String): Line = stringRegex
            .matchEntire(string)
            ?.let { matchResult ->
                val ints = (1..4)
                    .map { matchResult.groupValues[it].toInt() }

                Line(
                    Point(ints[0], ints[1]),
                    Point(ints[2], ints[3])
                )
            }
            ?: throw AssertionError("Regex did not match!")
    }
}

val allLines = input
    .trimIndent()
    .lines()
    .map { Line.fromString(it) }

fun doIt(outputPrefix: String, filter: (Line) -> Boolean) {
    val sea = mutableMapOf<Point, Int>()

    allLines
        .filter(filter)
        .flatMap { line -> line.coveredPoints() }
        .forEach { point ->
            sea.compute(point) { _, oldValue -> (oldValue ?: 0) + 1 }
        }

    val answer = sea
        .filterValues { it >= 2 }
        .count()

    println("$outputPrefix answer: $answer")
}

doIt("First") { it.horizontal || it.vertical }
doIt("Second") { true }
