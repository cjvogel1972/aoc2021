package com.github.cjvogel1972.day3;

public record DiagnosticEntry(String binaryValue) {

    public int getNumberOfBits() {
        return binaryValue.length();
    }

    public int bit(int bitIndex) {
        return binaryValue.charAt(bitIndex) - '0';
    }

    public int getIntegerValue() {
        return Integer.parseInt(binaryValue, 2);
    }

    @Override
    public String toString() {
        return "DiagnosticEntry{" +
                "binaryValue='" + binaryValue + '\'' +
                '}';
    }
}
