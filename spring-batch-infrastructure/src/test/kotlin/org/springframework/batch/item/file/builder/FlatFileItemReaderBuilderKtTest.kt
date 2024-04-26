package org.springframework.batch.item.file.builder

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.file.transform.Range
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource

class FlatFileItemReaderBuilderKtTest {
    @Test
    fun test() {
        val reader = FlatFileItemReaderBuilder<Foo>().name("fooReader")
            .resource(getResource("1  2  3"))
            .fixedLength()
            .columns(Range(1, 3), Range(4, 6), Range(7))
            .names("first", "second", "third")
            .targetType(Foo::class.java)
            .build()

        reader.open(ExecutionContext())
        val item: Foo? = reader.read()
        assertNotNull(item)
        assertEquals(1, item!!.first)
        assertEquals(2, item.second)
        assertEquals("3", item.third)
        assertNull(reader.read())
    }
}

private fun getResource(contents: String): Resource = ByteArrayResource(contents.toByteArray())

data class Foo(
    val first: Int,
    val second: Int,
    val third: String,
)
