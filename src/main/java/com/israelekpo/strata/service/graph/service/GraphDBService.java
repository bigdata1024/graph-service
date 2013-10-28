package com.israelekpo.strata.service.graph.service;

import java.util.List;

import com.israelekpo.strata.service.graph.model.User;

public interface GraphDBService {

  public void createUser(User user);

  public User getUser(String userId);

  public int connectUsers(final String userId1, final String userId2);

  public List<User> getUserConnections(final String userId);

}
