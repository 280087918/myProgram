package com.john.beanpostprocessor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 这个类用来研究一下spring的BeanPostProcessor是干嘛的
 * @author zhang.hc
 * @date 2016年6月25日 上午10:36:56
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
	
	Logger log = LoggerFactory.getLogger(MyBeanPostProcessor.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		log.info("postProcessBeforeInitialization before......beanName:{}", beanName);
		
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setInterfaces(MethodInterceptor.class);
		proxyFactoryBean.setTarget(bean);
		proxyFactoryBean.addAdvice(new MyAdvice());
		proxyFactoryBean.setProxyTargetClass(true);
		return proxyFactoryBean.getObject();
		
		//return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		log.info("postProcessBeforeInitialization after......");
		return bean;
	}

	public class MyAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation mi) throws Throwable {
			if(mi.getMethod().isAnnotationPresent(Transactional.class)) {
				System.out.println("尼玛，有事务");
			} else {
				System.out.println("没有事务哦");
			}
			return mi.proceed();
		}
		
	}
}
