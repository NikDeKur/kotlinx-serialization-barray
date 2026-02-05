package dev.nikdekur.serialization.byte

import dev.nikdekur.serialization.barray.readStringModern
import dev.nikdekur.serialization.barray.writeStringModern
import kotlinx.io.*

public class KotlinXioBufferByteStorage(
    public val buffer: Buffer = Buffer()
) : ByteStorage {
    override val size: Long
        get() = buffer.size

    override fun toByteArray(): ByteArray {
        return buffer.readByteArray()
    }

    override fun writeBytes(bytes: ByteArray) {
        buffer.write(bytes)
    }

    override fun flush() {
        buffer.flush()
    }

    override fun readBytes(size: Int): ByteArray {
        return buffer.readByteArray(size)
    }

    override fun readByte(): Byte {
        return buffer.readByte()
    }

    override fun readShort(): Short {
        return buffer.readShort()
    }

    override fun readInt(): Int {
        return buffer.readInt()
    }

    override fun readLong(): Long {
        return buffer.readLong()
    }

    override fun readFloat(): Float {
        return buffer.readFloat()
    }

    override fun readDouble(): Double {
        return buffer.readDouble()
    }

    override fun readBoolean(): Boolean {
        return buffer.readByte() != 0.toByte()
    }

    override fun readChar(): Char {
        return buffer.readCodePointValue().toChar()
    }

    override fun readString(): String {
        return buffer.readStringModern()
    }





    override fun write(bytes: ByteArray) {
        buffer.write(bytes)
    }

    override fun writeByte(value: Byte) {
        buffer.writeByte(value)
    }

    override fun writeShort(value: Short) {
        buffer.writeShort(value)
    }

    override fun writeInt(value: Int) {
        buffer.writeInt(value)
    }

    override fun writeLong(value: Long) {
        buffer.writeLong(value)
    }

    override fun writeFloat(value: Float) {
        buffer.writeFloat(value)
    }

    override fun writeDouble(value: Double) {
        buffer.writeDouble(value)
    }

    override fun writeBoolean(value: Boolean) {
        buffer.writeByte(if (value) 1 else 0)
    }

    override fun writeChar(value: Char) {
        buffer.writeCodePointValue(value.code)
    }

    override fun writeString(value: String) {
        buffer.writeStringModern(value)
    }
}