package io.github.ccjhr.hikaku.extensions

import io.github.ccjhr.boolean.`is`
import io.github.ccjhr.hikaku.extensions.isString
import io.github.ccjhr.hikaku.extensions.isUnit
import io.github.ccjhr.mustSatisfy
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import java.lang.String as JavaString

class ClassExtensionsTest {

    @Nested
    inner class IsUnitTests {

        @Test
        fun `returns true for kotlin type Unit`() {
            // given
            val obj = Unit::class

            // when
            val result = obj.isUnit()

            // then
            result mustSatisfy {
                it `is` true
            }
        }

        @Test
        fun `returns true for java type Void`() {
            // given
            val obj = Void::class

            // when
            val result = obj.isUnit()

            // then
            result mustSatisfy {
                it `is` true
            }
        }

        @Test
        fun `returns false for any other type`() {
            // given
            val obj = Int::class

            // when
            val result = obj.isUnit()

            // then
            result mustSatisfy {
                it `is` false
            }
        }
    }

    @Nested
    inner class IsStringTests {

        @Test
        fun `returns true for kotlin type String`() {
            // given
            val obj = String::class

            // when
            val result = obj.isString()

            // then
            result mustSatisfy {
                it `is` true
            }
        }

        @Test
        fun `returns true for java type String`() {
            // given
            val obj = JavaString::class

            // when
            val result = obj.isString()

            // then
            result mustSatisfy {
                it `is` true
            }
        }

        @Test
        fun `returns false for any other type`() {
            // given
            val obj = Int::class

            // when
            val result = obj.isString()

            // then
            result mustSatisfy {
                it `is` false
            }
        }
    }
}
