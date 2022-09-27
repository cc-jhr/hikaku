package io.github.ccjhr.hikaku.converters.wadl

import io.github.ccjhr.hikaku.endpoints.MatrixParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test
import java.nio.file.Paths

class WadlConverterMatrixParameterTest {

    @Test
    fun `check that matrix parameter are extracted correctly`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("matrix_parameters.wadl").toURI())
        val matrixParameters = setOf(
                MatrixParameter("done", false),
                MatrixParameter("tag", true),
        )

        // when
        val specification = WadlConverter(file)

        // then
        specification.conversionResult.first().matrixParameters mustSatisfy {
            it containsExactly matrixParameters
        }
    }
}