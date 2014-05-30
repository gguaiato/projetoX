package com.projetox;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.google.gson.Gson;
import com.projetox.bd.UserRepository;
import com.projetox.bd.model.User;

@Path("/person")
public class WebServices {
	
	// HTTP 200 OK 
	// The request has succeeded.
	private static final String HTTP_200 = "HTTP 200";
	// HTTP 202 Created 
	// The request has been fulfilled and resulted in a new resource being created.
	private static final String HTTP_201 = "HTTP 201";
	// HTTP 202 Accepted 
	// The request has been accepted for processing, but the processing has not been completed.
	private static final String HTTP_202 = "HTTP 202";
	// HTTP 204 No Content
	// The server has fulfilled the request but does not need to return an entity-body.
	private static final String HTTP_204 = "HTTP 204";
	// HTTP 404 Not Found
	// The server has not found anything matching the Request-URI.
	private static final String HTTP_404 = "HTTP 404";
	
	private static UserRepository userRepository = UserRepository.instance();
	
	@POST
	@Produces("text/plain")
	public String includeUser(@FormParam("facebookId") String facebookId, @Context HttpServletRequest httpRequest) {
		User user = Utils.getUserFromFacebookId(facebookId);
		if (user != null) {
			boolean included = userRepository.insert(user);
			if (included) 
				return HTTP_201;
		}
		return HTTP_202;
	}
	
	@GET
	@Produces("text/plain")
	public String listUsers(@QueryParam("limit") int limitQtd) {
		List<User> users = userRepository.list(limitQtd);
		String json = new Gson().toJson(users);
		String response = HTTP_200 + "\n" + json;
		return response;
	}

	@DELETE
	@Path("/{facebookId}")
	@Produces("text/plain")
	public String deleteUser(@PathParam("facebookId") String facebookId) {
		boolean deleted = userRepository.delete(facebookId);
		if (deleted) 
			return HTTP_204;
		return HTTP_404;
	}
}
