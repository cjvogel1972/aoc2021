package com.github.cjvogel1972.day16;

import java.io.IOException;
import java.util.List;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day16 {

    public static void main(String[] args) throws IOException {
        List<String> data = readFileLines("input-files/day16.txt");
        String packetStr = data.get(0);
        part1(packetStr);
        part2(packetStr);
    }

    private static void part1(String packetStr) {
        var packetReader = new PacketReader(packetStr);
        packetReader.read();
        System.out.println("Sum of versions = " + packetReader.computePacketVersionSum());
    }

    private static void part2(String packetStr) {
        var packetReader = new PacketReader(packetStr);
        Packet p = packetReader.read();
        System.out.println("Value = " + p.value());
    }
}
