/**
 * Copyright © 2011 by
 * AVS GmbH (www.avs.de)
 * EVES Information Technology AG (www.eves-it.de)
 * ® All rights reserved.
 */
package com.concretepage.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;
import com.concretepage.service.Status;

/**
 * AVS_MAPPIS<br>
 * de.avs.mappis.integration.persistence.partnerfirma PartnerfirmaDAOImpl.java <br>
 * DAO zum Partnerfirma-Entity
 * 
 * @author Ludwig Leuschner, AVS GmbH<br>
 *         Modifikations: Iva Abadjieva, AVS GmbH, April 2012
 * @version 1.0<br>
 */

@Repository
public class PartnerfirmaDAOImpl implements PartnerfirmaDAO {

	public static final int PFIMPORTER_VS = 1;
	private static final Logger log = LoggerFactory
			.getLogger(PartnerfirmaDAOImpl.class);

	@Resource(name = "SESSIONFactory")
	// @Autowired
	// @Qualifier("SESSIONFactory")
	SessionFactory sessionFactory;

	// AUS PartnerfirmaNewDAOImpl
	@Transactional
	public void save(Partnerfirma partnerfirma) throws Exception {
		this.sessionFactory.getCurrentSession().save(partnerfirma);
	}

	@Transactional
	public void merge(Partnerfirma partnerfirma) throws Exception {
		this.sessionFactory.getCurrentSession().merge(partnerfirma);
	}

	@Transactional
	public void saveOrUpdate(Partnerfirma partnerfirma) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(partnerfirma);
	}

	@Transactional
	public void setzenPartnerfirmaKZGeloescht(Partnerfirma pf, boolean geloescht)
			throws Exception {
		String hql;
		Query query;
		hql = "update Partnerfirma p set p.kzgeloescht = ? where p.id= ? and p.pftyp = ?";
		query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setBoolean(0, geloescht);
		query.setLong(1, pf.getPartnerfirmaId());
		query.setLong(2, PFIMPORTER_VS);

		int result = query.executeUpdate();
		if (result == 0)
			throw new Exception("Update nicht moeglich.");

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<StatusEnum> getAlle() throws Exception {
//		Query query = this.sessionFactory.getCurrentSession().getNamedQuery(
//				"Partnerfirma.getAllePartnerfirmenProMandant");
//		query.setInteger(0, PFIMPORTER_VS);
//		query.setInteger(1, 38);
		
		String q = "select a from StatusEnum a";
		Query qu = this.sessionFactory.getCurrentSession().createQuery(q);
		List<StatusEnum> liste = qu.list();
		return liste;
	}

	// /////////////// Status
	@Transactional
	public void setzenStatusStop(Status status) throws Exception {
////////////////////////////////////////////////////////
//this.sessionFactory.getCurrentSession().get(Partnerfirma.class, 1, LockOptions.UPGRADE);
////////////////////////////////////////////////////////

		String hql;
		Query query;
		hql = "update StatusEnum p set p.status = ?, p.date =? where p.id = 21 and p.version = 1";
		query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, status);
		//LocalDate ld = LocalDate.now();
		Date ld = new Date();
		query.setParameter(1, ld);

		int result = query.executeUpdate();

		if (result == 0)
			throw new Exception("Update nicht moeglich.");
	}

}