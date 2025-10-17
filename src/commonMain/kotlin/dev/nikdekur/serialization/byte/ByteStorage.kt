package dev.nikdekur.serialization.byte

public interface ByteStorage {

    public val size: Long


    public fun toByteArray(): ByteArray

    public fun writeBytes(bytes: ByteArray)

    public fun flush()


    public fun readBytes(size: Int): ByteArray
    public fun readByte(): Byte
    public fun readShort(): Short
    public fun readInt(): Int
    public fun readLong(): Long
    public fun readFloat(): Float
    public fun readDouble(): Double
    public fun readBoolean(): Boolean
    public fun readChar(): Char
    public fun readString(): String



    public fun write(bytes: ByteArray)
    public fun writeByte(value: Byte)
    public fun writeShort(value: Short)
    public fun writeInt(value: Int)
    public fun writeLong(value: Long)
    public fun writeFloat(value: Float)
    public fun writeDouble(value: Double)
    public fun writeBoolean(value: Boolean)
    public fun writeChar(value: Char)
    public fun writeString(value: String)
}