package io.github.ccjhr.hikaku.converters.spring.headerparameters

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.converters.spring.SpringConverter
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HeaderParameter
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
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

class SpringConverterHeaderParameterTest {

    @Nested
    @WebMvcTest(
        HeaderParameterNamedByVariableController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class HeaderParameterNamedByVariableTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `header parameter name defined by variable name`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    headerParameters = setOf(
                        HeaderParameter("allowCache", true),
                    ),
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    headerParameters = setOf(
                        HeaderParameter("allowCache", true),
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
        HeaderParameterNamedByValueAttributeController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class HeaderParameterNamedByValueAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `header parameter name defined by attribute 'value'`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", true),
                    ),
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", true),
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
        HeaderParameterNamedByNameAttributeController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class HeaderParameterNamedByNameAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `header parameter name defined by attribute 'name'`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", true),
                    ),
                ),
                Endpoint("/todos", OPTIONS),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", true),
                    ),
                )
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
        HeaderParameterHavingBothNameAndValueAttributeController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class HeaderParameterHavingBothNameAndValueAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `both 'value' and 'name' attribute defined for header parameter`() {
            expectsException<IllegalStateException> {
                SpringConverter(context).conversionResult
            }
        }
    }

    @Nested
    @WebMvcTest(HeaderParameterOptionalController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class HeaderParameterOptionalTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `header parameter optional`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", false),
                    ),
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", false),
                    ),
                )
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
        HeaderParameterOptionalBecauseOfDefaultValueController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class HeaderParameterOptionalBecauseOfDefaultValueTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `header parameter optional because of a default value`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    headerParameters = setOf(
                        HeaderParameter("tracker-id", false),
                    ),
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    headerParameters = setOf(
                        HeaderParameter("tracker-id", false),
                    ),
                )
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
    @WebMvcTest(HeaderParameterOnDefaultErrorEndpointController::class)
    inner class HeaderParameterOnDefaultErrorEndpointTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `header parameter is not available in default error endpoints`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", true),
                    ),
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    headerParameters = setOf(
                        HeaderParameter("allow-cache", true),
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
                Endpoint(
                    path = "/error",
                    httpMethod = OPTIONS,
                ),
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
                Endpoint(
                    path = "/error",
                    httpMethod = OPTIONS,
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
}
