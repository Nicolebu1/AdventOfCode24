package day_03

import java.io.File
import kotlin.math.max

val MultiplicationPattern = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")
val EnableInstruction = "do()"
val DisableInstruction = "don't()"

typealias Multiplication = Pair<Int, Int>

fun getProgramMemory(filePath: String): String {
    return File(filePath).readText()
}

fun getEnabledProgramMemory(programMemory: String): String {
    val programBuilder = StringBuilder()
    var offset = 0

    while (offset < programMemory.length) {

        val enabledProgramBlockEndIndex = programMemory.indexOf(DisableInstruction, offset)

        if(enabledProgramBlockEndIndex == -1) {
            programBuilder.append(programMemory.substring(offset))
            break;
        } else {
            programBuilder.append(programMemory.substring(offset, enabledProgramBlockEndIndex))
            val nextEnabledProgramBlockOffset = programMemory.indexOf(EnableInstruction, enabledProgramBlockEndIndex)

            if (nextEnabledProgramBlockOffset == -1) {
                break;
            }

            offset = nextEnabledProgramBlockOffset
        }
    }

    return programBuilder.toString()
}

fun getMultiplicationStatements(programMemory: String): ArrayList<Multiplication> {
    val multiplications = ArrayList<Multiplication>();
    val matches = MultiplicationPattern.findAll(programMemory)

    for (match in matches) {
        val x = match.groups[1]?.value?.toInt()
        val y = match.groups[2]?.value?.toInt()

        if(x != null && y != null) {
            multiplications.add(Pair(x, y))
        }
    }

    return multiplications
}

fun multiply(mul: Multiplication): Int {
    return mul.first * mul.second
}

fun main() {
    val filePath = "src/day_03/input_wolfram"
    val programMemory = getProgramMemory(filePath)
    val multiplicationStatements = getMultiplicationStatements(programMemory)
    val sumOfMultiplications = multiplicationStatements
        .map { multiply(it) }
        .reduce { sum, currentProduct -> sum + currentProduct }

    val enabledProgramMemory = getEnabledProgramMemory(programMemory)
    val enabledMultiplicationStatements = getMultiplicationStatements(enabledProgramMemory)
    val sumOfEnabledMultiplications = enabledMultiplicationStatements
        .map { multiply(it) }
        .reduce { sum, currentProduct -> sum + currentProduct }

    System.out.printf("Total sum of multiplications: %d\n", sumOfMultiplications)
    System.out.printf("Total sum of enabled multiplications: %d\n", sumOfEnabledMultiplications)
}
