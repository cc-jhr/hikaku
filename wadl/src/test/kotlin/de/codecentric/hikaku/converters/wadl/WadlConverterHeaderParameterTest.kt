package de.codecentric.hikaku.converters.wadl

import de.codecentric.hikaku.endpoints.HeaderParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test
import java.nio.file.Paths

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