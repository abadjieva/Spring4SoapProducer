package com.concretepage.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.in;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;

import com.concretepage.AppConfig;
import com.concretepage.ConfigBean;
import com.concretepage.ExpBean;
import com.concretepage.StudentUtility;
import com.concretepage.TestObj;
import com.concretepage.soap.EinStudent;
import com.concretepage.dao.PartnerfirmaDAO;
import com.concretepage.dao.PartnerfirmaDAOImpl;
import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;
import com.concretepage.service.EmployeeServiceImpl;

//Load Spring contexte

//@ActiveProfiles("testing")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AppConfig.class)

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration ()
//@ContextConfiguration(classes = { AppConfig.class})
@ContextConfiguration(classes = { AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
// public class ServiceTest extends AbstractJUnit4SpringContextTests{
public class ServiceTestMock {
	
	private static final Logger log = LoggerFactory
			.getLogger(ServiceTestMock.class);
	
	private static String temp;
	private String t;
	
	
	@Autowired
	ApplicationContext appCont;
	
	@Mock
	private PartnerfirmaDAO pfDaoMock;
	
	@InjectMocks
	@Autowired
	private EmployeeServiceImpl emplService;
	//
	
	@BeforeClass
	public static void initialize(){
		temp="iva";
		
	}

	//@Ignore
	@Test
	public void testProcess1() {
		//System.setProperty("file.encoding", "utf-8");
		
		AbstractApplicationContext cont  = new AnnotationConfigApplicationContext(StudentUtility.class, AppConfig.class, ConfigBean.class);
		//AnnotationConfigApplicationContext cont  = new AnnotationConfigApplicationContext();
		//cont.register( AppConfig.class);
		//cont.refresh();
		StudentUtility su = cont.getBean(StudentUtility.class);
		EinStudent st = su.getStudent(1);
		System.out.println("EGN= "+st.getEgn());
		
		ExpBean eb = cont.getBean(ExpBean.class);
		ExpBean eb1 = cont.getBean(ExpBean.class);
		TestObj passwObj= (TestObj)cont.getBean("Find");
		
		System.out.println("NAM1E= "+passwObj.getProp()+ " "+ eb.getTemp()+ " "+eb.getIntTemp());
		eb.setTemp("Iva Aba");
		
		System.out.println("NAME1= "+eb.getTemp());
		System.out.println("NAME2= "+eb1.getTemp());
		
		cont.destroy();
		System.out.println("----testEnde");
//		StudentUtility su1 =appCont.getBean(StudentUtility.class);
//		EinStudent st1=su1.getStudent(2);
//		System.out.println("EGN= "+st1.getEgn());
		
		System.out.println("Default Locale:   " + Locale.getDefault());
		System.out.println("Default Charset:  " + Charset.defaultCharset());
		System.out.println("file.encoding;    "
				+ System.getProperty("file.encoding"));
		
		
		//System.out.println("console.encoding;    "+System.getProperty("console.encoding"));
		System.out.println("os.name: " + System.getProperty("os.name"));
		System.out.println("lineSeparator    " + System.lineSeparator());
		
		//try (InputStream input = ServiceTestMock.class.getClassLoader().getResourceAsStream("application.properties")) {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
			InputStreamReader rt = new InputStreamReader(input,"utf-8");
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(rt);
            
            
            System.out.println("jdbc.url = "+prop.getProperty("jdbc.url"));
            String propNew = prop.getProperty("jdbc.url");
            String propNew1 = new String(propNew.getBytes("utf-8"));
            System.out.println("jdbc.url-new = "+propNew1);
            
		}catch(Exception e){
			e.printStackTrace();
		}
		String str = "\u0030\uFFFD\u00F5häßlich&таблици\u0030\u0444\ud83d\udf73";
		System.out.println("STR= " + str);
		
		try{
		String newStr = new String(str.getBytes("utf-8"));
		
		System.out.println("STR-NEW= " + newStr);
		
		byte[] bt = newStr.getBytes();
			
		//ist möglich	
		//byte[] bt = 	str.getBytes(StandardCharsets.UTF_8);
		
		FileOutputStream fo = new FileOutputStream("Dokumente\\experiment");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(bt);
		baos.writeTo(fo);
		
		}catch(Exception e){
			
		}
	
		assertTrue("NICHT TRUE", temp == "iva");
		
		System.out.println("\n----testEnde1\n");
	}

	
		// @Ignore
	@Test
	public void testProcess3() {
		log.info("ServiceTestMock 3");
		//System.out.println("----ServiceTestMock 3");
		//pfDaoMock = Mockito.mock(PartnerfirmaDAOImpl.class);
		//List<String> lis2 =Arrays.asList("Iva", "Olga");
		String[] arr = new String[]{"Iva", "Olga"};
		List<String> lis1 = Arrays.asList(arr);
		
		try {
			Mockito.verifyZeroInteractions(pfDaoMock);
			
			Mockito.when(pfDaoMock.getAlle()).thenReturn(
					null);
			List <StatusEnum> liste = pfDaoMock.getAlle(); //1.
			assertEquals("nicht gleich",liste,null);
			List <StatusEnum> liste1 = emplService.findAlle();  //2.
			for(StatusEnum e :liste1){
				t=e.getStatus().toString();
				System.out.println("---- "+ t);
			}
			
			assertNotNull("LISTE LEER!", liste1);
			
			assertEquals("nicht wie erwartet",t, Status.STOP.toString());
			
			liste1 = emplService.findAlle(); //3. nur zum Test des Zählens des Aufrufs mit verify
			
			log.info("\n----ServiceTestMock 31");
			Mockito.verify(pfDaoMock, times(3)).getAlle();
			log.info("\n----ServiceTestMock 32");
			
			
			Mockito.verifyNoMoreInteractions(pfDaoMock);
			
			assertThat(Boolean.TRUE, is(Boolean.TRUE));
			assertThat("Nicht Drin", "Iva", in(lis1));
			log.info("\n----ServiceTestMock 3-- ENDE");	
			
		} catch (Exception e) {

		}
	}
	

	
	@Ignore
	@Test
	public void testVerify(){
		
		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);
		mockedList.add("one");
		mockedList.add("mein");
		mockedList.clear();
		Mockito.verify(mockedList).add("one");
		Mockito.verify(mockedList).clear();
		Mockito.verify(mockedList, never()).add("iva");
		
	}
	

}