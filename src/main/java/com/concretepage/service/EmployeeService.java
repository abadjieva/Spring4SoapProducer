package com.concretepage.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;

public interface EmployeeService {

	//@PreAuthorize("hasRole('DBA')")
	List<StatusEnum> findAlle();
	
	public void setzenStatusStop();

}
