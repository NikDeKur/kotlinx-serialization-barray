@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package dev.nikdekur.serialization.barray

import kotlinx.io.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

public val byteArraySerializer: KSerializer<ByteArray> = serializer<ByteArray>()

public fun Sink.writeStringModern(str: String) {
    writeIntLe(str.utf8Size().toInt() + 1) // field value: length followed by the string
    writeString(str)
}

public fun Source.readStringModern(): String {
    val length = readIntLe() - 1L
    return readString(length)
}