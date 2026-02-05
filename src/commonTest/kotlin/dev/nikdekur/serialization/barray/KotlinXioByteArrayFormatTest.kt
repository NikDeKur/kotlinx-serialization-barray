package dev.nikdekur.serialization.barray

import dev.nikdekur.serialization.byte.KotlinXioBufferByteStorage

class KotlinXioByteArrayFormatTest : ByteArrayFormatTest() {
    override fun buildFormat(): ByteArrayFormat {
        return ByteArrayFormat { KotlinXioBufferByteStorage() }
    }
}