package de.codecentric.hikaku.converters.openapi

import io.github.ccjhr.hikaku.endpoints.HeaderParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class OpenApiConverterHeaderParameterTest {

    @Test
    fun `header parameter inline declaration on Operation object`() {
        //given
        val file = this::class.java.classLoader.getResource("header_parameter/header_parameter_inline.yaml").toURI().toPath()
        val headerParameters = setOf(
            HeaderParameter("x-b3-traceid", false),
            HeaderParameter("allow-cache", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].headerParameters

        //then
        result mustSatisfy {
            it containsExactly headerParameters
        }
    }

    @Test
    fun `one header parameter declared inline and one parameter referenced from parameters section in components`() {
        //given
        val file = this::class.java.classLoader.getResource("header_parameter/header_parameter_in_components.yaml").toURI().toPath()
        val headerParameters = setOf(
            HeaderParameter("x-b3-traceid", false),
            HeaderParameter("allow-cache", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].headerParameters

        //then
        result mustSatisfy {
            it containsExactly headerParameters
        }
    }

    @Test
    fun `common header parameter inline declaration on Operation object`() {
        //given
        val file = this::class.java.classLoader.getResource("header_parameter/common_header_parameter_inline.yaml").toURI().toPath()
        val headerParameters = setOf(
            HeaderParameter("x-b3-traceid", false),
            HeaderParameter("allow-cache", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].headerParameters

        //then
        result mustSatisfy {
            it containsExactly headerParameters
        }
    }

    @Test
    fun `one common header parameter declared inline and one parameter referenced from parameters section in components`() {
        //given
        val file = this::class.java.classLoader.getResource("header_parameter/common_header_parameter_in_components.yaml").toURI().toPath()
        val headerParameters = setOf(
            HeaderParameter("x-b3-traceid", false),
            HeaderParameter("allow-cache", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].headerParameters

        //then
        result mustSatisfy {
            it containsExactly headerParameters
        }
    }
}