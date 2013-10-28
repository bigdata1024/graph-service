package com.israelekpo.strata.service.graph.service;

import java.util.List;

import com.israelekpo.strata.service.graph.dao.GraphDBDAO;
import com.israelekpo.strata.service.graph.model.User;

public class GraphDBServiceImpl implements GraphDBService {

  GraphDBDAO graphDBDAO;

  public GraphDBDAO getGraphDBDAO() {
    return graphDBDAO;
  }

  public void setGraphDBDAO(GraphDBDAO graphDBDAO) {
    this.graphDBDAO = graphDBDAO;
  }

  @Override
  public void createUser(User user) {
    this.graphDBDAO.createUserNode(user);
  }

  @Override
  public User getUser(String userId) {
    return this.graphDBDAO.getUserNode(userId);
  }

  @Override
  public int connectUsers(String userId1, String userId2) {
    return this.graphDBDAO.connectUsers(userId1, userId2);
  }

  @Override
  public List<User> getUserConnections(String userId) {
      return this.graphDBDAO.getUserConnections(userId);
  }
}
