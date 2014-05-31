package com.projetox.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projetox.bd.UserRepository;
import com.projetox.bd.model.User;
import com.sun.jersey.multipart.FormDataParam;

import static com.projetox.rest.HttpCodes.*;

@Path("/person")
public class WebServices {
	
	private static Logger logger = Logger.getLogger(WebServices.class);
	private static UserRepository userRepository = UserRepository.instance();
	private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response includeUser(@FormDataParam("facebookId") String facebookId) {
		logger.info("POST /person params: " + "facebookId=" + facebookId);
		User user = Utils.getUserFromFacebookId(facebookId);
		if (user != null) {
			boolean included = userRepository.insert(user);
			if (included) 
				return Response.status(HTTP_201.getCode()).entity(HTTP_201.toString()).build();
		}
		return Response.status(HTTP_202.getCode()).entity(HTTP_202.toString()).build();
	}
	
	@GET
	public Response listUsers(@QueryParam("limit") int limitQtd) {
		logger.info("GET /person params: " + "limitQtd=" + limitQtd);
		List<User> users = userRepository.list(limitQtd);
		String json = gson.toJson(users);
		String response = HTTP_200.toString() + "\n" + json;
		return Response.status(HTTP_200.getCode()).entity(response).build();
	}

	@DELETE
	@Path("/{facebookId}")
	public Response deleteUser(@PathParam("facebookId") String facebookId) {
		logger.info("DELETE /person params: " + "facebookId=" + facebookId);
		boolean deleted = userRepository.delete(facebookId);
		if (deleted) 
			return Response.status(HTTP_204.getCode()).entity(HTTP_204.toString()).build();
		return Response.status(HTTP_404.getCode()).entity(HTTP_404.toString()).build();
	}
}
