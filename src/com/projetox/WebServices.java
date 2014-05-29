package com.projetox;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/rs")
public class WebServices {
	
	@GET
	@Produces("text/plain")
	public String test() {
		return "hi there!";
	}

	
}
