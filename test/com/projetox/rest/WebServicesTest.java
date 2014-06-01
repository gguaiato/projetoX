package com.projetox.rest;

import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static junit.framework.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class WebServicesTest extends JerseyTest {

	private static final String FACEBOOK_ID_TEST = "100007710667474";
	private static final String FACEBOOK_USERNAME_TEST = "renato.luizalabs";
	private static final String REST_BASE_URL = "http://localhost:8080/projetoX";
	private static final String REST_PERSON = "/person";

	private final WebResource webResource = client().resource(REST_BASE_URL);

	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}

	@Test
	public void testWebServices() {
		testPost();
		testGet();
		testDelete();
		testGetDeleted();
	}

	public void testPost() {
		FormDataMultiPart part = new FormDataMultiPart().field("facebookId",
				FACEBOOK_ID_TEST);
		ClientResponse response = webResource.path(REST_PERSON)
				.type(MediaType.MULTIPART_FORM_DATA_TYPE)
				.accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, part);
		
		String responseText = response.getEntity(String.class);
		
		Assert.assertEquals(CREATED.getStatusCode(), response.getStatus());
		
		response = webResource.path(REST_PERSON)
				.type(MediaType.MULTIPART_FORM_DATA_TYPE)
				.accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, part);
		responseText = response.getEntity(String.class);
		
		Assert.assertEquals(ACCEPTED.getStatusCode(), response.getStatus());
	}

	public void testGet() {
		ClientResponse response = webResource.path(REST_PERSON)
				.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		String responseText = response.getEntity(String.class);
		
		Assert.assertEquals(OK.getStatusCode(), response.getStatus());
		assertTrue(responseText.contains(FACEBOOK_ID_TEST));
		assertTrue(responseText.contains(FACEBOOK_USERNAME_TEST));
	}

	public void testDelete() {
		ClientResponse response = webResource.path(REST_PERSON + "/" + FACEBOOK_ID_TEST)
				.accept(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
		
		Assert.assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
		
		response = webResource.path(REST_PERSON + "/" + FACEBOOK_ID_TEST)
				.accept(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
		
		Assert.assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
	}

	private void testGetDeleted() {
		ClientResponse response = webResource.path(REST_PERSON)
				.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		String responseText = response.getEntity(String.class);
		
		Assert.assertEquals(OK.getStatusCode(), response.getStatus());
		assertTrue(!responseText.contains(FACEBOOK_ID_TEST));
		assertTrue(!responseText.contains(FACEBOOK_USERNAME_TEST));
	}
}
