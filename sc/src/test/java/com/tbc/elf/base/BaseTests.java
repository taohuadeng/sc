package com.tbc.elf.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:/spring-config/spring-servlet.xml",
        "classpath:/spring-config/spring-context.xml", "classpath:/ehcache.xml"})
public class BaseTests extends AbstractTransactionalJUnit4SpringContextTests {
}
