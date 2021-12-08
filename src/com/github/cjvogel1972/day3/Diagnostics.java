package com.github.cjvogel1972.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public class Diagnostics {
    private List<DiagnosticEntry> entries;

    public Diagnostics(List<DiagnosticEntry> entries) {
        this.entries = entries;
    }

    public int computePowerConsumption() {
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (int i = 0; i < getNumberOfBits(); i++) {
            int maxCommonBit = maxCommonBit(i);
            if (maxCommonBit == 0) {
                gamma.append("0");
                epsilon.append("1");
            } else {
                gamma.append("1");
                epsilon.append("0");
            }
        }

        int gammaValue = new DiagnosticEntry(gamma.toString()).getIntegerValue();
        int epsilonValue = new DiagnosticEntry(epsilon.toString()).getIntegerValue();

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
        for (int i = 0; i < getNumberOfBits() && temp.size() > 1; i++) {
            int maxCommonBit = maxCommonBit(temp, i);

            int keepBit = determineKeepBit.applyAsInt(maxCommonBit);
            int index = i;
            temp = temp.stream()
                    .filter(number -> number.bit(index) == keepBit)
                    .collect(Collectors.toList());
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
        long zeroCount = temp.stream()
                .filter(code -> code.bit(bitIndex) == 0)
                .count();

        int result = -1;
        if (zeroCount > (temp.size() / 2)) {
            result = 0;
        } else if (zeroCount < (temp.size() / 2)) {
            result = 1;
        }

        return result;
    }
}
