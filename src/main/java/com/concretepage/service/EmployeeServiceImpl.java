package com.concretepage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concretepage.dao.PartnerfirmaDAO;
import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService  {

	@Autowired
	private PartnerfirmaDAO dao;
	List<StatusEnum> liste=null;

	public List<StatusEnum> findAlle(){
		
		try{
		 liste = dao.getAlle();
			if(liste==null){
				StatusEnum ste = new StatusEnum();
				ste.setStatus(Status.STOP);
				liste = new ArrayList<StatusEnum>();
				liste.add(ste);
			}
		 
		}catch(Exception e){
			throw new RuntimeException(e);
		}

		return liste;
	}
	
	public void setzenStatusStop(){
		try{
			Status status = Status.STOP;
			dao.setzenStatusStop(status);
			
		}catch(final Exception e){
			throw new RuntimeException(e);
		}
	}

}
