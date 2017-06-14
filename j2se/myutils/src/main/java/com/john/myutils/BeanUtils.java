package com.john.myutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.john.vo.NormalUser;
import com.john.vo.UnderLineUser;

/**
 * 
 * @author Jonathan.Chang
 *	主要是为了解决Jumper现场项目bean的属性命名不规范的问题
 */
public class BeanUtils {
	
	/**
	 * 从下划线_属性copy到大写字母开头的属性中
	 * 	比如user_id -> userId
	 * @param source
	 * @param target
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void copyFromUnderLine(Object source, Object target) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] targetMethods = target.getClass().getMethods();//目标bean所有方法
		
		List<String> sourceMethodsStr = new ArrayList<String>();//源bean方法名集合(带下划线的)
		for(Method sourceMethod : source.getClass().getMethods()) {
			if(sourceMethod.getName().contains("set")) {
				sourceMethodsStr.add(sourceMethod.getName());
			}
		}
		
		for(Method method : targetMethods) {
			if(method.getName().contains("set")) {
				//如果方法名相同，那么就直接复制
				if(sourceMethodsStr.contains(method.getName())) {
					method.invoke(target, source.getClass().getMethod(method.getName().replace("set", "get")).invoke(source));
				} else {//这边默认处理下划线的域
					String sourceMethodNameTmp = null;
					for(String sourceMethod : sourceMethodsStr) {
						if(sourceMethod.indexOf("_") > 0) {
							sourceMethodNameTmp = sourceMethod.substring(0, sourceMethod.indexOf("_")) 
									+ sourceMethod.substring(sourceMethod.indexOf("_") + 1, sourceMethod.indexOf("_") + 2).toUpperCase()
									+ sourceMethod.substring(sourceMethod.indexOf("_") + 2);
							if(method.getName().equals(sourceMethodNameTmp)) {
								method.invoke(target, source.getClass().getMethod(sourceMethod.replace("set", "get")).invoke(source));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 以大写字母分割的属性复制到下划线分割的属性中
	 * 	比如userId -> user_id
	 * @param source
	 * @param target
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void copyToUnderLine(Object source, Object target) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method[] targetMethods = target.getClass().getMethods();//目标bean所有方法(带下划线的)
		
		List<String> sourceMethodsStr = new ArrayList<String>();//源bean方法名集合
		for(Method sourceMethod : source.getClass().getMethods()) {
			if(sourceMethod.getName().contains("set")) {
				sourceMethodsStr.add(sourceMethod.getName());
			}
		}
		
		for(Method method : targetMethods) {
			if(method.getName().contains("set")) {
				//如果方法名相同，那么就直接复制
				if(sourceMethodsStr.contains(method.getName())) {
					method.invoke(target, source.getClass().getMethod(method.getName().replace("set", "get")).invoke(source));
				} else {//这边默认处理下划线的域
					if(method.getName().indexOf("_") > 0) {
						String methodName = method.getName();
						String methodNameTmp = methodName.substring(0, methodName.indexOf("_"))
								+ methodName.substring(methodName.indexOf("_") + 1, methodName.indexOf("_") + 2).toUpperCase()
								+ methodName.substring(methodName.indexOf("_") + 2);
						if(sourceMethodsStr.contains(methodNameTmp)) {
							method.invoke(target, source.getClass().getMethod(methodNameTmp.replace("set", "get")).invoke(source));
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		/*UnderLineUser underLine = new UnderLineUser();
		underLine.setUser_id(001);
		underLine.setUser_name("张三");
		underLine.setScore(16.0f);
		NormalUser normalUser = new NormalUser();
		try {
			copyFromUnderLine(underLine, normalUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(normalUser);*/
		
		NormalUser normalUser = new NormalUser();
		normalUser.setUserId(002);
		normalUser.setUserName("李斯");
		normalUser.setScore(18.0f);
		UnderLineUser underLine = new UnderLineUser();
		try {
			copyToUnderLine(normalUser, underLine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(underLine);
	}
}
