package com.concretepage.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author abadjiei
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ServiceTest.class,
        ServiceTest1.class,
        ServiceTestMock.class
})
public class TestSuite {

}
