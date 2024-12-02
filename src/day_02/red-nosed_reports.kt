package day_02

import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.sign

fun getReports(inputFileName: String): ArrayList<List<Int>> {
    val fileContent = File(inputFileName).readText();
    val lines = fileContent.split("\n");
    val reports = ArrayList<List<Int>>(lines.count());

    for (line in lines) {
        if (line.isEmpty()) {
            continue;
        }

        val levels = line
            .split(" ")
            .filter{ it.isNotEmpty() }
            .map{it.toInt()};
        reports.add(levels);
    }

    return reports;
}

fun isSafe(report: List<Int>): Boolean {
    var previousDifference = 0;
    var unsafeLevels = 0;

    for (index in 1..report.lastIndex) {
        val previousLevel = report[index - 1];
        val difference = report[index] - previousLevel;
        if (abs(difference) !in 1..3) {
            return false;
        }

        if (index > 1 && sign(difference.toDouble()) != sign(previousDifference.toDouble())) {
            return false;
        }
        previousDifference = difference;
    }
    return true;
}

fun splice(report: List<Int>, index: Int): List<Int> {
    if (index == 0) {
        return report.subList(1, report.lastIndex + 1);
    } else if (index == report.lastIndex) {
        return report.subList(0, report.lastIndex)
    } else {
        return report.subList(0, index) + report.subList(index + 1, report.lastIndex + 1)
    }
}

fun isTolerated(report: List<Int>): Boolean {
    val reportsToCheck = ArrayList<List<Int>>();

    reportsToCheck.add(report)

    for (index in 0..report.lastIndex) {
        reportsToCheck.add(splice(report, index))
    }

    return reportsToCheck.any { isSafe(it) }
}

fun main() {
    val lists = getReports("src/day_02/input_nici");

    val safeReports = lists.filter { isSafe(it) };
    val toleratedReports = lists.filter { isTolerated(it) };
    val noSafeReports = safeReports.size;
    val noToleratedReports = toleratedReports.size;

    System.out.printf("Total safe reports: %d\n", noSafeReports);
    System.out.printf("Total tolerated reports: %d\n", noToleratedReports);
}
