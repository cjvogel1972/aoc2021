package com.github.cjvogel1972.day16;

import java.util.ArrayList;
import java.util.List;

public class OperatorPacket implements Packet {
    private final int version;
    private final int type;
    private final List<Packet> subPackets;

    public OperatorPacket(int version, int type) {
        this.version = version;
        this.type = type;
        subPackets = new ArrayList<>();
    }

    public void addSubPacket(Packet p) {
        subPackets.add(p);
    }

    @Override
    public long value() {
        long result = 0;

        if (type == 0) {
            result = subPackets.stream().mapToLong(Packet::value).sum();
        }
        else if (type == 1) {
            result = subPackets.stream().mapToLong(Packet::value).reduce(1, (left, right) -> left * right);
        }
        else if (type == 2) {
            result = subPackets.stream().mapToLong(Packet::value).min().getAsLong();
        }
        else if (type == 3) {
            result = subPackets.stream().mapToLong(Packet::value).max().getAsLong();
        }
        else if (type == 5) {
            if (subPackets.get(0).value() > subPackets.get(1).value()) {
                result = 1;
            }
        }
        else if (type == 6) {
            if (subPackets.get(0).value() < subPackets.get(1).value()) {
                result = 1;
            }
        }
        else if (type == 7) {
            if (subPackets.get(0).value() == subPackets.get(1).value()) {
                result = 1;
            }
        }

        return result;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public String toString() {
        return "OperatorPacket{" +
                "version=" + version +
                ", type=" + type +
                ", subPackets=" + subPackets +
                '}';
    }
}
