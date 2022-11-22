package io.github.ccjhr.hikaku.converters.spring.produces.servletresponse

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@SpringBootApplication
open class DummyApp

@Controller
open class ProducesServletResponseTestController {
    @GetMapping("/todos", produces = ["text/plain"])
    @ResponseBody
    fun getTest(response: HttpServletResponse) {
        response.outputStream.println("Hello, world!")
    }
}
