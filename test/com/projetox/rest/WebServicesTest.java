package com.projetox.rest;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import com.jayway.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.RestAssured.expect;
import static com.projetox.rest.Utils.*;

public class WebServicesTest {

	private static final String FACEBOOK_ID_TEST = "100007710667474";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		RestAssured.basePath = "http://localhost:8080/projetoX";
	}

	@Test
	public void testWebServices() {
		testPost();
		testGet();
		testDelete();
		testGetDeleted();
	}

	
	public void testPost() {
		expect().given().param("facebookId", FACEBOOK_ID_TEST).when()
				.post("/person").then().body(containsString(HTTP_201));
		expect().given().param("facebookId", FACEBOOK_ID_TEST).when()
				.post("/person").then().body(containsString(HTTP_202));
	}

	public void testGet() {
		expect().given().when().get("/person").then()
				.body(containsString(FACEBOOK_ID_TEST));
	}

	public void testDelete() {
		expect().given().when().delete("/person/" + FACEBOOK_ID_TEST).then()
				.body(containsString(HTTP_204));
		expect().given().when().delete("/person/" + FACEBOOK_ID_TEST).then()
				.body(containsString(HTTP_404));
	}

	private void testGetDeleted() {
		expect().given().when().get("/person").then()
		.body("facebookId", equalTo(FACEBOOK_ID_TEST));
	}
}
