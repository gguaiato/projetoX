package com.projetox.rest;

import static com.projetox.rest.Utils.HTTP_200;
import static com.projetox.rest.Utils.HTTP_201;
import static com.projetox.rest.Utils.HTTP_202;
import static com.projetox.rest.Utils.HTTP_204;
import static com.projetox.rest.Utils.HTTP_404;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.projetox.bd.UserRepository;
import com.projetox.bd.model.User;

@Path("/person")
public class WebServices {
	
	private static Logger logger = Logger.getLogger(WebServices.class);
	private static UserRepository userRepository = UserRepository.instance();
	
	@POST
	@Produces("text/plain")
	public String includeUser(@FormParam("facebookId") String facebookId, @Context HttpServletRequest httpRequest) {
		logger.info("POST /person params: " + "facebookId=" + facebookId);
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
		logger.info("GET /person params: " + "limitQtd=" + limitQtd);
		List<User> users = userRepository.list(limitQtd);
		String json = new Gson().toJson(users);
		String response = HTTP_200 + "\n" + json;
		return response;
	}

	@DELETE
	@Path("/{facebookId}")
	@Produces("text/plain")
	public String deleteUser(@PathParam("facebookId") String facebookId) {
		logger.info("DELETE /person params: " + "facebookId=" + facebookId);
		boolean deleted = userRepository.delete(facebookId);
		if (deleted) 
			return HTTP_204;
		return HTTP_404;
	}
}
