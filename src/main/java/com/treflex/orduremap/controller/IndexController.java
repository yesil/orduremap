package com.treflex.orduremap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
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
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
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

	@RequestMapping(value = "/ordures/{ordureId}/{size}", method = RequestMethod.GET)
	public void findOrdure(@PathVariable String ordureId, @PathVariable String size, HttpServletResponse response) throws IOException {
		Cache cache = null;
		try {
			cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
		} catch (CacheException e) {
			LOGGER.error("Cache problem", e);
		}
		byte[] imageData = null;
		String cacheKey = ordureId + size;
		if (!cache.containsKey(cacheKey)) {
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			final Key key = KeyFactory.createKey("Ordure", Long.parseLong(ordureId));
			final Ordure ordure = ordureDao.find(key);
			if (ordure != null) {
				imageData = ordure.getPhoto().getBytes();
				if ("small".equals(size)) {
					Image oldImage = ImagesServiceFactory.makeImage(imageData);
					Transform resize = ImagesServiceFactory.makeResize(256, 256);
					Transform lucky = ImagesServiceFactory.makeImFeelingLucky();
					ArrayList<Transform> transforms = new ArrayList<Transform>();
					transforms.add(lucky);
					transforms.add(resize);
					Transform composite = ImagesServiceFactory.makeCompositeTransform(transforms);
					Image newImage = imagesService.applyTransform(composite, oldImage);
					imageData = newImage.getImageData();
				} else {

				}
			}
			cache.put(cacheKey, imageData);
		} else
			imageData = (byte[]) cache.get(cacheKey);
		response.setContentType("image/jpeg");
		response.getOutputStream().write(imageData);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
}