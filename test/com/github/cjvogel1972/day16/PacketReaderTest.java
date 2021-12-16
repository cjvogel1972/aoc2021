package com.github.cjvogel1972.day16;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacketReaderTest {

    @Test
    void readLiteralPacket() {
        var reader = new PacketReader("D2FE28");
        var packet = reader.read();
        assertEquals(2021, packet.value());
    }

    @Test
    void readOperatorLengthId1Packet() {
        var reader = new PacketReader("38006F45291200");
        var packet = reader.read();
        assertEquals(1, packet.version());
        assertEquals(6, packet.type());
    }

    @Test
    void readOperatorPacketSumType() {
        var reader = new PacketReader("C200B40A82");
        var packet = reader.read();
        assertEquals(3, packet.value());
    }

    @Test
    void readOperatorPacketProductType() {
        var reader = new PacketReader("04005AC33890");
        var packet = reader.read();
        assertEquals(54, packet.value());
    }

    @Test
    void readOperatorPacketMinimumType() {
        var reader = new PacketReader("880086C3E88112");
        var packet = reader.read();
        assertEquals(7, packet.value());
    }

    @Test
    void readOperatorPacketMaximumType() {
        var reader = new PacketReader("CE00C43D881120");
        var packet = reader.read();
        assertEquals(9, packet.value());
    }

    @Test
    void readOperatorPacketGreaterThanType() {
        var reader = new PacketReader("F600BC2D8F");
        var packet = reader.read();
        assertEquals(0, packet.value());
    }

    @Test
    void readOperatorPacketLessThanType() {
        var reader = new PacketReader("D8005AC2A8F0");
        var packet = reader.read();
        assertEquals(1, packet.value());
    }

    @Test
    void readOperatorPacketNotEqualsType() {
        var reader = new PacketReader("9C005AC2F8F0");
        var packet = reader.read();
        assertEquals(0, packet.value());
    }

    @Test
    void readOperatorPacketEqualsType() {
        var reader = new PacketReader("9C0141080250320F1802104A08");
        var packet = reader.read();
        assertEquals(1, packet.value());
    }

    @Test
    void readVersionSum() {
        var reader = new PacketReader("8A004A801A8002F478");
        reader.read();
        assertEquals(16, reader.computePacketVersionSum());

        reader = new PacketReader("620080001611562C8802118E34");
        reader.read();
        assertEquals(12, reader.computePacketVersionSum());

        reader = new PacketReader("C0015000016115A2E0802F182340");
        reader.read();
        assertEquals(23, reader.computePacketVersionSum());

        reader = new PacketReader("A0016C880162017C3686B18A3D4780");
        reader.read();
        assertEquals(31, reader.computePacketVersionSum());
    }

}