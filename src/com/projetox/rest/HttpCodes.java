package com.projetox.rest;

public enum HttpCodes {

	// HTTP 200 OK
	// The request has succeeded.
	HTTP_200(200),
	// HTTP 202 Created
	// The request has been fulfilled and resulted in a new resource being
	// created.
	HTTP_201(201),
	// HTTP 202 Accepted
	// The request has been accepted for processing, but the processing has not
	// been completed.
	HTTP_202(202),
	// HTTP 204 No Content
	// The server has fulfilled the request but does not need to return an
	// entity-body.
	HTTP_204(204),
	// HTTP 404 Not Found
	// The server has not found anything matching the Request-URI.
	HTTP_404(404);

	private int code;

	private HttpCodes(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return super.toString().replace("_", " ");
	}
}
