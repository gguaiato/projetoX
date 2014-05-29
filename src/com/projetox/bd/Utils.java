package com.projetox.bd;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Utils {
	
	private static final String GRAPH_FB_URL = "http://graph.facebook.com/";

	public static User getUserFromFacebookId(String facebookId) {
		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(GRAPH_FB_URL + facebookId);
 
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
 
		String json = response.getEntity(String.class);
		User user = null;
		try {
			user = new Gson().fromJson(json, User.class);
		} catch (JsonSyntaxException e) {
			// Ocorre somente em caso de erro interno do fb, usuario inexistente e talvez falta de internet
		}
		return user;
	}
}
