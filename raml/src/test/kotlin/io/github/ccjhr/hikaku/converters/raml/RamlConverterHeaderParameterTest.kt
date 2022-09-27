package io.github.ccjhr.hikaku.converters.raml

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HeaderParameter
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterHeaderParameterTest {

    @Test
    fun `extract an optional and a required header parameter`() {
        // given
        val file = this::class.java.classLoader.getResource("header_parameter.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                headerParameters = setOf(
                    HeaderParameter("allow-cache", true),
                    HeaderParameter("x-b3-traceid", false),
                ),
            ),
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }
}