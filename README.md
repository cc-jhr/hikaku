# hikaku
[![Build](https://github.com/cc-jhr/hikaku/actions/workflows/build.yml/badge.svg)](https://github.com/cc-jhr/hikaku/actions/workflows/build.yml) [![Coverage Status](https://coveralls.io/repos/github/cc-jhr/hikaku/badge.svg?branch=master)](https://coveralls.io/github/cc-jhr/hikaku?branch=master)

<p align="center">
  <img src="docs/images/hikaku-logo-small.png">
</p>

Hikaku (比較) is japanese and means "comparison". This library tests if a REST-API implementation meets its specification.

If you create your REST-API contract-first without using any type of generation, you have to make sure that specification and implementation don't diverge.
The aim of this project is to meet this need and offer a mechanism to check specification and implementation for equality without having to create requests which are fired against a mock server. So this library won't check the behavior of the API, but the structural correctness. Please see also the section [limitations](#limitations)

## Currently supported

+ **Specifications**
  + [OpenAPI 3.1.X](openapi/README.md)
  + [RAML 1.X](raml/README.md)
  + [WADL](wadl/README.md)
+ **Implementations**
  + [Spring WebMVC 6.0.X](spring/README.md)
  + [Micronaut 4.0.X](micronaut/README.md)
  + [JAX-RS 3.1.X](jax-rs/README.md)
    + [Apache CXF](http://cxf.apache.org)
    + [Dropwizard](https://www.dropwizard.io)
    + [Jersey](https://jersey.github.io)
    + [Resteasy](https://resteasy.github.io)
    + [Restlet](https://restlet.com/open-source/documentation/user-guide/2.3/extensions/jaxrs)
    + [Quarkus](https://quarkus.io)
  
Please refer to the list of [all features](docs/features.md). To check the feature support for each converter.
It is possible that not every converter supports every feature. Only the intersection of the features of two `EndpointConverter`s is used for the matching. Please keep that in mind regarding the equality of implementation and specification.

## Setup

In order to use this library you need a github account and a repository read token.
When logged-in open the [personal access tokens page](https://github.com/settings/tokens). Create a new token having `read:packages` as the only permission.

### Gradle - kotlin-dsl

Add the repository to your existing list of repositories:

```gradle
repositories {
    maven {
        name = "nagare"
        url = uri("https://maven.pkg.github.com/cc-jhr/hikaku")
        credentials {
            username = "your-github-username-here"            // you should probably use environment variables
            password = "your-github-packages-read-token-here" // or gradle properties here to inject the values
        }
    }
}
```

There is an artifact for each converter. So you need one dependency for the specification and one for the implementation.
Here is an example for OpenAPI as specification and Spring as implementation.

```gradle
dependencies {
    testImplementation "io.github.ccjhr.hikaku:hikaku-openapi:$hikakuVersion"
    testImplementation "io.github.ccjhr.hikaku:hikaku-spring:$hikakuVersion"
}
```

## Usage

Setting up a test with hikaku is very simple. You just instantiate the `Hikaku` class and provide an `EndpointConverter` for the specification and another one for the implementation. Optionally, you can also pass an instance of `HikakuConfig`. Check the list of options and default values of the [config](docs/config.md). Then you call `match()` on the `Hikaku` class.
The match result is sent to one or multiple `Reporter`. If the test fails kotlin's `DefaultAsserter.fail()` method is called.

In the following example our project consists of an OpenAPI specification and a Spring implementation. The specification does not contain the _/error_ endpoints created by spring, so we want to omit those.

#### Kotlin

And now we can create the test case:

```kotlin
@SpringBootTest
class SpecificationTest {

    @Autowired
    private lateinit var springContext: ApplicationContext

    @Test
    fun `specification matches implementation`() { 
        Hikaku(
            specification = OpenApiConverter(Paths.get("openapi.yaml")),
            implementation = SpringConverter(springContext),
            config = HikakuConfig(
                filters = listOf(SpringConverter.IGNORE_ERROR_ENDPOINT),
            ),
      )
      .match()
    }
}
```

#### Java

Same example in Java:

```java
@SpringBootTest
public class SpecificationTest {

  @Autowired
  private ApplicationContext springContext;

  @Test
  public void specification_matches_implementation() {
    List<Function1<Endpoint, Boolean>> filters = new ArrayList<>();
    filters.add(SpringConverter.IGNORE_ERROR_ENDPOINT);

    List<Reporter> reporters = new ArrayList<>();
    reporters.add(new CommandLineReporter());

    new Hikaku(
        new OpenApiConverter(Paths.get("openapi.json")),
        new SpringConverter(springContext),
        new HikakuConfig(
            reporters,
            filters
        )
    )
    .match();
  }
}
```

## Limitations
Hikaku checks the implementation with static code analysis. So everything that is highly dynamic is not covered by hikaku. There might be other libraries and frameworks that can cover these aspects by checking the behavior.

### http status codes
For implementations the status codes are very dynamic. There are various ways to set a http status. For example using a `ResponseEntity` object in spring or using additional filters and so on. That's why hikaku does not support http status codes.

### Request and response object
For implementations both request and response objects are highly dynamic. For response objects there might be a generic `ResponseEntity` as well or interfaces with different implementations can be used. In both cases (request and response) the objects can be altered by a serialization library and there a lot of different libs out there. That's why hikaku neither supports request nor response objects.

## More Info

* **Blog (english):** [Spotting mismatches between your spec and your REST-API with hikaku](https://blog.codecentric.de/en/2019/03/spot-mismatches-between-your-spec-and-your-rest-api/)
* **Blog (german):** [ Abweichungen zwischen Spezifikation und REST-API mit hikaku erkennen](https://blog.codecentric.de/2019/03/abweichungen-zwischen-rest-api-spezifikation-erkennen/)
* **Sample project** [A complete sample project](https://github.com/cc-jhr/hikaku-sample)
