package com.github.cjvogel1972.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3 {

    public static void main(String[] args) throws IOException {
        var diagnostic = readFile("input-files/day3.txt");
        var diagnostics = new Diagnostics(diagnostic);

        part1(diagnostics);
        part2(diagnostics);
    }

    private static List<DiagnosticEntry> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var result = lines.map(DiagnosticEntry::new)
                .toList();
        lines.close();

        return result;
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
