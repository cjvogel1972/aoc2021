package com.github.cjvogel1972.day16;

import java.util.ArrayList;
import java.util.List;

public class PacketReader {
    private final String bits;
    private int readLoc;
    private final List<Packet> packets;

    public PacketReader(String bits) {
        this.bits = bits;
        readLoc = 0;
        packets = new ArrayList<>();
    }

    public Packet read() {
        return parsePacket();
    }

    public List<Packet> getPackets() {
        return packets;
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
