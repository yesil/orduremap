package com.treflex.orduremap.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.treflex.orduremap.dao.OrdureDao;
import com.treflex.orduremap.model.Ordure;

@Controller
public class IndexController {
	private static final Logger LOGGER = Logger.getLogger("Orduremap");
	@Autowired
	OrdureDao ordureDao;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public void map() {
		LOGGER.info("Accès à la carte des ordures");
	}

	@RequestMapping(value="/ordures/{ordureId}", method=RequestMethod.GET)
	public void findOrdure(@PathVariable String ordureId, HttpServletResponse response) throws IOException {
		final Key key = KeyFactory.createKey("Ordure", Long.parseLong(ordureId));
		final Ordure ordure = ordureDao.find(key);
		if (ordure != null) {
			response.setContentType("image/jpeg");
			response.getOutputStream().write(ordure.getPhoto().getBytes());
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
}