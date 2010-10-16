package com.treflex.orduremap;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Service.GDataRequest;
import com.google.gdata.client.Service.GDataRequest.RequestType;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ContentType;
import com.google.gdata.util.ServiceException;
import com.treflex.orduremap.model.Ordure;

@Service
public class FusionTableHelper {
	private static final String SERVICE_URL = "http://www.google.com/fusiontables/api/query";
	private static final Logger LOGGER = Logger.getLogger("Fusion helper");

	public void setToken(final String token) throws AuthenticationException {

	}

	public void insert(final Ordure ordure) throws IOException, ServiceException {
		final GoogleService service = new GoogleService("fusiontables", "orduremap");
		service.setUserCredentials("global.garbage.map@gmail.com", "o1r9d8u3re", ClientLoginAccountType.GOOGLE);
		LOGGER.info("Insertion de " + ordure + " dans fusion tables");
		final String query = "insert into 256513 (User,Location,Tags,OrdureId) VALUES ('" + ordure.getReporter() + "','"
				+ ordure.getPosition() + "','" + ordure.getTags() + "','" + ordure.getKey().getId() + "')";
		LOGGER.info("Query : " + query);

		final URL url = new URL(SERVICE_URL);
		final GDataRequest request = service.getRequestFactory().getRequest(RequestType.INSERT, url,
				new ContentType("application/x-www-form-urlencoded"));
		final OutputStreamWriter writer = new OutputStreamWriter(request.getRequestStream());
		writer.append("sql=" + URLEncoder.encode(query, "UTF-8"));
		writer.flush();
		request.execute();
		LOGGER.info("Insertion de {} a été terminé avec succès" + ordure.toString());
	}
}
