package io.lucci.bookshop.test.base;

import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import io.lucci.bookshop.application.PersistenceConfig;

@ActiveProfiles("dbtest")
@ContextConfiguration(
		classes = { PersistenceConfig.class }, 
		loader = AnnotationConfigContextLoader.class,
		initializers = ConfigFileApplicationContextInitializer.class)
public abstract class AbstractDbTest extends AbstractTransactionalJUnit4SpringContextTests {

}
