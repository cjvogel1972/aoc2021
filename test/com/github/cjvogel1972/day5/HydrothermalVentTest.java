package com.github.cjvogel1972.day5;

import com.github.cjvogel1972.util.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HydrothermalVentTest {

    private HydrothermalVent vent;

    @Test
    void isHorizontal_HorizontalVent() {
        vent = new HydrothermalVent(new Coordinate(0, 9), new Coordinate(5, 9));
        assertTrue(vent.isHorizontal());
    }

    @Test
    void isHorizontal_VerticalVent() {
        vent = new HydrothermalVent(new Coordinate(7,0), new Coordinate(7, 4));
        assertFalse(vent.isHorizontal());
    }

    @Test
    void isHorizontal_DiagonalVent() {
        vent = new HydrothermalVent(new Coordinate(8,0), new Coordinate(0, 8));
        assertFalse(vent.isHorizontal());
    }

    @Test
    void isVertical_VerticalVent() {
        vent = new HydrothermalVent(new Coordinate(7,0), new Coordinate(7, 4));
        assertTrue(vent.isVertical());
    }

    @Test
    void isVertical_HorizontalVent() {
        vent = new HydrothermalVent(new Coordinate(0,9), new Coordinate(5, 9));
        assertFalse(vent.isVertical());
    }

    @Test
    void isVertical_DiagonalVent() {
        vent = new HydrothermalVent(new Coordinate(8,0), new Coordinate(0, 8));
        assertFalse(vent.isVertical());
    }
}