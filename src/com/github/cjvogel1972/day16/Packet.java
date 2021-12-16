package com.github.cjvogel1972.day16;

public interface Packet {
    int version();
    int type();
    long value();
}
