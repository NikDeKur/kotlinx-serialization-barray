package dev.nikdekur.serialization.barray

import dev.nikdekur.serialization.byte.JavaNIOByteStorage

class JavaNIOByteArrayFormatTest : ByteArrayFormatTest() {
    override fun buildFormat(): ByteArrayFormat {
        return ByteArrayFormat { JavaNIOByteStorage() }
    }
}