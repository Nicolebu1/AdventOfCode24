package day_01

import java.io.File
import kotlin.math.abs

fun getLists(inputFileName: String): Array<ArrayList<Int>> {
    val fileContent = File(inputFileName).readText();
    val lines = fileContent.split("\n");
    val leftList = ArrayList<Int>(lines.count());
    val rightList = ArrayList<Int>(lines.count());

    for (line in lines) {
        if (line.isEmpty()) {
            continue;
        }

        val entries = line.split("   ");


        leftList.add(entries[0].toInt());
        rightList.add(entries[1].toInt());
    }

    leftList.sort();
    rightList.sort();

    return arrayOf(leftList, rightList);
}

fun calculateTotalDistance(leftList: ArrayList<Int>, rightList: ArrayList<Int>): Int {
    var totalDistance = 0;

    for (i in 0..<leftList.size) {
        val diff = abs(leftList[i] - rightList[i]);

        totalDistance += diff;
    }

    return totalDistance;
}

fun countOccurrences(list: ArrayList<Int>, searchValue: Int): Int {
    var occurrences = 0;

    for(i in list){
        if(i == searchValue){
            occurrences++;
        }
        else if(i > searchValue){
            break;
        }
    }
    return occurrences;
}

fun calculateSimilarityScore(leftList: ArrayList<Int>, rightList: ArrayList<Int>): Int {
    var similarityScore = 0;

    for (locationId in leftList) {
        val multiplier = countOccurrences(rightList, locationId);

        similarityScore += locationId * multiplier;
    }

    return similarityScore;
}

fun main() {
    val lists = getLists("src/day_01/input_wolfram");
    val leftList = lists[0];
    val rightList = lists[1];

    val totalDistance = calculateTotalDistance(leftList, rightList)
    val similarityScore = calculateSimilarityScore(leftList, rightList)

    System.out.printf("Total distance: %d\n", totalDistance);
    System.out.printf("Similarity score: %d\n", similarityScore);
}
