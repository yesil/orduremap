package com.treflex.orduremap.indexer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.sanselan.ImageReadException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.treflex.orduremap.dao.OrdureDao;
import com.treflex.orduremap.model.Ordure;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GPSIndexerTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	@Autowired
	private GpsIndexer indexer;
	@Autowired
	OrdureDao ordureDao;

	// @Test
	public void mailMest() throws MessagingException, IOException, ImageReadException {
		final Properties props = new Properties();
		props.put("address", "imap.gmail.com");
		props.put("login", "global.garbage.map");
		props.put("password", "ggm19833979");
		indexer.index(props);
		Assert.assertTrue(true);
	}

	@Test
	public void fileTest() throws MessagingException, IOException, ImageReadException {
		final File file = new File("src/test/resources/gps-images/IMG_0269.jpg").getAbsoluteFile();
		indexer.index(new FileInputStream(file), file.getName(), "test", "=?ISO-8859-1?Q?ezgi_g=FCven=E7?= <ezgi.guvenc@gmail.com>",
				new Date());
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] imageBytes = new byte[4096];
		InputStream is = new FileInputStream(file);
		while (is.read(imageBytes) != -1) {
			bout.write(imageBytes);
		}
		Key key = KeyFactory.createKey("Ordure", 1);
		Ordure found = ordureDao.find(key);

		Assert.assertArrayEquals(bout.toByteArray(), found.getPhoto().getBytes());
		// Assert.assertEquals("ezgi.guvenc@gm ail.com", found.getReporter());
	}

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
}
