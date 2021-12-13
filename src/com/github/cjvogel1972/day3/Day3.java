package com.github.cjvogel1972.day3;

import java.io.IOException;
import java.util.List;

import static com.github.cjvogel1972.util.Utilities.parseFile;

public class Day3 {

    public static void main(String[] args) throws IOException {
        var diagnostic = readFile("input-files/day3.txt");
        var diagnostics = new Diagnostics(diagnostic);

        part1(diagnostics);
        part2(diagnostics);
    }

    private static List<DiagnosticEntry> readFile(String fileName) throws IOException {
        return parseFile(fileName, DiagnosticEntry::new);
    }

    private static void part1(Diagnostics diagnostics) {
        System.out.println("power consumption = " + diagnostics.computePowerConsumption());
    }

    private static void part2(Diagnostics diagnostics) {
        var oxygen = diagnostics.computeO2GeneratorRating();
        var co2 = diagnostics.computeCo2ScrubberRating();
        var lifeSupport = diagnostics.computeLifeSupportRating();

        System.out.println("oxygen = " + oxygen + " co2 = " + co2 + " life support rating = " + lifeSupport);
    }
}
