package io.github.ccjhr.hikaku.converters.spring.produces.servletresponse

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
import kotlin.test.Test

class SpringConverterProducesServletResponseTest {

    @Nested
    @WebMvcTest(
        ProducesServletResponseTestController::class,
        excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
    )
    inner class NoProducesInfoAndNoReturnTypeTest {

        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `media type and response servlet argument declared and no return type results in proper media type`() {
            //given
            val specification: Set<Endpoint> = setOf(
                Endpoint("/todos", GET, produces = setOf("text/plain")),
                Endpoint("/todos", HEAD, produces = setOf("text/plain")),
                Endpoint("/todos", OPTIONS, produces = emptySet())
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
