package com.israelekpo.strata.service.graph.webservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.israelekpo.strata.service.graph.model.User;
import com.israelekpo.strata.service.graph.service.GraphDBService;

public class GraphWebServiceImpl implements GraphWebService {

  private GraphDBService graphDBService;

  public GraphDBService getGraphDBService() {
    return graphDBService;
  }

  public void setGraphDBService(GraphDBService graphDBService) {
    this.graphDBService = graphDBService;
  }

  @Override
  public Response getUserDetails(String userId) {

    User user = this.graphDBService.getUser(userId);

    if (user == null) {
      return Response.status(404).entity("User Id " + userId + " does not exist in the graph database ").build();
    }

    return Response.ok().entity(user).build();
  }

  @Override
  public Response createUser(User user) {

    URI location = null;

    try {

      this.graphDBService.createUser(user);

      location = new URI("/users/" + user.getId());

    } catch (Exception e) {

      return Response.serverError().entity(e).build();
    }

    return Response.created(location).build();
  }

  @Override
  public Response createConnection(String user1, String user2) {

    int returnStatus = this.graphDBService.connectUsers(user1, user2);

    URI location = null;

    // Both nodes exists and a connection was successfully created
    if (returnStatus == 0) {
      try {
        location = new URI("/connections/" + user1);
      } catch (URISyntaxException e) {

      }

      return Response.created(location).build();
    }

    return Response.status(400).entity("One of more of the nodes are not valid nodes").build();
  }

  @Override
  public Response getConnections(String userId) {

    Map<String, User> connections = this.graphDBService.getUserConnections(userId);

    if (null == connections) {
      return Response.status(404).entity("No connections found for user " + userId).build();
    }

    return Response.ok().entity(connections).build();
  }

}
