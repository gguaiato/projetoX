package com.projetox.rest;

import com.google.gson.Gson;
import com.projetox.bd.model.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Utils {
	// HTTP 200 OK 
	// The request has succeeded.
	public static final String HTTP_200 = "HTTP 200";
	// HTTP 202 Created 
	// The request has been fulfilled and resulted in a new resource being created.
	public static final String HTTP_201 = "HTTP 201";
	// HTTP 202 Accepted 
	// The request has been accepted for processing, but the processing has not been completed.
	public static final String HTTP_202 = "HTTP 202";
	// HTTP 204 No Content
	// The server has fulfilled the request but does not need to return an entity-body.
	public static final String HTTP_204 = "HTTP 204";
	// HTTP 404 Not Found
	// The server has not found anything matching the Request-URI.
	public static final String HTTP_404 = "HTTP 404";
	
	private static final String GRAPH_FB_URL = "http://graph.facebook.com/";
	
	/**
	 * Faz uma requisição ao Facebook e cria o objeto {@code User} com as informações recebidas
	 * @param facebookId FacebookID do usuário do Facebook
	 * @return Objeto {@code User} ou {@code null} caso não exista usuário no Facebook com FacebookID passado.
	 */
	public static User getUserFromFacebookId(String facebookId){
		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(GRAPH_FB_URL + facebookId);
 
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
 
		User user = null;
		if (response.getStatus() == 200) {
			String json = response.getEntity(String.class);
			user = new Gson().fromJson(json, User.class);
		}
		return user;
	}
	
	public static void main(String[] args) {
		System.out.println(getUserFromFacebookId("100007w710667474").getName());
	}
}
