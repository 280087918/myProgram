package com.john.utils;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.NamedThreadLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource
		implements BeanPostProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

	public static final String MASTER_DATA_SOURCE = "MASTER";

	public static final String SLAVE_DATA_SOURCE = "SLAVE";

	private static final ThreadLocal<Object> currentLookupKey = new NamedThreadLocal<Object>("Current lookup key");
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (TransactionInterceptor.class.isInstance(bean)) {
			ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
			proxyFactoryBean.setInterfaces(new Class[]{MethodInterceptor.class});
			proxyFactoryBean.setTarget(bean);
			proxyFactoryBean.addAdvice(new TransactionInterceptorAdvice());
			proxyFactoryBean.setProxyTargetClass(true);
			return proxyFactoryBean.getObject();
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicRoutingDataSource.currentLookupKey.get();
	}

	/**
	 * 事务数据源决策者
	 * 
	 * @author ching
	 * 
	 */
	public class TransactionInterceptorAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			try {
				LOGGER.info("boolean:{}", TransactionSynchronizationManager.isSynchronizationActive());
				if (!TransactionSynchronizationManager.isSynchronizationActive()) {
					DynamicRoutingDataSource.currentLookupKey.set(MASTER_DATA_SOURCE);
					LOGGER.info("DynamicRoutingDataSource.currentLookupKey.set(MASTER_DATA_SOURCE):{}", DynamicRoutingDataSource.currentLookupKey.get());
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Current lookup key is '{}'", MASTER_DATA_SOURCE);
					}
				}
				return invocation.proceed();
			} finally {
				DynamicRoutingDataSource.currentLookupKey.remove();
			}
		}
	}
}
