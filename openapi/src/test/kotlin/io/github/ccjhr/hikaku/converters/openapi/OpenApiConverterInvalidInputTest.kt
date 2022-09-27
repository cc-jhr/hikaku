package io.github.ccjhr.hikaku.converters.openapi

import io.github.ccjhr.hikaku.converters.EndpointConverterException
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.string.contains
import io.github.ccjhr.throwable.expectsException
import io.github.ccjhr.throwable.hasMessage
import org.junit.jupiter.api.Nested
import java.io.File
import kotlin.io.path.toPath
import kotlin.test.Test

class OpenApiConverterInvalidInputTest {

    @Nested
    inner class PathObjectTests {

        @Test
        fun `empty file returns an empty list`() {
            //given
            val file = this::class.java.classLoader.getResource("invalid_input/empty_file.yaml").toURI().toPath()

            //when
            val result = expectsException<EndpointConverterException> {
                OpenApiConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Given OpenAPI file is blank."
            }
        }

        @Test
        fun `file consisting solely of whitespaces returns an empty list`() {
            //given
            val file = this::class.java.classLoader.getResource("invalid_input/whitespaces_only_file.yaml").toURI().toPath()

            //when
            val result = expectsException<EndpointConverterException> {
                OpenApiConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Given OpenAPI file is blank."
            }
        }

        @Test
        fun `OpenAPI yaml file containing syntax error`() {
            //given
            val file = this::class.java.classLoader.getResource("invalid_input/syntax_error.yaml").toURI().toPath()
            val converter = OpenApiConverter(file)

            //when
            val result = expectsException<EndpointConverterException> {
                converter.conversionResult
            }

            // then
            result.message mustSatisfy {
                it contains "Failed to parse OpenAPI spec."
            }
        }
    }

    @Nested
    inner class FileObjectTests {

        @Test
        fun `empty file returns an empty list`() {
            //given
            val file = File(this::class.java.classLoader.getResource("invalid_input/empty_file.yaml").toURI())

            //when
            val result = expectsException<EndpointConverterException> {
                OpenApiConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Given OpenAPI file is blank."
            }
        }

        @Test
        fun `file consisting solely of whitespaces returns an empty list`() {
            //given
            val file = File(this::class.java.classLoader.getResource("invalid_input/whitespaces_only_file.yaml").toURI())

            //when
            val result = expectsException<EndpointConverterException> {
                OpenApiConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Given OpenAPI file is blank."
            }
        }

        @Test
        fun `OpenAPI yaml file containing syntax error`() {
            //given
            val file = File(this::class.java.classLoader.getResource("invalid_input/syntax_error.yaml").toURI())
            val converter = OpenApiConverter(file)

            //when
            val result = expectsException<EndpointConverterException> {
                converter.conversionResult
            }

            // then
            result.message mustSatisfy {
                it contains "Failed to parse OpenAPI spec"
            }
        }
    }
}