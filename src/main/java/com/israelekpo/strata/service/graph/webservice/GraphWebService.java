package com.israelekpo.strata.service.graph.webservice;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import com.israelekpo.strata.service.graph.model.User;

@WebService
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public interface GraphWebService {

  public static final String USER_ID = "userId";

  public static final String USER_1 = "user1";

  public static final String USER_2 = "user2";

  @GET
  @Path("/users/{userId}")
  public Response getUserDetails(@PathParam(USER_ID) String userId);

  @PUT
  @Path("/users")
  public Response createUser(@RequestBody User user);

  @POST
  @Path("/connections/{user1}/{user2}")
  public Response createConnection(@PathParam(USER_1) String user1, @PathParam(USER_2) String user2);

  @GET
  @Path("/connections/{userId}")
  public Response getConnections(@PathParam(USER_ID) String userId);

}
