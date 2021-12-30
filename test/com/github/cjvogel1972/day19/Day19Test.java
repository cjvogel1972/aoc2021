package com.github.cjvogel1972.day19;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day19Test {

    @Test
    void testScanner1Scanner4() {
        Scanner scanner1 = new Scanner(1);
        var scanner1BeaconCoordinates = """
                686,422,578
                605,423,415
                515,917,-361
                -336,658,858
                95,138,22
                -476,619,847
                -340,-569,-846
                567,-361,727
                -460,603,-452
                669,-402,600
                729,430,532
                -500,-761,534
                -322,571,750
                -466,-666,-811
                -429,-592,574
                -355,545,-477
                703,-491,-529
                -328,-685,520
                413,935,-424
                -391,539,-444
                586,-435,557
                -364,-763,-893
                807,-499,-711
                755,-354,-619
                553,889,-390
                """;
        Arrays.stream(scanner1BeaconCoordinates.split("\n"))
                .map(Day19::parseLine)
                .forEach(scanner1::addBeacon);

        scanner1.setCoordinate(new Coordinate3D(68, -1246, -43), Day19.rotations.get(2));
        assertTrue(scanner1.getBeacons()
                           .contains(new Coordinate3D(-618, -824, -621)));

        Scanner scanner4 = new Scanner(4);
        var scanner4BeaconCoordinates = """
                727,592,562
                -293,-554,779
                441,611,-461
                -714,465,-776
                -743,427,-804
                -660,-479,-426
                832,-632,460
                927,-485,-438
                408,393,-506
                466,436,-512
                110,16,151
                -258,-428,682
                -393,719,612
                -211,-452,876
                808,-476,-593
                -575,615,604
                -485,667,467
                -680,325,-822
                -627,-443,-432
                872,-547,-609
                833,512,582
                807,604,487
                839,-516,451
                891,-625,532
                -652,-548,-490
                30,-46,-14
                """;
        Arrays.stream(scanner4BeaconCoordinates.split("\n"))
                .map(Day19::parseLine)
                .forEach(scanner4::addBeacon);

        Day19.checkScanners(scanner1, scanner4);

        assertTrue(scanner4.isCoordinateSet());
        assertEquals(new Coordinate3D(-20, -1133, 1061), scanner4.getCoordinate());
    }
}