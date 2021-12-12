package com.github.cjvogel1972.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class Diagnostics {
    private final List<DiagnosticEntry> entries;

    public Diagnostics(List<DiagnosticEntry> entries) {
        this.entries = entries;
    }

    public int computePowerConsumption() {
        var gamma = new StringBuilder();
        var epsilon = new StringBuilder();
        for (var i = 0; i < getNumberOfBits(); i++) {
            var maxCommonBit = maxCommonBit(i);
            if (maxCommonBit == 0) {
                gamma.append("0");
                epsilon.append("1");
            } else {
                gamma.append("1");
                epsilon.append("0");
            }
        }

        var gammaValue = new DiagnosticEntry(gamma.toString()).getIntegerValue();
        var epsilonValue = new DiagnosticEntry(epsilon.toString()).getIntegerValue();

        return gammaValue * epsilonValue;
    }

    public int computeLifeSupportRating() {
        return computeO2GeneratorRating() * computeCo2ScrubberRating();
    }

    public int computeO2GeneratorRating() {
        return computeRating(this::determineOxygenKeepBit);
    }

    public int computeCo2ScrubberRating() {
        return computeRating(this::determineCo2KeepBit);
    }

    private int computeRating(IntUnaryOperator determineKeepBit) {
        List<DiagnosticEntry> temp = new ArrayList<>(entries);
        for (var i = 0; i < getNumberOfBits() && temp.size() > 1; i++) {
            var maxCommonBit = maxCommonBit(temp, i);

            var keepBit = determineKeepBit.applyAsInt(maxCommonBit);
            var index = i;
            temp = temp.stream()
                    .filter(number -> number.bit(index) == keepBit)
                    .toList();
        }

        return temp.get(0).getIntegerValue();
    }

    private int determineOxygenKeepBit(int maxCommonBit) {
        return maxCommonBit == 0 ? 0 : 1;
    }

    private int determineCo2KeepBit(int maxCommonBit) {
        return maxCommonBit == 0 ? 1 : 0;
    }

    public int getNumberOfBits() {
        return entries.get(0).getNumberOfBits();
    }

    public int maxCommonBit(int bitIndex) {
        return maxCommonBit(entries, bitIndex);
    }

    private int maxCommonBit(List<DiagnosticEntry> temp, int bitIndex) {
        var zeroCount = temp.stream()
                .filter(code -> code.bit(bitIndex) == 0)
                .count();

        var result = -1;
        if (zeroCount > (temp.size() / 2)) {
            result = 0;
        } else if (zeroCount < (temp.size() / 2)) {
            result = 1;
        }

        return result;
    }
}
