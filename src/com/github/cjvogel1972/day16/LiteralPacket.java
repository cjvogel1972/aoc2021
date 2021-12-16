package com.github.cjvogel1972.day16;

public record LiteralPacket(int version, int type, long value) implements Packet {
}
