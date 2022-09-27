package io.github.ccjhr.hikaku.converters.spring.deprecation

import io.github.ccjhr.hikaku.converters.spring.SpringConverter
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import kotlin.test.Test

class SpringConverterDeprecationTest {

    @Nested
    @WebMvcTest(NoDeprecationController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class NoDeprecationTest {

        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `no deprecation`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    produces = setOf(APPLICATION_JSON_VALUE),
                    deprecated = false,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    produces = setOf(APPLICATION_JSON_VALUE),
                    deprecated = false,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                    deprecated = false,
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
    @Suppress("Deprecation")
    @WebMvcTest(DeprecatedClassController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class NoDeprecatedClassTest {

        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `deprecated class`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    produces = setOf(APPLICATION_JSON_VALUE),
                    deprecated = true,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    produces = setOf(APPLICATION_JSON_VALUE),
                    deprecated = true,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                    deprecated = true,
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
    @WebMvcTest(DeprecatedFunctionController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class NoDeprecatedFunctionTest {

        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `deprcated function`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint(
                    path = "/todos",
                    httpMethod = GET,
                    produces = setOf(APPLICATION_JSON_VALUE),
                    deprecated = true,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = HEAD,
                    produces = setOf(APPLICATION_JSON_VALUE),
                    deprecated = true,
                ),
                Endpoint(
                    path = "/todos",
                    httpMethod = OPTIONS,
                    deprecated = true,
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
