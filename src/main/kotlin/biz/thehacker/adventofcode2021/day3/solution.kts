package biz.thehacker.adventofcode2021.day3

val countBits = input
    .trimIndent()
    .lineSequence()
    .first()
    .length

var gammaRate = 0
var epsilonRate = 0
for (i in 0 until countBits) {
    val bitsCount = input
        .trimIndent()
        .lines()
        .groupingBy { line -> line[countBits - i - 1] }
        .eachCount()

    val (mostCommonBit, leastCommonBit) = when {
        bitsCount['1']!! > bitsCount['0']!! -> listOf(1, 0)
        bitsCount['1']!! < bitsCount['0']!! -> listOf(0, 1)
        else -> throw IllegalStateException("same count of bits, can't distinguish most/least common bit!?")
    }
    val value = 1 shl i

    gammaRate += mostCommonBit * value
    epsilonRate += leastCommonBit * value
}

println("gammaRate = $gammaRate, epsilonRate = $epsilonRate")
println("First answer: ${gammaRate * epsilonRate}")

fun step2Algorithm(mostOrLeast: Char, bitValueToKeepOnSameCount: Int): Int {
    val remainingNumbers = input
        .trimIndent()
        .lines()
        .toMutableList()

    for (i in (countBits-1) downTo 0) {
        if (remainingNumbers.size == 1) break

        val bitsCount = remainingNumbers
            .groupingBy { line -> line[countBits - i - 1] }
            .eachCount()

        val (mostCommonBit, leastCommonBit) = when {
            (bitsCount['1'] ?: 0) > (bitsCount['0'] ?: 0) -> listOf(1, 0)
            (bitsCount['1'] ?: 0) < (bitsCount['0'] ?: 0) -> listOf(0, 1)
            else -> listOf(null, null)
        }

        val bitValueToUse = when {
            mostCommonBit == null || leastCommonBit == null -> bitValueToKeepOnSameCount
            mostOrLeast == 'M' -> mostCommonBit
            mostOrLeast == 'L' -> leastCommonBit
            else -> throw AssertionError("never reached")
        }

        remainingNumbers
            .removeIf { it[countBits - i - 1].digitToInt(2) != bitValueToUse }
    }

    assert(remainingNumbers.size == 1)
    return remainingNumbers.first().toInt(2)
}

val oxygenGenerationRating = step2Algorithm('M', 1)
val co2ScrubberRating = step2Algorithm('L', 0)

println("-".repeat(80))
println("oxygenGenerationRating = $oxygenGenerationRating, co2ScrubberRating = $co2ScrubberRating")
println("Second answer: ${oxygenGenerationRating * co2ScrubberRating}")
