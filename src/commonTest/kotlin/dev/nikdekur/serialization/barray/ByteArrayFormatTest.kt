package dev.nikdekur.serialization.barray

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.*
import kotlinx.serialization.modules.SerializersModule
import kotlin.test.*

class ByteArrayFormatTest {

    private lateinit var format: ByteArrayFormat

    @BeforeTest
    fun setup() {
        format = ByteArrayFormat()
    }

    // ==================== Primitive Types Tests ====================

    @Test
    fun `test encode and decode Boolean true`() {
        val original = true
        val encoded = format.encodeToByteArray(Boolean.serializer(), original)
        val decoded = format.decodeFromByteArray(Boolean.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Boolean false`() {
        val original = false
        val encoded = format.encodeToByteArray(Boolean.serializer(), original)
        val decoded = format.decodeFromByteArray(Boolean.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Byte positive`() {
        val original: Byte = 127
        val encoded = format.encodeToByteArray(Byte.serializer(), original)
        val decoded = format.decodeFromByteArray(Byte.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Byte negative`() {
        val original: Byte = -128
        val encoded = format.encodeToByteArray(Byte.serializer(), original)
        val decoded = format.decodeFromByteArray(Byte.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Byte zero`() {
        val original: Byte = 0
        val encoded = format.encodeToByteArray(Byte.serializer(), original)
        val decoded = format.decodeFromByteArray(Byte.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Short positive`() {
        val original: Short = 32767
        val encoded = format.encodeToByteArray(Short.serializer(), original)
        val decoded = format.decodeFromByteArray(Short.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Short negative`() {
        val original: Short = -32768
        val encoded = format.encodeToByteArray(Short.serializer(), original)
        val decoded = format.decodeFromByteArray(Short.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Int positive`() {
        val original = 2147483647
        val encoded = format.encodeToByteArray(Int.serializer(), original)
        val decoded = format.decodeFromByteArray(Int.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Int negative`() {
        val original = -2147483648
        val encoded = format.encodeToByteArray(Int.serializer(), original)
        val decoded = format.decodeFromByteArray(Int.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Int zero`() {
        val original = 0
        val encoded = format.encodeToByteArray(Int.serializer(), original)
        val decoded = format.decodeFromByteArray(Int.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Long positive`() {
        val original = 9223372036854775807L
        val encoded = format.encodeToByteArray(Long.serializer(), original)
        val decoded = format.decodeFromByteArray(Long.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Long negative`() {
        val original = -9223372036854775807L
        val encoded = format.encodeToByteArray(Long.serializer(), original)
        val decoded = format.decodeFromByteArray(Long.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Float positive`() {
        val original = 3.14159f
        val encoded = format.encodeToByteArray(Float.serializer(), original)
        val decoded = format.decodeFromByteArray(Float.serializer(), encoded)
        assertEquals(original, decoded, 0.00001f)
    }

    @Test
    fun `test encode and decode Float negative`() {
        val original = -2.71828f
        val encoded = format.encodeToByteArray(Float.serializer(), original)
        val decoded = format.decodeFromByteArray(Float.serializer(), encoded)
        assertEquals(original, decoded, 0.00001f)
    }

    @Test
    fun `test encode and decode Float zero`() {
        val original = 0.0f
        val encoded = format.encodeToByteArray(Float.serializer(), original)
        val decoded = format.decodeFromByteArray(Float.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Float special values`() {
        listOf(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY).forEach { value ->
            val encoded = format.encodeToByteArray(Float.serializer(), value)
            val decoded = format.decodeFromByteArray(Float.serializer(), encoded)
            if (value.isNaN()) {
                assertTrue(decoded.isNaN())
            } else {
                assertEquals(value, decoded)
            }
        }
    }

    @Test
    fun `test encode and decode Double positive`() {
        val original = 3.141592653589793
        val encoded = format.encodeToByteArray(Double.serializer(), original)
        val decoded = format.decodeFromByteArray(Double.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Double negative`() {
        val original = -2.718281828459045
        val encoded = format.encodeToByteArray(Double.serializer(), original)
        val decoded = format.decodeFromByteArray(Double.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Double special values`() {
        listOf(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY).forEach { value ->
            val encoded = format.encodeToByteArray(Double.serializer(), value)
            val decoded = format.decodeFromByteArray(Double.serializer(), encoded)
            if (value.isNaN()) {
                assertTrue(decoded.isNaN())
            } else {
                assertEquals(value, decoded)
            }
        }
    }

    @Test
    fun `test encode and decode Char ascii`() {
        val original = 'A'
        val encoded = format.encodeToByteArray(Char.serializer(), original)
        val decoded = format.decodeFromByteArray(Char.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Char unicode`() {
        val original = '€'
        val encoded = format.encodeToByteArray(Char.serializer(), original)
        val decoded = format.decodeFromByteArray(Char.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode String empty`() {
        val original = ""
        val encoded = format.encodeToByteArray(String.serializer(), original)
        val decoded = format.decodeFromByteArray(String.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode String simple`() {
        val original = "Hello, World!"
        val encoded = format.encodeToByteArray(String.serializer(), original)
        val decoded = format.decodeFromByteArray(String.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode String unicode`() {
        val original = "Привет, мир! 你好世界! 🌍"
        val encoded = format.encodeToByteArray(String.serializer(), original)
        val decoded = format.decodeFromByteArray(String.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode String with special characters`() {
        val original = "Line1\nLine2\tTabbed\rCarriageReturn\u0000Null"
        val encoded = format.encodeToByteArray(String.serializer(), original)
        val decoded = format.decodeFromByteArray(String.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode String long`() {
        val original = "a".repeat(10000)
        val encoded = format.encodeToByteArray(String.serializer(), original)
        val decoded = format.decodeFromByteArray(String.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Enum Tests ====================

    @Serializable
    enum class Color { RED, GREEN, BLUE }

    @Test
    fun `test encode and decode Enum first value`() {
        val original = Color.RED
        val encoded = format.encodeToByteArray(Color.serializer(), original)
        val decoded = format.decodeFromByteArray(Color.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Enum middle value`() {
        val original = Color.GREEN
        val encoded = format.encodeToByteArray(Color.serializer(), original)
        val decoded = format.decodeFromByteArray(Color.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Enum last value`() {
        val original = Color.BLUE
        val encoded = format.encodeToByteArray(Color.serializer(), original)
        val decoded = format.decodeFromByteArray(Color.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Collection Tests ====================

    @Test
    fun `test encode and decode List empty`() {
        val original = emptyList<Int>()
        val encoded = format.encodeToByteArray(ListSerializer(Int.serializer()), original)
        val decoded = format.decodeFromByteArray(ListSerializer(Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode List single element`() {
        val original = listOf(42)
        val encoded = format.encodeToByteArray(ListSerializer(Int.serializer()), original)
        val decoded = format.decodeFromByteArray(ListSerializer(Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode List multiple elements`() {
        val original = listOf(1, 2, 3, 4, 5)
        val encoded = format.encodeToByteArray(ListSerializer(Int.serializer()), original)
        val decoded = format.decodeFromByteArray(ListSerializer(Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode List large`() {
        val original = (1..1000).toList()
        val encoded = format.encodeToByteArray(ListSerializer(Int.serializer()), original)
        val decoded = format.decodeFromByteArray(ListSerializer(Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode List of Strings`() {
        val original = listOf("apple", "banana", "cherry")
        val encoded = format.encodeToByteArray(ListSerializer(String.serializer()), original)
        val decoded = format.decodeFromByteArray(ListSerializer(String.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Set empty`() {
        val original = emptySet<Int>()
        val encoded = format.encodeToByteArray(SetSerializer(Int.serializer()), original)
        val decoded = format.decodeFromByteArray(SetSerializer(Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Set multiple elements`() {
        val original = setOf(1, 2, 3, 4, 5)
        val encoded = format.encodeToByteArray(SetSerializer(Int.serializer()), original)
        val decoded = format.decodeFromByteArray(SetSerializer(Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Map empty`() {
        val original = emptyMap<String, Int>()
        val encoded = format.encodeToByteArray(MapSerializer(String.serializer(), Int.serializer()), original)
        val decoded = format.decodeFromByteArray(MapSerializer(String.serializer(), Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Map single entry`() {
        val original = mapOf("one" to 1)
        val encoded = format.encodeToByteArray(MapSerializer(String.serializer(), Int.serializer()), original)
        val decoded = format.decodeFromByteArray(MapSerializer(String.serializer(), Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode Map multiple entries`() {
        val original = mapOf("one" to 1, "two" to 2, "three" to 3)
        val encoded = format.encodeToByteArray(MapSerializer(String.serializer(), Int.serializer()), original)
        val decoded = format.decodeFromByteArray(MapSerializer(String.serializer(), Int.serializer()), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Nullable Types Tests ====================

    @Test
    fun `test encode and decode nullable Int null`() {
        val original: Int? = null
        val encoded = format.encodeToByteArray(Int.serializer().nullable, original)
        val decoded = format.decodeFromByteArray(Int.serializer().nullable, encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode nullable Int not null`() {
        val original = 42
        val encoded = format.encodeToByteArray(Int.serializer().nullable, original)
        val decoded = format.decodeFromByteArray(Int.serializer().nullable, encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode nullable String null`() {
        val original: String? = null
        val encoded = format.encodeToByteArray(String.serializer().nullable, original)
        val decoded = format.decodeFromByteArray(String.serializer().nullable, encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode nullable String not null`() {
        val original = "Hello"
        val encoded = format.encodeToByteArray(String.serializer().nullable, original)
        val decoded = format.decodeFromByteArray(String.serializer().nullable, encoded)
        assertEquals(original, decoded)
    }

    // ==================== ByteArray Tests ====================

    @Test
    fun `test encode and decode ByteArray empty`() {
        val original = byteArrayOf()
        val encoded = format.encodeToByteArray(ByteArraySerializer(), original)
        val decoded = format.decodeFromByteArray(ByteArraySerializer(), encoded)
        assertContentEquals(original, decoded)
    }

    @Test
    fun `test encode and decode ByteArray small`() {
        val original = byteArrayOf(1, 2, 3, 4, 5)
        val encoded = format.encodeToByteArray(ByteArraySerializer(), original)
        val decoded = format.decodeFromByteArray(ByteArraySerializer(), encoded)
        assertContentEquals(original, decoded)
    }

    @Test
    fun `test encode and decode ByteArray compact size boundary 254`() {
        val original = ByteArray(254) { it.toByte() }
        val encoded = format.encodeToByteArray(ByteArraySerializer(), original)
        val decoded = format.decodeFromByteArray(ByteArraySerializer(), encoded)
        assertContentEquals(original, decoded)
    }

    @Test
    fun `test encode and decode ByteArray compact size boundary 255`() {
        val original = ByteArray(255) { it.toByte() }
        val encoded = format.encodeToByteArray(ByteArraySerializer(), original)
        val decoded = format.decodeFromByteArray(ByteArraySerializer(), encoded)
        assertContentEquals(original, decoded)
    }

    @Test
    fun `test encode and decode ByteArray large`() {
        val original = ByteArray(10000) { (it % 256).toByte() }
        val encoded = format.encodeToByteArray(ByteArraySerializer(), original)
        val decoded = format.decodeFromByteArray(ByteArraySerializer(), encoded)
        assertContentEquals(original, decoded)
    }

    // ==================== Data Class Tests ====================

    @Serializable
    data class SimpleData(val id: Int, val name: String)

    @Test
    fun `test encode and decode simple data class`() {
        val original = SimpleData(42, "Test")
        val encoded = format.encodeToByteArray(SimpleData.serializer(), original)
        val decoded = format.decodeFromByteArray(SimpleData.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class ComplexData(
        val boolean: Boolean,
        val byte: Byte,
        val short: Short,
        val int: Int,
        val long: Long,
        val float: Float,
        val double: Double,
        val char: Char,
        val string: String
    )

    @Test
    fun `test encode and decode complex data class with all primitive types`() {
        val original = ComplexData(
            boolean = true,
            byte = 127,
            short = 32767,
            int = 2147483647,
            long = 9223372036854775807L,
            float = 3.14f,
            double = 2.718281828,
            char = 'X',
            string = "Hello"
        )
        val encoded = format.encodeToByteArray(ComplexData.serializer(), original)
        val decoded = format.decodeFromByteArray(ComplexData.serializer(), encoded)

        // Check each field individually for better error reporting
        assertEquals(original.boolean, decoded.boolean)
        assertEquals(original.byte, decoded.byte)
        assertEquals(original.short, decoded.short)
        assertEquals(original.int, decoded.int)
        assertEquals(original.long, decoded.long)
        assertEquals(original.char, decoded.char)
        assertEquals(original.string, decoded.string)

        // Use a tolerance for floating point comparisons
        assertEquals(original.float, decoded.float, absoluteTolerance = 1e-6f)
        assertEquals(original.double, decoded.double, absoluteTolerance = 1e-9)
    }

    @Serializable
    data class NestedData(val outer: String, val inner: SimpleData)

    @Test
    fun `test encode and decode nested data class`() {
        val original = NestedData("Outer", SimpleData(10, "Inner"))
        val encoded = format.encodeToByteArray(NestedData.serializer(), original)
        val decoded = format.decodeFromByteArray(NestedData.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class DataWithList(val items: List<Int>)

    @Test
    fun `test encode and decode data class with collection`() {
        val original = DataWithList(listOf(1, 2, 3, 4, 5))
        val encoded = format.encodeToByteArray(DataWithList.serializer(), original)
        val decoded = format.decodeFromByteArray(DataWithList.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class DataWithNullable(val value: Int?)

    @Test
    fun `test encode and decode data class with nullable field null`() {
        val original = DataWithNullable(null)
        val encoded = format.encodeToByteArray(DataWithNullable.serializer(), original)
        val decoded = format.decodeFromByteArray(DataWithNullable.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode data class with nullable field not null`() {
        val original = DataWithNullable(42)
        val encoded = format.encodeToByteArray(DataWithNullable.serializer(), original)
        val decoded = format.decodeFromByteArray(DataWithNullable.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class DataWithEnum(val color: Color, val name: String)

    @Test
    fun `test encode and decode data class with enum`() {
        val original = DataWithEnum(Color.GREEN, "My Color")
        val encoded = format.encodeToByteArray(DataWithEnum.serializer(), original)
        val decoded = format.decodeFromByteArray(DataWithEnum.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class DataWithByteArray(val data: ByteArray, val label: String) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as DataWithByteArray
            if (!data.contentEquals(other.data)) return false
            if (label != other.label) return false
            return true
        }

        override fun hashCode(): Int {
            var result = data.contentHashCode()
            result = 31 * result + label.hashCode()
            return result
        }
    }

    @Test
    fun `test encode and decode data class with ByteArray`() {
        val original = DataWithByteArray(byteArrayOf(1, 2, 3), "Label")
        val encoded = format.encodeToByteArray(DataWithByteArray.serializer(), original)
        val decoded = format.decodeFromByteArray(DataWithByteArray.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Deep Nesting Tests ====================

    @Serializable
    data class Level1(val data: String, val next: Level2)

    @Serializable
    data class Level2(val value: Int, val next: Level3)

    @Serializable
    data class Level3(val flag: Boolean)

    @Test
    fun `test encode and decode deeply nested structure`() {
        val original = Level1("L1", Level2(42, Level3(true)))
        val encoded = format.encodeToByteArray(Level1.serializer(), original)
        val decoded = format.decodeFromByteArray(Level1.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== List of Complex Objects Tests ====================

    @Test
    fun `test encode and decode list of data classes`() {
        val original = listOf(
            SimpleData(1, "First"),
            SimpleData(2, "Second"),
            SimpleData(3, "Third")
        )
        val encoded = format.encodeToByteArray(ListSerializer(SimpleData.serializer()), original)
        val decoded = format.decodeFromByteArray(ListSerializer(SimpleData.serializer()), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class Matrix(val rows: List<List<Int>>)

    @Test
    fun `test encode and decode nested collections`() {
        val original = Matrix(
            listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9)
            )
        )
        val encoded = format.encodeToByteArray(Matrix.serializer(), original)
        val decoded = format.decodeFromByteArray(Matrix.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Custom SerializersModule Tests ====================

    @Serializable
    data class CustomData(val value: String)

    @Test
    fun `test custom SerializersModule`() {
        val customModule = SerializersModule {
            // Empty module for testing custom module support
        }
        val customFormat = ByteArrayFormat(customModule)
        val original = CustomData("test")
        val encoded = customFormat.encodeToByteArray(CustomData.serializer(), original)
        val decoded = customFormat.decodeFromByteArray(CustomData.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Default Instance Tests ====================

    @Test
    fun `test Default companion object instance`() {
        val original = SimpleData(99, "Default")
        val encoded = ByteArrayFormat.encodeToByteArray(SimpleData.serializer(), original)
        val decoded = ByteArrayFormat.decodeFromByteArray(SimpleData.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Edge Case Tests ====================

    @Serializable
    data class EmptyData(val unit: Unit = Unit)

    @Test
    fun `test encode and decode data class with Unit`() {
        val original = EmptyData()
        val encoded = format.encodeToByteArray(EmptyData.serializer(), original)
        val decoded = format.decodeFromByteArray(EmptyData.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class MultipleNullables(val a: Int?, val b: String?, val c: Boolean?)

    @Test
    fun `test encode and decode multiple nullable fields all null`() {
        val original = MultipleNullables(null, null, null)
        val encoded = format.encodeToByteArray(MultipleNullables.serializer(), original)
        val decoded = format.decodeFromByteArray(MultipleNullables.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode multiple nullable fields mixed`() {
        val original = MultipleNullables(42, null, true)
        val encoded = format.encodeToByteArray(MultipleNullables.serializer(), original)
        val decoded = format.decodeFromByteArray(MultipleNullables.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode multiple nullable fields all set`() {
        val original = MultipleNullables(42, "Hello", true)
        val encoded = format.encodeToByteArray(MultipleNullables.serializer(), original)
        val decoded = format.decodeFromByteArray(MultipleNullables.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Serialization Stability Tests ====================

    @Test
    fun `test multiple encode decode cycles maintain data integrity`() {
        var data = SimpleData(42, "Test")
        repeat(100) {
            val encoded = format.encodeToByteArray(SimpleData.serializer(), data)
            data = format.decodeFromByteArray(SimpleData.serializer(), encoded)
        }
        assertEquals(SimpleData(42, "Test"), data)
    }

    @Test
    fun `test different instances produce same encoding`() {
        val data1 = SimpleData(42, "Test")
        val data2 = SimpleData(42, "Test")
        val encoded1 = format.encodeToByteArray(SimpleData.serializer(), data1)
        val encoded2 = format.encodeToByteArray(SimpleData.serializer(), data2)
        assertContentEquals(encoded1, encoded2)
    }

    // ==================== Boundary Value Tests ====================

    @Serializable
    data class AllBoundaries(
        val minByte: Byte,
        val maxByte: Byte,
        val minShort: Short,
        val maxShort: Short,
        val minInt: Int,
        val maxInt: Int,
        val minLong: Long,
        val maxLong: Long
    )

    @Test
    fun `test encode and decode all boundary values`() {
        val original = AllBoundaries(
            minByte = Byte.MIN_VALUE,
            maxByte = Byte.MAX_VALUE,
            minShort = Short.MIN_VALUE,
            maxShort = Short.MAX_VALUE,
            minInt = Int.MIN_VALUE,
            maxInt = Int.MAX_VALUE,
            minLong = Long.MIN_VALUE,
            maxLong = Long.MAX_VALUE
        )
        val encoded = format.encodeToByteArray(AllBoundaries.serializer(), original)
        val decoded = format.decodeFromByteArray(AllBoundaries.serializer(), encoded)
        assertEquals(original, decoded)
    }

    // ==================== Collection Edge Cases ====================

    @Test
    fun `test encode and decode list with single null element`() {
        val original = listOf<Int?>(null)
        val encoded = format.encodeToByteArray(ListSerializer(Int.serializer().nullable), original)
        val decoded = format.decodeFromByteArray(ListSerializer(Int.serializer().nullable), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode list with mixed null and non-null`() {
        val original = listOf(1, null, 3, null, 5)
        val encoded = format.encodeToByteArray(ListSerializer(Int.serializer().nullable), original)
        val decoded = format.decodeFromByteArray(ListSerializer(Int.serializer().nullable), encoded)
        assertEquals(original, decoded)
    }

    @Serializable
    data class RecursiveContainer(val value: Int, val children: List<RecursiveContainer>)

    @Test
    fun `test encode and decode recursive structure with empty children`() {
        val original = RecursiveContainer(1, emptyList())
        val encoded = format.encodeToByteArray(RecursiveContainer.serializer(), original)
        val decoded = format.decodeFromByteArray(RecursiveContainer.serializer(), encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun `test encode and decode recursive structure with nested children`() {
        val original = RecursiveContainer(
            1,
            listOf(
                RecursiveContainer(2, emptyList()),
                RecursiveContainer(3, listOf(
                    RecursiveContainer(4, emptyList())
                ))
            )
        )
        val encoded = format.encodeToByteArray(RecursiveContainer.serializer(), original)
        val decoded = format.decodeFromByteArray(RecursiveContainer.serializer(), encoded)
        assertEquals(original, decoded)
    }
}