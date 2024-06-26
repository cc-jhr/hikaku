package io.github.ccjhr.hikaku.converters.spring.pathparameters

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.converters.spring.SpringConverter
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
import io.github.ccjhr.hikaku.endpoints.PathParameter
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.throwable.expectsException
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_HTML_VALUE
import kotlin.test.Test

class SpringConverterPathParameterTest {

    @Nested
    @WebMvcTest(
        PathParameterNamedByVariableController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class PathParameterNamedByVariableTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by variable name`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = GET,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
                Endpoint("/todos/{id}", OPTIONS),
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = HEAD,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
            )

            //when
            val implementation = SpringConverter(context).conversionResult

            //then
            implementation mustSatisfy {
                it containsExactly specification
            }
        }
    }

    @Nested
    @WebMvcTest(
        PathParameterNamedByValueAttributeController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class PathParameterNamedByValueAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by 'value' attribute`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = GET,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
                Endpoint("/todos/{id}", OPTIONS),
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = HEAD,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
            )

            //when
            val implementation = SpringConverter(context).conversionResult

            //then
            implementation mustSatisfy {
                it containsExactly specification
            }
        }
    }

    @Nested
    @WebMvcTest(
        PathParameterNamedByNameAttributeController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class PathParameterNamedByNameAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by 'name' attribute`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = GET,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
                Endpoint("/todos/{id}", OPTIONS),
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = HEAD,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
            )

            //when
            val implementation = SpringConverter(context).conversionResult

            //then
            implementation mustSatisfy {
                it containsExactly specification
            }
        }
    }

    @Nested
    @WebMvcTest(
        PathParameterHavingBothValueAndNameAttributeController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class PathParameterHavingBothValueAndNameAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by both 'value' and 'name' attribute`() {
            expectsException<IllegalStateException> {
                SpringConverter(context).conversionResult
            }
        }
    }

    @Nested
    @WebMvcTest(
        PathParameterSupportedForOptionsIfExplicitlyDefinedController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class PathParameterSupportedForOptionsIfExplicitlyDefinedTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter are supported for OPTIONS if defined explicitly`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = OPTIONS,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = HEAD,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
            )

            //when
            val implementation = SpringConverter(context).conversionResult

            //then
            implementation mustSatisfy {
                it containsExactly specification
            }
        }
    }

    @Nested
    @WebMvcTest(PathParameterOnDefaultErrorEndpointController::class)
    inner class PathParameterOnDefaultErrorEndpointTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameters are not added to default error endpoint`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = GET,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
                Endpoint("/todos/{id}", OPTIONS),
                Endpoint(
                    path = "/todos/{id}",
                    httpMethod = HEAD,
                    pathParameters = setOf(
                        PathParameter("id"),
                    ),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = GET,
                    produces = setOf(APPLICATION_JSON_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = POST,
                    produces = setOf(APPLICATION_JSON_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = HEAD,
                    produces = setOf(APPLICATION_JSON_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = PUT,
                    produces = setOf(APPLICATION_JSON_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = PATCH,
                    produces = setOf(APPLICATION_JSON_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = DELETE,
                    produces = setOf(APPLICATION_JSON_VALUE),
                ),
                Endpoint("/error", OPTIONS),
                Endpoint(
                    path = "/error",
                    httpMethod = GET,
                    produces = setOf(TEXT_HTML_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = POST,
                    produces = setOf(TEXT_HTML_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = HEAD,
                    produces = setOf(TEXT_HTML_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = PUT,
                    produces = setOf(TEXT_HTML_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = PATCH,
                    produces = setOf(TEXT_HTML_VALUE),
                ),
                Endpoint(
                    path = "/error",
                    httpMethod = DELETE,
                    produces = setOf(TEXT_HTML_VALUE),
                ),
                Endpoint("/error", OPTIONS),
            )

            //when
            val implementation = SpringConverter(context).conversionResult

            //then
            implementation mustSatisfy {
                it containsExactly specification
            }
        }
    }
}
