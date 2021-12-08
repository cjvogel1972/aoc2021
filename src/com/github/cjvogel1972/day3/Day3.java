package com.github.cjvogel1972.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

    public static void main(String[] args) throws IOException {
        List<DiagnosticEntry> diagnostic = readFile("input-files/day3.txt");
        Diagnostics diagnostics = new Diagnostics(diagnostic);

        System.out.println("power consumption = " + diagnostics.computePowerConsumption());

        int oxygen = diagnostics.computeO2GeneratorRating();
        int co2 = diagnostics.computeCo2ScrubberRating();
        int lifeSupport = diagnostics.computeLifeSupportRating();

        System.out.println("oxygen = " + oxygen + " co2 = " + co2 + " life support rating = " + lifeSupport);
    }

    private static List<DiagnosticEntry> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<DiagnosticEntry> result = lines.map(DiagnosticEntry::new).collect(Collectors.toList());
        lines.close();

        return result;
    }
}
