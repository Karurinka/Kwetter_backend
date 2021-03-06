package Kwetter.resource;

import Kwetter.model.User;
import Kwetter.service.LoginService;
import Kwetter.utility.LoginContainer;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/login")
public class LoginResource {

    @Inject
    private LoginService loginService;

    @Inject
    LoginContainer loginContainer;

    private Gson gson = new Gson();

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("/register")
    public Response register(String userJson) {
        String json = "";
        User createdUser = null;
        User user = gson.fromJson(userJson, User.class);
        if(loginService.checkUsername(user.getUsername()) == null) {
            createdUser = loginService.register(user.getUsername(), user.getPassword());
            json = gson.toJson(createdUser);
        }
        return Response.ok(json).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response Login(String userJson) {
        User attemptUser = gson.fromJson(userJson, User.class);
        String json = "";
        try {
            json = gson.toJson(loginContainer.getUser(attemptUser.getUsername(),
                                                      attemptUser.getPassword()));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return Response.ok(json).build();
    }
}

