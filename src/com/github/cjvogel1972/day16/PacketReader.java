package com.github.cjvogel1972.day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PacketReader {

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

    private final String bits;
    private int readLoc;
    private final List<Packet> packets;

    public PacketReader(String data) {
        bits = computeBits(data);
        readLoc = 0;
        packets = new ArrayList<>();
    }

    private String computeBits(String hex) {
        return hex.chars()
                .mapToObj(c -> HEX_TO_BINARY.get((char) c))
                .collect(Collectors.joining());
    }

    public Packet read() {
        return parsePacket();
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public int computePacketVersionSum() {
        return packets.stream()
                .mapToInt(Packet::version)
                .sum();
    }

    private Packet parsePacket() {
        Packet result;

        String versionBits = bits.substring(readLoc, readLoc + 3);
        readLoc += 3;
        int version = getIntegerValue(versionBits);
        String typeBits = bits.substring(readLoc, readLoc + 3);
        readLoc += 3;
        int type = getIntegerValue(typeBits);
        if (type == 4) {
            result = parseLiteralValue(version, type);
        } else {
            result = parseOperatorValue(version, type);
        }
        packets.add(result);

        return result;
    }

    private Packet parseLiteralValue(int version, int type) {
        boolean done = false;

        var sb = new StringBuilder();
        while (!done) {
            String literalBits = bits.substring(readLoc, readLoc + 5);
            sb.append(literalBits.substring(1, 5));
            readLoc += 5;
            if (literalBits.charAt(0) == '0') {
                done = true;
            }
        }

        long value = getLongValue(sb.toString());
        return new LiteralPacket(version, type, value);
    }

    private Packet parseOperatorValue(int version, int type) {
        char lenghtTypeId = bits.charAt(readLoc);
        readLoc++;

        var result = new OperatorPacket(version, type);
        if (lenghtTypeId == '0') {
            String totalLengthOfBitsStr = bits.substring(readLoc, readLoc + 15);
            readLoc += 15;
            int totalLengthOfBits = getIntegerValue(totalLengthOfBitsStr);
            int endOfSubPackets = readLoc + totalLengthOfBits;
            while (readLoc < endOfSubPackets) {
                result.addSubPacket(parsePacket());
            }
        } else {
            String numberOfSubPackaetsStr = bits.substring(readLoc, readLoc + 11);
            readLoc += 11;
            int numberOfSubPackaets = getIntegerValue(numberOfSubPackaetsStr);
            var packetsProcessed = 0;
            while (packetsProcessed < numberOfSubPackaets) {
                result.addSubPacket(parsePacket());
                packetsProcessed++;
            }
        }

        return result;
    }

    private int getIntegerValue(String bits) {
        return Integer.parseInt(bits, 2);
    }

    private long getLongValue(String bits) {
        return Long.parseLong(bits, 2);
    }
}
