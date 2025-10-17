@file:OptIn(ExperimentalSerializationApi::class)

package dev.nikdekur.serialization.barray

import dev.nikdekur.ndkore.serial.encodeVarInt
import dev.nikdekur.serialization.byte.ByteStorage
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.SerializersModule

public class DataOutputEncoder(
    override val serializersModule: SerializersModule,
    public val output: ByteStorage
) : AbstractEncoder() {

    override fun encodeBoolean(value: Boolean): Unit = output.writeBoolean(value)
    override fun encodeByte(value: Byte): Unit = output.writeByte(value)
    override fun encodeShort(value: Short): Unit = output.writeShort(value)
    override fun encodeInt(value: Int): Unit = output.writeInt(value)
    override fun encodeLong(value: Long): Unit = output.writeLong(value)
    override fun encodeFloat(value: Float): Unit = output.writeFloat(value)
    override fun encodeDouble(value: Double): Unit = output.writeDouble(value)
    override fun encodeChar(value: Char): Unit = output.writeChar(value)
    override fun encodeString(value: String): Unit = output.writeString(value)
    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int): Unit = output.writeInt(index)

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        encodeInt(collectionSize)
        return this
    }

    override fun encodeNull(): Unit = encodeBoolean(false)
    override fun encodeNotNullMark(): Unit = encodeBoolean(true)

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (serializer.descriptor == byteArraySerializer.descriptor)
            encodeByteArray(value as ByteArray)
        else
            super.encodeSerializableValue(serializer, value)
    }

    private fun encodeByteArray(bytes: ByteArray) {
        encodeVarInt(bytes.size)
        output.write(bytes)
    }
}