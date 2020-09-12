package com.concretepage.service;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ContextLoader;

import com.concretepage.AppConfig;
import com.concretepage.StudentUtility;
import com.concretepage.dao.PartnerfirmaDAO;
import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;
import com.concretepage.soap.EinStudent;

//Load Spring contexte

//@ActiveProfiles("testing")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigWebContextLoader.class)
// public class ServiceTest extends AbstractJUnit4SpringContextTests{
public class ServiceTest {

	private final Logger log= Logger.getLogger(getClass().getName());
	//
	
	@Autowired
	ApplicationContext appCont;
		
	@Autowired
	protected PartnerfirmaDAO partnerDao;
	
	@Resource(name="employeeService")
	EmployeeService service;
	
	private static int a=4;

	@BeforeClass
	public static void testAllg(){
		a = 5;
	
	}
	//@Ignore("Before ignoriert")
	@Before
	public void vorTest(){
		a = 5;
	}
	
	@Test
	public void testProcess1() {
		//int a = 6;
		assertTrue("NICHT TRUE", a == 5);
		
		StudentUtility su =appCont.getBean(StudentUtility.class);
		EinStudent st=su.getStudent(2);
		System.out.println("EGN= "+st.getEgn());
		
		}

	//@Ignore
	@Test
	public void testProcess2() {
		List<StatusEnum> lis = null;
		try {
			lis = partnerDao.getAlle();
			//lis=service.findAlle();
			System.out.println(lis.size());
			assertNotNull("LISTE IST not LEER!", lis);
			assertThat(lis, not(hasSize(2)));
		} catch (Exception e) {

		}
	}

	
}