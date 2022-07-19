package de.codecentric.hikaku.converters.openapi

import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.POST
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test
import java.nio.file.Paths

class OpenApiConverterConsumesTest {

    @Test
    fun `inline declaration`() {
        //given
        val file = Paths.get(this::class.java.classLoader.getResource("consumes/consumes_inline.yaml").toURI())
        val implementation = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = POST,
                consumes = setOf("application/xml"),
            )
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `response is declared in components section`() {
        //given
        val file = Paths.get(
            this::class.java.classLoader.getResource("consumes/consumes_requestbody_in_components.yaml").toURI()
        )
        val implementation = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = POST,
                consumes = setOf("application/xml"),
            )
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }
}