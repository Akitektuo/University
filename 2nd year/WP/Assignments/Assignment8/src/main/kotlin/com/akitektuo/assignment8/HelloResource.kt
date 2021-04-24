package com.akitektuo.assignment8

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/hello-world")
class HelloResource {
    @GET
    @Produces("text/plain")
    fun hello(): String {
        return "Hello, World!"
    }
}