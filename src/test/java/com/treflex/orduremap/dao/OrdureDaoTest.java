package com.treflex.orduremap.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.treflex.orduremap.model.Ordure;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class OrdureDaoTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	@Autowired
	OrdureDao ordureDao;

	@Test
	public void saveOrdure() {
		Ordure ordure = new Ordure("40,40");
		ordure.setTags("plastique");
		ordure.setReporter("yesilturk@gmail.com");
		ordure.setDatePhoto(new Date());
		ordure.setDateReceive(new Date());
		ordureDao.save(ordure);
		Ordure found = ordureDao.find(ordure.getKey());
		assertEquals(ordure.getPosition(), found.getPosition());
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
