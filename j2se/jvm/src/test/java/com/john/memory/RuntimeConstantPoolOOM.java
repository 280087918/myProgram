package com.john.memory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 方法区内存溢出(使用String.intern()方法在运行时常量池上创建对象)
 * 	这个没模拟出来，根据资料上说，这儿是可以抛出PerGem Space:Out Of Memory异常的
 *  资料上说了，1.6可以模拟出来，1.7模拟不出来，没说原因
 * @author Administrator
 * vm Args:-XX:PermSize=5M -XX:MaxPermSize=5M
 */
public class RuntimeConstantPoolOOM {
	public static void main(String[] args) {
		List<String> strList = new ArrayList<String>();
		int i = 1;
		while(true) {
			i = i + 1;
			System.out.println(i);
			strList.add(String.valueOf(i).intern());
		}
	}

	/**
	 * 这里验证了一个问题：
	 * 	java可能是作为一个保留字，已经在运行时常量池里面了
	 *  【intern()会返回首次出现在常量池中的实例引用】，kappa toString完了然后intern()是相当于在常量池中是首次出现，所以记录的是kappa这个字符串的堆引用
	 *  而java之前已经出现过了,所以不符合这个规定，也可以说这种情况下，常量池和Heap中都有一个java对象
	 */
	@Test
	public void compare() {
		String str1=new StringBuilder("ka").append("ppa").toString();
		System.out.println(str1.intern()==str1);
		
		String str2=new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern()==str2);
	}
}
