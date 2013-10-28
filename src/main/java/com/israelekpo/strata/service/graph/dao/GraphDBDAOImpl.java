package com.israelekpo.strata.service.graph.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

import com.israelekpo.strata.service.graph.model.User;
import com.israelekpo.strata.service.graph.model.UserRelationshipTypes;

public class GraphDBDAOImpl implements GraphDBDAO {

  private static final Logger log = Logger.getLogger(GraphDBDAO.class);

  private static final String USERS_INDEX = "users";

  private GraphDatabaseService graphDb;

  private String dbPath;

  public GraphDBDAOImpl() {

  }

  private User retrieveUserIfExists(final String userId) {

    Index<Node> usersIndex = this.graphDb.index().forNodes(USERS_INDEX);

    Node node = usersIndex.get(User.ID, userId).getSingle();

    User user = new User();

    if (node != null && node.getProperty(User.ID) != null) {

      user.setId((String) node.getProperty(User.ID));
      user.setFname((String) node.getProperty(User.FNAME));
      user.setLname((String) node.getProperty(User.LNAME));

      return user;

     }

     return null;
  }

  public synchronized User getUserNode(String userId) {

    User user = retrieveUserIfExists(userId);

    if (null == user) {

      log.info("Request for userId " + userId + " did not find any nodes");

      return null;

    } else {

      log.info("Request for userId " + userId + " found one node");

      return user;
    }
  }

  public synchronized void createUserNode(User user) {

    Transaction tx = graphDb.beginTx();

    try {

      Index<Node> usersIndex = this.graphDb.index().forNodes(USERS_INDEX);

      Node node = graphDb.createNode();

      node.setProperty(User.ID, user.getId());
      node.setProperty(User.FNAME, user.getFname());
      node.setProperty(User.LNAME, user.getLname());

      Node originalNode = usersIndex.get(User.ID, user.getId()).getSingle();

      // If this node is already there, let's remove it and put a new one there
      if (null != originalNode) {
        log.info("Removing Prexisting Node Id " + user.getId() + " from the index " + USERS_INDEX);
        usersIndex.remove(originalNode, User.ID, user.getId());
      }

      log.info("Inserting User Id " + user.getId() + " into the index " + USERS_INDEX);

      usersIndex.add(node, User.ID, user.getId());

      tx.success();

    } finally {

      tx.finish();
    }
  }

  public int connectUsers(final String userId1, final String userId2) {

    Transaction tx = graphDb.beginTx();

    int returnStatus = 0;

    try {

      Index<Node> usersIndex = this.graphDb.index().forNodes(USERS_INDEX);

      Node node1 = usersIndex.get(User.ID, userId1).getSingle();

      Node node2 = usersIndex.get(User.ID, userId2).getSingle();

      if (node1 != null && node2 != null) {

        node1.createRelationshipTo(node2, UserRelationshipTypes.CONNECTED_TO);
        node2.createRelationshipTo(node1, UserRelationshipTypes.CONNECTED_TO);

        tx.success();

      } else {

        tx.failure();

        returnStatus = 1;
      }


    } finally {

      tx.finish();
    }

    return returnStatus;
  }

  public List<User> getUserConnections(final String userId) {

    Index<Node> usersIndex = this.graphDb.index().forNodes(USERS_INDEX);

    List<User> connections = new ArrayList<User>();

    Node start = usersIndex.get(User.ID, userId).getSingle();

    if (start == null ) {
      return null;
    }

    Iterable <Relationship> relationships = start.getRelationships(UserRelationshipTypes.CONNECTED_TO, Direction.BOTH);

    for(Relationship relationship : relationships) {

       Node endNode = relationship.getEndNode();

       User user = new User();

       user.setId((String) endNode.getProperty(User.ID));
       user.setFname((String) endNode.getProperty(User.FNAME));
       user.setLname((String) endNode.getProperty(User.LNAME));

       connections.add(user);
    }

    return connections;
  }

  public String getDbPath() {
    return dbPath;
  }

  public void setDbPath(String dbPath) {

    this.dbPath = dbPath;

    log.info("Neo4j DB PATH Set to " + this.dbPath);

    this.graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);

    this.graphDb.index().forNodes(USERS_INDEX);

    log.info("Index " + USERS_INDEX + " has been initialized");

    // Ensures that the database is shutdown properly
    this.registerShutdownHook();
  }

  private void registerShutdownHook() {

    log.info("Registering Shutdown Hook for Neo4j DB " + this.dbPath);

    Runtime.getRuntime().addShutdownHook( new Thread()
    {
        @Override
        public void run()
        {
            shutdown();
        }
    } );
  }

  private void shutdown() {

      log.info("Shutting Down Neo4j DB " + this.dbPath);

      graphDb.shutdown();
  }
}
