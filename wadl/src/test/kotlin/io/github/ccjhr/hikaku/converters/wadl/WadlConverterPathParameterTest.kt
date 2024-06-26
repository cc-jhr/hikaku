package io.github.ccjhr.hikaku.converters.wadl

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.endpoints.PathParameter
import io.github.ccjhr.mustSatisfy
import java.nio.file.Paths
import kotlin.test.Test


class WadlConverterPathParameterTest {

    @Test
    fun `check that path parameter are extracted correctly`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("path_parameters.wadl").toURI())
        val pathParameter = listOf(
            PathParameter("id"),
        )

        // when
        val specification = WadlConverter(file)

        // then
        specification.conversionResult.first().pathParameters mustSatisfy {
            it containsExactly pathParameter
        }
    }
}