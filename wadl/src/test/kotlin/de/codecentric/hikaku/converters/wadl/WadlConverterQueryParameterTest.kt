package de.codecentric.hikaku.converters.wadl

import de.codecentric.hikaku.endpoints.QueryParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test
import java.nio.file.Paths

class WadlConverterQueryParameterTest {

    @Test
    fun `check that query parameter are extracted correctly`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("query_parameters.wadl").toURI())
        val queryParameters = setOf(
                QueryParameter("tag", false),
                QueryParameter("limit", true),
        )

        // when
        val specification = WadlConverter(file)

        // then
        specification.conversionResult.first().queryParameters mustSatisfy {
            it containsExactly queryParameters
        }
    }
}