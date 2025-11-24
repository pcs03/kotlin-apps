package nl.pcstet.core.network

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import nl.pcstet.core.Error
import nl.pcstet.core.Result
import kotlin.test.Test

class ExampleTest {
    @Test
    fun testType() {
        val result = Result.Success("st")

        result.shouldBeTypeOf<Result.Success<String>>()
    }

    @Test
    fun testExample() {
        2 + 2 shouldBe 4
    }
}