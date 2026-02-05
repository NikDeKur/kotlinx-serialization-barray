package dev.nikdekur.serialization.byte

import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.nio.ByteOrder

public class JavaNIOByteStorage(
    initialCapacity: Int = 1024,
    private val order: ByteOrder = ByteOrder.BIG_ENDIAN
) : ByteStorage {

    init {
        require(initialCapacity >= 0) { "initialCapacity must be positive" }
    }

    private var buffer: ByteBuffer = ByteBuffer.allocate(initialCapacity).order(order)

    // Current read/write positions (in bytes)
    private var readPos: Int = 0
    private var writePos: Int = 0

    override val size: Long
        get() = writePos.toLong()

    // --- Helpers ---------------------------------------------------------------

    private fun ensureCapacity(additional: Int) {
        if (additional < 0) throw IllegalArgumentException("additional < 0")
        val needed = writePos + additional
        if (needed <= buffer.capacity()) return

        // Growth strategy: double capacity until it fits, or set to max if overflow
        var newCap = buffer.capacity().let { if (it == 0) 1 else it }
        while (newCap < needed) {
            newCap *= 2
            if (newCap <= 0) { // overflow protection
                newCap = Int.MAX_VALUE
                break
            }
        }

        val newBuf = ByteBuffer.allocate(newCap).order(order)
        // Copy existing data [0..writePos)
        for (i in 0 until writePos) {
            newBuf.put(i, buffer.get(i))
        }
        buffer = newBuf
    }

    private fun requireReadable(bytes: Int) {
        if (bytes < 0) throw IllegalArgumentException("bytes < 0")
        if (readPos + bytes > writePos) throw BufferUnderflowException()
    }

    // --- Export ----------------------------------------------------------------

    override fun toByteArray(): ByteArray {
        val out = ByteArray(writePos)
        for (i in 0 until writePos) out[i] = buffer.get(i)
        return out
    }

    // --- Write (raw and typed) -------------------------------------------------

    override fun writeBytes(bytes: ByteArray) {
        write(bytes) // alias — write without length prefix
    }

    override fun write(bytes: ByteArray) {
        ensureCapacity(bytes.size)
        for (i in bytes.indices) {
            buffer.put(writePos + i, bytes[i])
        }
        writePos += bytes.size
    }

    override fun writeByte(value: Byte) {
        ensureCapacity(1)
        buffer.put(writePos, value)
        writePos += 1
    }

    override fun writeShort(value: Short) {
        ensureCapacity(Short.SIZE_BYTES)
        buffer.putShort(writePos, value)
        writePos += Short.SIZE_BYTES
    }

    override fun writeInt(value: Int) {
        ensureCapacity(Int.SIZE_BYTES)
        buffer.putInt(writePos, value)
        writePos += Int.SIZE_BYTES
    }

    override fun writeLong(value: Long) {
        ensureCapacity(Long.SIZE_BYTES)
        buffer.putLong(writePos, value)
        writePos += Long.SIZE_BYTES
    }

    override fun writeFloat(value: Float) {
        ensureCapacity(Float.SIZE_BYTES)
        buffer.putFloat(writePos, value)
        writePos += Float.SIZE_BYTES
    }

    override fun writeDouble(value: Double) {
        ensureCapacity(Double.SIZE_BYTES)
        buffer.putDouble(writePos, value)
        writePos += Double.SIZE_BYTES
    }

    override fun writeBoolean(value: Boolean) {
        writeByte(if (value) 1 else 0)
    }

    override fun writeChar(value: Char) {
        ensureCapacity(Char.SIZE_BYTES)
        buffer.putChar(writePos, value)
        writePos += Char.SIZE_BYTES
    }

    override fun writeString(value: String) {
        val bytes = value.toByteArray(Charsets.UTF_8)
        // Write length (Int) + bytes
        ensureCapacity(Int.SIZE_BYTES + bytes.size)
        buffer.putInt(writePos, bytes.size)
        writePos += Int.SIZE_BYTES
        for (i in bytes.indices) {
            buffer.put(writePos + i, bytes[i])
        }
        writePos += bytes.size
    }

    override fun flush() {
        // Compact: remove already read data and shift remaining bytes to the beginning
        if (readPos == 0) return
        val remaining = writePos - readPos
        if (remaining > 0) {
            for (i in 0 until remaining) {
                buffer.put(i, buffer.get(readPos + i))
            }
        }
        readPos = 0
        writePos = remaining
        // Optionally could shrink capacity, but we keep it unchanged
    }

    // --- Read ------------------------------------------------------------------

    override fun readBytes(size: Int): ByteArray {
        requireReadable(size)
        val out = ByteArray(size)
        for (i in 0 until size) out[i] = buffer.get(readPos + i)
        readPos += size
        return out
    }

    override fun readByte(): Byte {
        requireReadable(1)
        val v = buffer.get(readPos)
        readPos += 1
        return v
    }

    override fun readShort(): Short {
        requireReadable(Short.SIZE_BYTES)
        val v = buffer.getShort(readPos)
        readPos += Short.SIZE_BYTES
        return v
    }

    override fun readInt(): Int {
        requireReadable(Int.SIZE_BYTES)
        val v = buffer.getInt(readPos)
        readPos += Int.SIZE_BYTES
        return v
    }

    override fun readLong(): Long {
        requireReadable(Long.SIZE_BYTES)
        val v = buffer.getLong(readPos)
        readPos += Long.SIZE_BYTES
        return v
    }

    override fun readFloat(): Float {
        requireReadable(Float.SIZE_BYTES)
        val v = buffer.getFloat(readPos)
        readPos += Float.SIZE_BYTES
        return v
    }

    override fun readDouble(): Double {
        requireReadable(Double.SIZE_BYTES)
        val v = buffer.getDouble(readPos)
        readPos += Double.SIZE_BYTES
        return v
    }

    override fun readBoolean(): Boolean {
        val b = readByte()
        return b.toInt() != 0
    }

    override fun readChar(): Char {
        requireReadable(Char.SIZE_BYTES)
        val v = buffer.getChar(readPos)
        readPos += Char.SIZE_BYTES
        return v
    }

    override fun readString(): String {
        val len = readInt()
        if (len < 0) throw IllegalStateException("Negative string length: $len")
        val bytes = readBytes(len)
        return String(bytes, Charsets.UTF_8)
    }
}