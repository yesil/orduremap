package com.treflex.orduremap.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	private static final Logger LOGGER = Logger.getLogger("Orduremap");

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public void map() {
		LOGGER.info("Accès à la carte des ordures");
	}
}