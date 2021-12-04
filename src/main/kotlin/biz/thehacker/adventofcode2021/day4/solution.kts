package biz.thehacker.adventofcode2021.day4

import java.util.*

val lines = input
    .trimIndent()
    .lines()

fun getNumbersAndBoards(): Pair<List<Int>, List<BingoBoard>> {
    val numbers = lines.first().split(',').map { it.toInt() }
    val boards = lines
        .drop(2)
        .windowed(5, 6) {
            BingoBoard(it)
        }

    return Pair(numbers, boards)
}

class BingoBoard(lines: List<String>) {

    private val numbers: List<Int>
    private val positionsMarked = BitSet(25)

    init {
        assert(lines.size == 5)

        numbers = lines
            .flatMap { line -> line
                .trim()
                .split(Regex("\\s+"))
                .map { it.toInt() }
            }

        assert(numbers.size == 25)
    }

    fun mark(number: Int) {
        numbers
            .indexOf(number)
            .takeIf { it != -1 }
            ?.also { positionsMarked.set(it) }
    }

    private val winningBitSets = listOf(
        // horizontal lines
        BitSet.valueOf(longArrayOf(0b0000000000000000000011111)),
        BitSet.valueOf(longArrayOf(0b0000000000000001111100000)),
        BitSet.valueOf(longArrayOf(0b0000000000111110000000000)),
        BitSet.valueOf(longArrayOf(0b0000011111000000000000000)),
        BitSet.valueOf(longArrayOf(0b1111100000000000000000000)),

        // vertical lines
        BitSet.valueOf(longArrayOf(0b0000100001000010000100001)),
        BitSet.valueOf(longArrayOf(0b0001000010000100001000010)),
        BitSet.valueOf(longArrayOf(0b0010000100001000010000100)),
        BitSet.valueOf(longArrayOf(0b0100001000010000100001000)),
        BitSet.valueOf(longArrayOf(0b1000010000100001000010000)),
    )

    fun hasWon(): Boolean = winningBitSets
        .any {
            val andResult = it.clone() as BitSet // JDK class BitSet does not support creation of new instance, so we have to clone
            andResult.and(positionsMarked)

            andResult == it
        }

    fun sumOfUnmarkedNumbers() = (0 until 25)
        .filter { !positionsMarked[it] }
        .sumOf { numbers[it] }
}

fun part1() {
    val (numbers, boards) = getNumbersAndBoards()

    print("Drawing...")
    numbers@ for (numberDrawn in numbers) {
        print(" $numberDrawn")
        for (board in boards) {
            board.mark(numberDrawn)

            if (board.hasWon()) {
                println("\nBoard has won!")
                println("First answer: ${board.sumOfUnmarkedNumbers() * numberDrawn}")
                break@numbers
            }
        }
    }
}

fun part2() {
    val (numbers, boards) = getNumbersAndBoards()

    val boardsRemaining = boards.toMutableSet()

    print("Drawing...")
    numbers@ for (numberDrawn in numbers) {
        print(" $numberDrawn")
        for (board in boards) {
            board.mark(numberDrawn)

            if (board.hasWon()) {
                boardsRemaining -= board

                if (boardsRemaining.isEmpty()) {
                    println("\nLast remaining board has won!")
                    println("Second answer: ${board.sumOfUnmarkedNumbers() * numberDrawn}")
                    break@numbers
                }
            }
        }
    }
}

part1()
println("-".repeat(80))
part2()
