package io.github.ccjhr.hikaku.converters.spring.path

import io.github.ccjhr.Experimental
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.Hikaku
import io.github.ccjhr.hikaku.HikakuConfig
import io.github.ccjhr.hikaku.SupportedFeatures
import io.github.ccjhr.hikaku.converters.EndpointConverter
import io.github.ccjhr.hikaku.converters.spring.SpringConverter
import io.github.ccjhr.hikaku.converters.spring.SpringConverter.Companion.IGNORE_ERROR_ENDPOINT
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
import io.github.ccjhr.hikaku.endpoints.PathParameter
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.throwable.noExceptionThrown
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.ConfigurableApplicationContext
import kotlin.test.Test

@OptIn(Experimental::class)
class SpringConverterPathTest {

    @Nested
    inner class RequestMappingTests {

        @Nested
        @WebMvcTest(RequestMappingIgnoreErrorPathController::class)
        inner class RequestMappingIgnoreErrorPathTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `pre defined filter for ignoring error paths`() {
                //given
                val specification = object : EndpointConverter {
                    override val conversionResult = setOf(
                        Endpoint(httpMethod = GET, path = "/todos"),
                        Endpoint(httpMethod = HEAD, path = "/todos"),
                        Endpoint(httpMethod = OPTIONS, path = "/todos"),
                    )
                    override val supportedFeatures = SupportedFeatures()
                }

                val hikakuConfig = HikakuConfig(filters = listOf(IGNORE_ERROR_ENDPOINT))
                val implementation = SpringConverter(context)

                //when
                val hikaku = Hikaku(config = hikakuConfig, specification = specification, implementation = implementation)

                //then
                noExceptionThrown {
                    hikaku.match()
                }
            }
        }

