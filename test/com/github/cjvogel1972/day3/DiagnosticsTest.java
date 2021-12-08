package com.github.cjvogel1972.day3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DiagnosticsTest {

    private Diagnostics diagnostics;

    @BeforeEach
    void setup() {
        List<DiagnosticEntry> diagnostic = new ArrayList<>();
        diagnostic.add(new DiagnosticEntry("00100"));
        diagnostic.add(new DiagnosticEntry("11110"));
        diagnostic.add(new DiagnosticEntry("10110"));
        diagnostic.add(new DiagnosticEntry("10111"));
        diagnostic.add(new DiagnosticEntry("10101"));
        diagnostic.add(new DiagnosticEntry("01111"));
        diagnostic.add(new DiagnosticEntry("00111"));
        diagnostic.add(new DiagnosticEntry("11100"));
        diagnostic.add(new DiagnosticEntry("10000"));
        diagnostic.add(new DiagnosticEntry("11001"));
        diagnostic.add(new DiagnosticEntry("00010"));
        diagnostic.add(new DiagnosticEntry("01010"));

        diagnostics = new Diagnostics(diagnostic);
    }

    @Test
    void computePowerConsumption() {
        assertEquals(198, diagnostics.computePowerConsumption());
    }

    @Test
    void maxCommonBit() {
        assertEquals(1, diagnostics.maxCommonBit(0));
        assertEquals(0, diagnostics.maxCommonBit(1));
        assertEquals(1, diagnostics.maxCommonBit(2));
        assertEquals(1, diagnostics.maxCommonBit(3));
        assertEquals(0, diagnostics.maxCommonBit(4));
    }

    @Test
    void computeO2GeneratorRating() {
        assertEquals(23, diagnostics.computeO2GeneratorRating());
    }

    @Test
    void computeCo2ScrubberRating() {
        assertEquals(10, diagnostics.computeCo2ScrubberRating());
    }

    @Test
    void computeLifeSupportRating() {
        assertEquals(230, diagnostics.computeLifeSupportRating());
    }
}