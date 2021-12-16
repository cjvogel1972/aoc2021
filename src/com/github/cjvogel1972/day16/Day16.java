package com.github.cjvogel1972.day16;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day16 {

    private static final Map<Character, String> HEX_TO_BINARY;

    static {
        HEX_TO_BINARY = new HashMap<>();
        HEX_TO_BINARY.put('0', "0000");
        HEX_TO_BINARY.put('1', "0001");
        HEX_TO_BINARY.put('2', "0010");
        HEX_TO_BINARY.put('3', "0011");
        HEX_TO_BINARY.put('4', "0100");
        HEX_TO_BINARY.put('5', "0101");
        HEX_TO_BINARY.put('6', "0110");
        HEX_TO_BINARY.put('7', "0111");
        HEX_TO_BINARY.put('8', "1000");
        HEX_TO_BINARY.put('9', "1001");
        HEX_TO_BINARY.put('A', "1010");
        HEX_TO_BINARY.put('B', "1011");
        HEX_TO_BINARY.put('C', "1100");
        HEX_TO_BINARY.put('D', "1101");
        HEX_TO_BINARY.put('E', "1110");
        HEX_TO_BINARY.put('F', "1111");
    }

    public static void main(String[] args) throws IOException {
        List<String> data = readFileLines("input-files/day16.txt");
        String packetStr = data.get(0);
//        String bits = computeBits("9C0141080250320F1802104A08");
        part1(packetStr);
        part2(packetStr);
    }

    private static void part1(String packetStr) {
        var packetReader = new PacketReader(packetStr);
        packetReader.read();
        var versionSum2 = packetReader.getPackets().stream()
                .mapToInt(Packet::version)
                .sum();
        System.out.println("Sum of versions = " + versionSum2);
    }

    private static void part2(String packetStr) {
        var packetReader = new PacketReader(packetStr);
        Packet p = packetReader.read();
        System.out.println("Value = " + p.value());
    }
}
