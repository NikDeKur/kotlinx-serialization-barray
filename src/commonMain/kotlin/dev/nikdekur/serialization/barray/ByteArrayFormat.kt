package dev.nikdekur.serialization.barray

import dev.nikdekur.serialization.byte.BufferByteStorage
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.SerializersModule

/**
 * # ByteArrayFormat
 *
 * An implementation of [BinaryFormat] that encodes and decodes data to and from byte arrays.
 *
 * This specific implementation is designed to send as little data as possible.
 * It includes only values to ByteArray without any additional metadata, even the type and field names are not included.
 * To deserialize encoded data, you need to know the exact structure of the data it was encoded from.
 * You have to keep the same: Order, Type, and Size of the fields.
 *
 * This format is useful when you need to send data over the network or store it in a file,
 * and you want to save as much space as possible.
 * Firstly was designed to be used with WebSockets, but can be used in any other case.
 *
 * ### Example usage:
 * ```kotlin
 * val format = ByteArrayFormat()
 * val data = format.encodeToByteArray(MyData.serializer(), MyData(42, "Hello"))
 * val decoded = format.decodeFromByteArray(MyData.serializer(), data)
 * ```
 *
 * @param serializersModule The module that contains serializers for the data you want to encode and decode.
 */
public open class ByteArrayFormat(
    override val serializersModule: SerializersModule = SerializersModule {  }
) : BinaryFormat {

    override fun <T> encodeToByteArray(
        serializer: SerializationStrategy<T>,
        value: T
    ): ByteArray {
        val buffer = BufferByteStorage()
        val encoder = DataOutputEncoder(serializersModule, buffer)
        serializer.serialize(encoder, value)
        return buffer.toByteArray()
    }

    override fun <T> decodeFromByteArray(
        deserializer: DeserializationStrategy<T>,
        bytes: ByteArray
    ): T {
        val buffer = BufferByteStorage().also {
            it.writeBytes(bytes)
        }
        val decoder = DataInputDecoder(serializersModule, buffer)
        return deserializer.deserialize(decoder)
    }


    public companion object Default : ByteArrayFormat()
}