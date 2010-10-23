package com.treflex.orduremap.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.treflex.orduremap.indexer.GpsIndexer;

@Controller
public class FileUpoadController {
	@Autowired
	private GpsIndexer gpsIndexer;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(final @RequestParam("name") String name, final @RequestParam("latitude") String latitude,
			final @RequestParam("longitude") String longitude, final @RequestParam("tags") String tags,
			final @RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			gpsIndexer.index(file.getInputStream(), name, tags, "orduremap.anroid", new Date(), latitude + "," + longitude);
		}
		return "index";
	}

}