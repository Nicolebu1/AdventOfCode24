package day_04

import java.io.File
import kotlin.math.max

val SearchWord = "XMAS"

val CharacterIndexes = arrayOf(
    arrayOf(Pair(1, 0), Pair(2, 0), Pair(3, 0)),
    arrayOf(Pair(-1, 0), Pair(-2, 0), Pair(-3, 0)),
    arrayOf(Pair(1, 1), Pair(2, 2), Pair(3, 3)),
    arrayOf(Pair(0, 1), Pair(0, 2), Pair(0, 3)),
    arrayOf(Pair(-1, 1), Pair(-2, 2), Pair(-3, 3)),
    arrayOf(Pair(-1, -1), Pair(-2, -2), Pair(-3, -3)),
    arrayOf(Pair(0, -1), Pair(0, -2), Pair(0, -3)),
    arrayOf(Pair(1, -1), Pair(2, -2), Pair(3, -3)),
)

val LessCharacterIndexes = arrayOf(
    arrayOf(Pair(Pair(-1, -1), 'M'), Pair(Pair(1, -1), 'M'), Pair(Pair(-1, 1), 'S'), Pair(Pair(1, 1), 'S')),
    arrayOf(Pair(Pair(-1, -1), 'S'), Pair(Pair(1, -1), 'S'), Pair(Pair(-1, 1), 'M'), Pair(Pair(1, 1), 'M')),
    arrayOf(Pair(Pair(-1, -1), 'S'), Pair(Pair(1, -1), 'M'), Pair(Pair(-1, 1), 'S'), Pair(Pair(1, 1), 'M')),
    arrayOf(Pair(Pair(-1, -1), 'M'), Pair(Pair(1, -1), 'S'), Pair(Pair(-1, 1), 'M'), Pair(Pair(1, 1), 'S')),
)

fun getWordSearch(filePath: String): String {
    return File(filePath).readText()
}

fun findXmas(rawWordSearch: String): Int {
    var numOccurrences = 0
    val lines = rawWordSearch.replace("\r", "").split('\n').filter { it.isNotEmpty() }
    val wordSearch = rawWordSearch.replace("\r", "").replace("\n", "")
    val columns = lines[0].length

    for (y in lines.indices) {
        for (x in 0..<columns) {
            val currentCharacter = wordSearch.elementAt(y * columns + x)

            if (currentCharacter != 'X') {
                continue
            }

            for (characterOffsets in CharacterIndexes) {
                var matches = true

                for (charOffsetIndex in 0..2) {
                    val charOffset = characterOffsets[charOffsetIndex]
                    val index = (y + charOffset.second) * columns + charOffset.first + x
                    val searchCharacter = SearchWord.elementAt(charOffsetIndex + 1)

                    if (x + charOffset.first >= columns || x + charOffset.first < 0) {
                        matches = false
                        break
                    }

                    if (y + charOffset.second >= lines.size) {
                        matches = false
                        break
                    }

                    if (index < 0 || index > wordSearch.lastIndex) {
                        matches = false
                        break;
                    }

                    if (wordSearch.elementAt(index) != searchCharacter) {
                        matches = false
                        break;
                    }
                }

                if (matches) {
                    numOccurrences++
                }
            }
        }
    }

    return numOccurrences
}

fun findXMas(rawWordSearch: String): Int {
    var numOccurrences = 0
    val lines = rawWordSearch.replace("\r", "").split('\n').filter { it.isNotEmpty() }
    val wordSearch = rawWordSearch.replace("\r", "").replace("\n", "")
    val columns = lines[0].length

    for (y in lines.indices) {
        for (x in 0..<columns) {
            val currentCharacter = wordSearch.elementAt(y * columns + x)

            if (currentCharacter != 'A') {
                continue
            }

            for (characterOffsets in LessCharacterIndexes) {
                var matches = true

                for (characterOffsetPair in characterOffsets) {
                    val charOffset = characterOffsetPair.first;
                    val index = (y + charOffset.second) * columns + charOffset.first + x
                    val searchCharacter = characterOffsetPair.second

                    if (x + charOffset.first >= columns || x + charOffset.first < 0) {
                        matches = false
                        break
                    }

                    if (y + charOffset.second >= lines.size) {
                        matches = false
                        break
                    }

                    if (index < 0 || index > wordSearch.lastIndex) {
                        matches = false
                        break;
                    }

                    if (wordSearch.elementAt(index) != searchCharacter) {
                        matches = false
                        break;
                    }
                }

                if (matches) {
                    numOccurrences++
                }
            }
        }
    }

    return numOccurrences
}

fun main() {
    val filePath = "src/day_04/input_nici"
    val wordSearch = getWordSearch(filePath)
    val numXmasOccurrences = findXmas(wordSearch)
    val numXMasOccurrences = findXMas(wordSearch)

    System.out.printf("XMAS appears %d times\n", numXmasOccurrences)
    System.out.printf("X-MAS appears %d times\n", numXMasOccurrences)
}
