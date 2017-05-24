package com.john.test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-base.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@TransactionConfiguration(transactionManager = "transactionManager")
public class BaseTestWithDB extends AbstractTransactionalJUnit4SpringContextTests {
	protected final static Logger log = LoggerFactory.getLogger(BaseTestWithDB.class);
	
	@Override  
    @Resource(name = "dynamicDataSource")  
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    } 
}
