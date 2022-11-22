package io.github.ccjhr.hikaku.converters.spring.produces.redirect

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@SpringBootApplication
open class DummyApp

@RestController
open class RedirectTestController {

    @GetMapping("/todos")
    fun todos(): RedirectView = RedirectView()
}

@RestController
open class RedirectUsingHttpServletResponseTestController {

    @GetMapping("/todos")
    @ResponseBody
    fun getTest(response: HttpServletResponse) {
        response.sendRedirect("http://localhost:8080/other")
    }
}
