package com.projetox.rest;

import static com.jayway.restassured.RestAssured.expect;
import static com.projetox.rest.HttpCodes.HTTP_200;
import static com.projetox.rest.HttpCodes.HTTP_201;
import static com.projetox.rest.HttpCodes.HTTP_202;
import static com.projetox.rest.HttpCodes.HTTP_204;
import static com.projetox.rest.HttpCodes.HTTP_404;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

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
		expect().statusCode(HTTP_201.getCode()).given()
				.param("facebookId", FACEBOOK_ID_TEST).when().post("/person")
				.then().body(containsString(HTTP_201.toString()));
		expect().statusCode(HTTP_202.getCode()).given()
				.param("facebookId", FACEBOOK_ID_TEST).when().post("/person")
				.then().body(containsString(HTTP_202.toString()));
	}

	public void testGet() {
		expect().statusCode(HTTP_200.getCode()).given().when().get("/person")
				.then().body("facebookId", equalTo(FACEBOOK_ID_TEST))
				.body(containsString(HTTP_200.toString()));
	}

	public void testDelete() {
		expect().statusCode(HTTP_204.getCode()).given().when()
				.delete("/person/" + FACEBOOK_ID_TEST).then()
				.body(containsString(HTTP_204.toString()));
		expect().statusCode(HTTP_404.getCode()).given().when()
				.delete("/person/" + FACEBOOK_ID_TEST).then()
				.body(containsString(HTTP_404.toString()));
	}

	private void testGetDeleted() {
		expect().statusCode(HTTP_200.getCode()).given().when().get("/person")
				.then().body("facebookId", equalTo(FACEBOOK_ID_TEST));
	}
}
