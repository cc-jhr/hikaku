package io.github.ccjhr.hikaku.converters.spring.produces.servletresponse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

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
