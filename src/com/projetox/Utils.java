package com.projetox;

import com.google.gson.Gson;
import com.projetox.bd.model.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Utils {
	
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
