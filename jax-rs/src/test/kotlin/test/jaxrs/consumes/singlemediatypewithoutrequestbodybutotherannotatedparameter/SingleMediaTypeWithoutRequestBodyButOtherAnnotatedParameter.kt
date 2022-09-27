package test.jaxrs.consumes.singlemediatypewithoutrequestbodybutotherannotatedparameter

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.Encoded
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/todos")
@Suppress("UNUSED_PARAMETER")
class SingleMediaTypeWithoutRequestBodyButOtherAnnotatedParameter {

    @GET
    @Consumes("application/json")
    fun todo(@Encoded filter: String) {
    }
}
