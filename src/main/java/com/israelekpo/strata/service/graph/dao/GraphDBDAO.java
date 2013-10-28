package com.israelekpo.strata.service.graph.dao;

import java.util.Map;

import com.israelekpo.strata.service.graph.model.User;

public interface GraphDBDAO {

  public String getDbPath();

  public void setDbPath(String dbPath);

  public void createUserNode(User user);

  public User getUserNode(String userId);

  public Map<String, User> getUserConnections(final String userId);

  public int connectUsers(final String userId1, final String userId2);
}
