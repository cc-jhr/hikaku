package io.github.ccjhr.hikaku.converters.wadl

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.endpoints.HeaderParameter
import io.github.ccjhr.mustSatisfy
import java.nio.file.Paths
import kotlin.test.Test

class WadlConverterHeaderParameterTest {

    @Test
    fun `check that header parameter are extracted correctly`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("header_parameters.wadl").toURI())
        val headerParameters = setOf(
            HeaderParameter("x-b3-traceid", false),
            HeaderParameter("allow-cache", true),
        )

        // when
        val specification = WadlConverter(file)

        // then
        specification.conversionResult.first().headerParameters mustSatisfy {
            it containsExactly headerParameters
        }
    }
}