package com.treflex.orduremap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gdata.util.AuthenticationException;
import com.treflex.orduremap.FusionTableHelper;

@Controller
public class GoogleController {
	@Autowired
	private FusionTableHelper ft;
	private static final Logger LOGGER = LoggerFactory.getLogger("Google authentication");

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public void admin() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			LOGGER.info("L'utilisateur {} accède à la page d'accueil", auth.getPrincipal().toString());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/fusion", method = RequestMethod.GET)
	public void setToken(final @RequestParam("token") String token) throws AuthenticationException {
		LOGGER.info("Reçeption d'un nouveau token {}", token);
		ft.setToken(token);
	}
}