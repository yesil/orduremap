package com.treflex.orduremap.dao;

import javax.jdo.PersistenceManager;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;
import com.treflex.orduremap.config.PMF;
import com.treflex.orduremap.model.Ordure;

@Repository
public class OrdureDao {
	private static final Logger LOGGER = Logger.getLogger("Ordure DAO");

	public void save(final Ordure ordure) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(ordure);
		LOGGER.info("Sauvegarde d'une nouvelle " + ordure.toString());
	}

	public Ordure find(final Key key) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final Ordure found = pm.getObjectById(Ordure.class, key);
		LOGGER.info("Une ordure " + found.toString() + " trouvée pour la clé " + found.toString());
		return found;
	}
}
