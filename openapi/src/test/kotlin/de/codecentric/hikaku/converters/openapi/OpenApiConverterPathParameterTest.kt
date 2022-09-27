package de.codecentric.hikaku.converters.openapi

import io.github.ccjhr.hikaku.endpoints.PathParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test
import kotlin.io.path.toPath

class OpenApiConverterPathParameterTest {

    @Test
    fun `path parameter inline declaration on Operation object`() {
        //given
        val file = this::class.java.classLoader.getResource("path_parameter/path_parameter_inline.yaml").toURI().toPath()
        val pathParameter = setOf(
            PathParameter("id"),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()

        //then
        result mustSatisfy {
            it.content[0].pathParameters mustSatisfy { firstEndpointPathParameters ->
                firstEndpointPathParameters containsExactly pathParameter
            }

            it.content[1].pathParameters mustSatisfy { secondEndpointPathParameter ->
                secondEndpointPathParameter containsExactly pathParameter
            }

            it.content[2].pathParameters mustSatisfy { thirdEndpointPathParameter ->
                thirdEndpointPathParameter containsExactly pathParameter
            }
        }
    }

    @Test
    fun `one path parameter declared inline and two parameters referenced from parameters section in components`() {
        //given
        val file = this::class.java.classLoader.getResource("path_parameter/path_parameter_in_components.yaml").toURI().toPath()
        val pathParameter = setOf(
            PathParameter("id"),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()

        //then
        result mustSatisfy {
            it.content[0].pathParameters mustSatisfy { firstEndpointPathParameters ->
                firstEndpointPathParameters containsExactly pathParameter
            }

            it.content[1].pathParameters mustSatisfy { secondEndpointPathParameter ->
                secondEndpointPathParameter containsExactly pathParameter
            }

            it.content[2].pathParameters mustSatisfy { thirdEndpointPathParameter ->
                thirdEndpointPathParameter containsExactly pathParameter
            }
        }
    }

    @Test
    fun `common path parameter inline declaration`() {
        //given
        val file = this::class.java.classLoader.getResource("path_parameter/common_path_parameter_inline.yaml").toURI().toPath()
        val pathParameter = setOf(
            PathParameter("id"),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()

        //then
        result mustSatisfy {
            it.content[0].pathParameters mustSatisfy { firstEndpointPathParameters ->
                firstEndpointPathParameters containsExactly pathParameter
            }

            it.content[1].pathParameters mustSatisfy { secondEndpointPathParameter ->
                secondEndpointPathParameter containsExactly pathParameter
            }

            it.content[2].pathParameters mustSatisfy { thirdEndpointPathParameter ->
                thirdEndpointPathParameter containsExactly pathParameter
            }
        }
    }

    @Test
    fun `common path parameter referenced from parameters section in components`() {
        //given
        val file = this::class.java.classLoader.getResource("path_parameter/common_path_parameter_in_components.yaml").toURI().toPath()
        val pathParameter = setOf(
            PathParameter("id"),
        )

        //when
        val result = OpenApiConverter(file).conversionResult.toList()

        //then
        result mustSatisfy {
            it.content[0].pathParameters mustSatisfy { firstEndpointPathParameters ->
                firstEndpointPathParameters containsExactly pathParameter
            }

            it.content[1].pathParameters mustSatisfy { secondEndpointPathParameter ->
                secondEndpointPathParameter containsExactly pathParameter
            }

            it.content[2].pathParameters mustSatisfy { thirdEndpointPathParameter ->
                thirdEndpointPathParameter containsExactly pathParameter
            }
        }
    }
}