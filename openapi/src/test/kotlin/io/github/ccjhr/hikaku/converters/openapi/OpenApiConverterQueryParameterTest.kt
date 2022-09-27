package io.github.ccjhr.hikaku.converters.openapi

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.endpoints.QueryParameter
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class OpenApiConverterQueryParameterTest {

    @Test
    fun `query parameter inline declaration on Operation object`() {
        //given
        val file = this::class.java.classLoader.getResource("query_parameter/query_parameter_inline.yaml").toURI().toPath()
        val queryParameters = setOf(
            QueryParameter("tag", false),
            QueryParameter("limit", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].queryParameters

        //then
        result mustSatisfy {
            it containsExactly queryParameters
        }
    }

    @Test
    fun `one query parameter declared inline and one parameter referenced from parameters section in components`() {
        //given
        val file = this::class.java.classLoader.getResource("query_parameter/query_parameter_in_components.yaml").toURI().toPath()
        val queryParameters = setOf(
            QueryParameter("tag", false),
            QueryParameter("limit", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].queryParameters

        //then
        result mustSatisfy {
            it containsExactly queryParameters
        }
    }

    @Test
    fun `common query parameter inline declaration on Operation object`() {
        //given
        val file = this::class.java.classLoader.getResource("query_parameter/common_query_parameter_inline.yaml").toURI().toPath()
        val queryParameters = setOf(
            QueryParameter("tag", false),
            QueryParameter("limit", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].queryParameters

        //then
        result mustSatisfy {
            it containsExactly queryParameters
        }
    }

    @Test
    fun `one query parameter declared inline and one common parameter referenced from parameters section in components`() {
        //given
        val file = this::class.java.classLoader.getResource("query_parameter/common_query_parameter_in_components.yaml").toURI().toPath()
        val queryParameters = setOf(
            QueryParameter("tag", false),
            QueryParameter("limit", true),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()[0].queryParameters

        //then
        result mustSatisfy {
            it containsExactly queryParameters
        }
    }
}