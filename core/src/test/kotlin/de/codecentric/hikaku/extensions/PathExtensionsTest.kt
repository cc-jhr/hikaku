package de.codecentric.hikaku.extensions

import io.github.ccjhr.Experimental
import io.github.ccjhr.boolean.`is`
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.throwable.expectsException
import io.github.ccjhr.throwable.hasMessage
import io.github.ccjhr.throwable.noExceptionThrown
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import java.nio.file.Files.createTempDirectory
import kotlin.io.path.Path
import kotlin.io.path.toPath

@OptIn(Experimental::class)
class PathExtensionsTest {

    @Nested
    inner class CheckValidityTests {

        @Test
        fun `non-existing file throws an exception`() {
            // given
            val path = Path("test-file-which-does-not-exist.spec")

            // when
            val result = expectsException<IllegalArgumentException> {
                path.checkFileValidity()
            }

            // then
            result mustSatisfy {
                it hasMessage "Given file does not exist."
            }
        }

        @Test
        fun `directory in validity check throws an exception`() {
            // given
            val dir = createTempDirectory("tmp")

            // when
            val result = expectsException<IllegalArgumentException> {
                dir.checkFileValidity()
            }

            // then
            result mustSatisfy {
                it hasMessage "Given file is not a regular file."
            }
        }

        @Test
        fun `existing file with invalid file extension throws an exception`() {
            // given
            val path = this::class.java.classLoader.getResource("test_file.txt").toURI().toPath()

            // when
            val result = expectsException<IllegalArgumentException> {
                path.checkFileValidity(".css")
            }

            // then
            result mustSatisfy {
                it hasMessage "Given file is not of type .css"
            }
        }

        @Test
        fun `file is valid without extension check`() {
            // given
            val path = this::class.java.classLoader.getResource("test_file.txt").toURI().toPath()

            // when
            val result = noExceptionThrown {
                path.checkFileValidity()
            }

            // then
            result mustSatisfy {
                it `is` true
            }
        }

        @Test
        fun `file is valid with extension check`() {
            // given
            val path = this::class.java.classLoader.getResource("test_file.txt").toURI().toPath()

            // when
            val result = noExceptionThrown {
                path.checkFileValidity(".txt")
            }

            // then
            result mustSatisfy {
                it `is` true
            }
        }
    }
}