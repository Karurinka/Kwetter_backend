package Kwetter.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestResource {

    @GET
    public Response getTest() {
        String test = "Rest working :D";
        return Response.ok(test).build();
    }
}
