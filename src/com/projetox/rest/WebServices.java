package com.projetox.rest;

import static com.projetox.rest.Utils.buildSimpleResponse;
import static com.projetox.rest.Utils.buildTextResponse;
import static com.projetox.rest.Utils.getUserFromFacebookId;
import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projetox.bd.UserRepository;
import com.projetox.bd.model.User;
import com.sun.jersey.multipart.FormDataParam;


@Path("/person")
public class WebServices {
	
	private static Logger logger = Logger.getLogger(WebServices.class);
	private static UserRepository userRepository = UserRepository.instance();
	private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response includeUser(@FormDataParam("facebookId") String facebookId) {
		logger.info("POST /person params: " + "facebookId=" + facebookId);
		User user = getUserFromFacebookId(facebookId);
		if (user != null) {
			boolean included = userRepository.insert(user);
			if (included) 
				return buildSimpleResponse(CREATED);
		}
		return buildSimpleResponse(ACCEPTED);
	}
	

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listUsers(@QueryParam("limit") int limitQtd) {
		logger.info("GET /person params: " + "limitQtd=" + limitQtd);
		List<User> users = userRepository.list(limitQtd);
		String json = gson.toJson(users);
		return buildTextResponse(OK, json);
	}

	@DELETE
	@Path("/{facebookId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteUser(@PathParam("facebookId") String facebookId) {
		logger.info("DELETE /person params: " + "facebookId=" + facebookId);
		boolean deleted = userRepository.delete(facebookId);
		if (deleted) 
			return buildSimpleResponse(NO_CONTENT);
		return buildSimpleResponse(NOT_FOUND);
	}
}
