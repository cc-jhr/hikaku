package test.micronaut.consumes.onclass.consumesoverridescontroller.singlemediatype

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import test.micronaut.Todo


@Consumes("application/xml")
@Controller("/todos", consumes = ["text/plain"])
@Suppress("UNUSED_PARAMETER")
class ConsumesSingleMediaTypeTestController {

    @Post
    fun todos(@Body todo: Todo) {
    }
}
