package com.github.cjvogel1972.day3;

import static com.github.cjvogel1972.util.Utilities.binaryToInt;

public record DiagnosticEntry(String binaryValue) {

    public int getNumberOfBits() {
        return binaryValue.length();
    }

    public int bit(int bitIndex) {
        return binaryValue.charAt(bitIndex) - '0';
    }

    public int getIntegerValue() {
        return binaryToInt(binaryValue);
    }

    @Override
    public String toString() {
        return "DiagnosticEntry{" +
                "binaryValue='" + binaryValue + '\'' +
                '}';
    }
}
