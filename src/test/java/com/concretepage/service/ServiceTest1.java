package com.concretepage.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.concretepage.AppConfig;
import com.concretepage.dao.PartnerfirmaDAO;
import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.collection.IsEmptyCollection.*;
import static org.hamcrest.collection.IsIn.*;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={AppConfig.class}, loader= AnnotationConfigWebContextLoader.class)
public class ServiceTest1 {
	
	@Resource
	PartnerfirmaDAO pfd;

	private static int param = 5;

	@BeforeClass
	public static void initialize(){
		param=6;
	}

	@Test
	public void vadidateTest1(){
		System.out.println("----- vadidateTest1");
		param=7;
		List<StatusEnum> list = null;
		String[] arr = new String[]{"Iva", "Olga"};
		List<String> lis1 = Arrays.asList(arr);

		try{
		list = pfd.getAlle();
		assertNotNull("list is NOT NULL", list);
		//hasItems both .and
		assertThat(list, not(hasSize(4))) ;
		assertThat("EMPTY", list, not(empty()));
		assertThat("Nicht Drin", "Iva", in(lis1));
		assertThat(list, is(not(nullValue())));
		assertThat(list.size(), is(not(0)));
		assertThat(Boolean.TRUE, is(Boolean.TRUE));
		assertEquals("nicht7", param, 7);
		
		}catch(Exception e){
			
		}
		
	}

}
