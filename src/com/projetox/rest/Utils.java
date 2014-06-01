package com.projetox.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.projetox.bd.model.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Utils {

	private static final String GRAPH_FB_URL = "http://graph.facebook.com/";

	private static final String FACEBOOK_ID_FIELD_NAME = "\"facebookId\"";
	private static final String JSON_FACEBOOK_ID_FIELD_NAME = "\"id\"";

	private static final Gson gson = new Gson();

	/**
	 * Faz uma requisição ao Facebook e cria o objeto {@code User} com as
	 * informações recebidas
	 * 
	 * @param facebookId
	 *            FacebookID do usuário do Facebook
	 * @return Objeto {@code User} ou {@code null} caso não exista usuário no
	 *         Facebook com FacebookID passado.
	 */
	public static User getUserFromFacebookId(String facebookId) {
		Client client = Client.create();
		WebResource webResource = client.resource(GRAPH_FB_URL + facebookId);
		ClientResponse response = webResource.accept("application/json").get(
				ClientResponse.class);

		User user = null;
		if (response.getStatus() == Status.OK.getStatusCode()) {
			String json = response.getEntity(String.class).replace(
					JSON_FACEBOOK_ID_FIELD_NAME, FACEBOOK_ID_FIELD_NAME);
			user = gson.fromJson(json, User.class);
		}
		return user;
	}

	public static Response buildSimpleResponse(Status status) {
		return Response.status(status.getStatusCode()).build();
	}

	public static Response buildTextResponse(Status status,
			String responseText) {
		return Response.status(status.getStatusCode())
				.entity(responseText).build();
	}

}