        @Nested
        inner class ClassLevelTests {

            @Nested
            @WebMvcTest(
                RequestMappingOnClassWithMultiplePathsController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnClassWithMultiplePathsTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `multiple paths extracted correctly`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint("/todos", GET),
                        Endpoint("/todos", OPTIONS),
                        Endpoint("/todos", HEAD),
                        Endpoint("/todo/list", GET),
                        Endpoint("/todo/list", OPTIONS),
                        Endpoint("/todo/list", HEAD),
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
                RequestMappingOnClassProvidingRegexForPathVariableController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnClassProvidingRegexForPathVariableTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `endpoint having regex on path parameter using RequestMapping converts to a path without the regex`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint(
                            path = "/todos/{id}",
                            httpMethod = GET,
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
                        Endpoint("/todos/{id}", OPTIONS),
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
                RequestMappingOnClassProvidingComplexRegexForPathVariableController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnClassProvidingComplexRegexForPathVariableTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `endpoint having complex regex on path parameter using RequestMapping converts to a path without the regex`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint(
                            path = "/todos/{id}",
                            httpMethod = GET,
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
                        Endpoint("/todos/{id}", OPTIONS),
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
                RequestMappingOnClassProvidingMultipleRegexForPathVariableController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnClassProvidingMutlipleRegexForPathVariableTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `endpoint having complex regex on path parameter using RequestMapping converts to a path without the regex`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint(
                            path = "/todos/{id}/{title}",
                            httpMethod = GET,
                            pathParameters = setOf(
                                PathParameter("id"),
                                PathParameter("title"),
                            ),
                        ),
                        Endpoint(
                            path = "/todos/{id}/{title}",
                            httpMethod = HEAD,
                            pathParameters = setOf(
                                PathParameter("id"),
                                PathParameter("title"),
                            ),
                        ),
                        Endpoint("/todos/{id}/{title}", OPTIONS),
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

        @Nested
        inner class FunctionLevelTest {

            @Nested
            @WebMvcTest(
                RequestMappingOnFunctionWithMultiplePathsController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnFunctionWithMultiplePathsTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `multiple paths extracted correctly`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint("/todos", GET),
                        Endpoint("/todos", OPTIONS),
                        Endpoint("/todos", HEAD),
                        Endpoint("/todo/list", GET),
                        Endpoint("/todo/list", OPTIONS),
                        Endpoint("/todo/list", HEAD),
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
                RequestMappingOnFunctionProvidingRegexForPathVariableController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnFunctionProvidingRegexForPathVariableTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `endpoint having regex on path parameter using RequestMapping converts to a path without the regex`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint(
                            path = "/todos/{id}",
                            httpMethod = GET,
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
                        Endpoint("/todos/{id}", OPTIONS),
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
                RequestMappingOnFunctionProvidingComplexRegexForPathVariableController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnFunctionProvidingComplexRegexForPathVariableTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `endpoint having complex regex on path parameter using RequestMapping converts to a path without the regex`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint(
                            path = "/todos/{id}",
                            httpMethod = GET,
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
                        Endpoint("/todos/{id}", OPTIONS),
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
                RequestMappingOnFunctionProvidingMultipleRegexForPathVariableController::class,
                excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
            )
            inner class RequestMappingOnFunctionProvidingMutlipleRegexForPathVariableTest {
                @Autowired
                lateinit var context: ConfigurableApplicationContext

                @Test
                fun `endpoint having complex regex on path parameter using RequestMapping converts to a path without the regex`() {
                    //given
                    val specification: Set<Endpoint> = setOf(
                        Endpoint(
                            path = "/todos/{id}/{title}",
                            httpMethod = GET,
                            pathParameters = setOf(
                                PathParameter("id"),
                                PathParameter("title"),
                            ),
                        ),
                        Endpoint(
                            path = "/todos/{id}/{title}",
                            httpMethod = HEAD,
                            pathParameters = setOf(
                                PathParameter("id"),
                                PathParameter("title"),
                            ),
                        ),
                        Endpoint("/todos/{id}/{title}", OPTIONS),
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
    }

    @Nested
    inner class HttpMethodMappingAnnotationTests {

        @Nested
        @WebMvcTest(
            GetMappingOnFunctionWithMultiplePathsController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class GetMappingOnFunctionWithMultiplePathsTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todos", GET),
                    Endpoint("/todos", OPTIONS),
                    Endpoint("/todos", HEAD),
                    Endpoint("/todo/list", GET),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            GetMappingProvidingRegexForPathVariableController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class GetMappingProvidingRegexForPathVariableTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `endpoint having regex on path parameter using converts to a path without the regex`() {
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
            DeleteMappingOnFunctionWithMultiplePathsController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class DeleteMappingOnFunctionWithMultiplePathsTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todos", DELETE),
                    Endpoint("/todos", OPTIONS),
                    Endpoint("/todos", HEAD),
                    Endpoint("/todo/list", DELETE),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            DeleteMappingProvidingRegexForPathVariableController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class DeleteMappingProvidingRegexForPathVariableTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `endpoint having regex on path parameter using converts to a path without the regex`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint(
                        path = "/todos/{id}",
                        httpMethod = DELETE,
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
            PatchMappingOnFunctionWithMultiplePathsController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PatchMappingOnFunctionWithMultiplePathsTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todos", PATCH),
                    Endpoint("/todos", OPTIONS),
                    Endpoint("/todos", HEAD),
                    Endpoint("/todo/list", PATCH),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            PatchMappingProvidingRegexForPathVariableController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PatchMappingProvidingRegexForPathVariableTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `endpoint having regex on path parameter using converts to a path without the regex`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint(
                        path = "/todos/{id}",
                        httpMethod = PATCH,
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
            PostMappingOnFunctionWithMultiplePathsController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PostMappingOnFunctionWithMultiplePathsTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todos", POST),
                    Endpoint("/todos", OPTIONS),
                    Endpoint("/todos", HEAD),
                    Endpoint("/todo/list", POST),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD)
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
            PostMappingProvidingRegexForPathVariableController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PostMappingProvidingRegexForPathVariableTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `endpoint having regex on path parameter using converts to a path without the regex`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint(
                        path = "/todos/{id}",
                        httpMethod = POST,
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
            PutMappingOnFunctionWithMultiplePathsController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PutMappingOnFunctionWithMultiplePathsTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todos", PUT),
                    Endpoint("/todos", OPTIONS),
                    Endpoint("/todos", HEAD),
                    Endpoint("/todo/list", PUT),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            PutMappingProvidingRegexForPathVariableController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PutMappingProvidingRegexForPathVariableTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `endpoint having regex on path parameter using converts to a path without the regex`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint(
                        path = "/todos/{id}",
                        httpMethod = PUT,
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

    }

    @Nested
    inner class ConjunctionTests {

        @Nested
        @WebMvcTest(
            RequestMappingNestedPathController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class RequestMappingNestedPathTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `nested paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todo/list", GET),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            GetMappingNestedPathController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class GetMappingNestedPathTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todo/list", GET),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            DeleteMappingNestedPathController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class DeleteMappingNestedPathTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todo/list", DELETE),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            PatchMappingNestedPathController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PatchMappingNestedPathTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todo/list", PATCH),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            PostMappingNestedPathController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PostMappingNestedPathTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todo/list", POST),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
            PutMappingNestedPathController::class,
            excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class]
        )
        inner class PutMappingNestedPathTest {
            @Autowired
            lateinit var context: ConfigurableApplicationContext

            @Test
            fun `multiple paths extracted correctly`() {
                //given
                val specification: Set<Endpoint> = setOf(
                    Endpoint("/todo/list", PUT),
                    Endpoint("/todo/list", OPTIONS),
                    Endpoint("/todo/list", HEAD),
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
}
