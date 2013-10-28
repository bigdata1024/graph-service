package com.israelekpo.strata.service.graph.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;


public class GraphServiceExceptionMapper implements ExceptionMapper<Throwable> {

  private static final Logger log = Logger.getLogger(GraphServiceExceptionMapper.class);

  @Override
  public Response toResponse(Throwable exception) {

    log.error(exception);

    return Response.status(500)
        .entity(exception)
        .build();
  }

}
