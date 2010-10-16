package com.treflex.orduremap.config;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.stereotype.Service;

@Service
public final class PMF {
	private static final PersistenceManagerFactory PMFACTORY = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return PMFACTORY;
	}
}