/**
 * Copyright © 2011 by
 * AVS GmbH (www.avs.de)
 * EVES Information Technology AG (www.eves-it.de)
 * ® All rights reserved.
 */
package com.concretepage.dao;

import java.util.List;

import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;
import com.concretepage.service.Status;

/**
 * AVS_MAPPIS<br>
 * de.avs.mappis.integration.persistence.partnerfirma PartnerfirmaDAO.java <br>
 * DAO-Interface zum PartnerfirmaDAOImpl
 * 
 * @author Ludwig Leuschner, AVS GmbH<br>
 *         Modifikations: Iva Abadjieva, AVS GmbH, April 2012
 * @version 1.0<br>
 */

public interface PartnerfirmaDAO{

	void save(Partnerfirma partnerfirma) throws Exception;

	void saveOrUpdate(Partnerfirma partnerfirma);

	
	void setzenPartnerfirmaKZGeloescht(Partnerfirma pf, boolean geloescht) throws Exception;

		
	void merge(Partnerfirma partnerfirma) throws Exception;
	
	List<StatusEnum> getAlle() throws Exception;
	
	void setzenStatusStop(Status status) throws Exception;

}