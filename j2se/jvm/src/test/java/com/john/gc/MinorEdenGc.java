package com.john.gc;

import org.junit.Test;

/**
 * 新生代(Minor)的Eden区GC
 * @author Administrator
 *	VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *	verbose监测哪方面的信息,这里verbose:gc就是监测gc相关的信息
 *		例java -verbose:class监测类的加载信息
 */
public class MinorEdenGc {
	private static final int _1MB=1024*1024;
	
	/**
	 * 这些结果是下面程序运行时的输出信息
	 * [GC [PSYoungGen: 6591K->1016K(9216K)] 6591K->3311K(19456K), 0.0243933 secs] [Times: user=0.00 sys=0.00, real=0.04 secs] 
		Heap
		 PSYoungGen      total 9216K, used 5574K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
		  eden space 8192K, 55% used [0x00000000ff600000,0x00000000ffa73b90,0x00000000ffe00000)
		  from space 1024K, 99% used [0x00000000ffe00000,0x00000000ffefe030,0x00000000fff00000)
		  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
		 ParOldGen       total 10240K, used 6391K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
		  object space 10240K, 62% used [0x00000000fec00000,0x00000000ff23dfd0,0x00000000ff600000)
		 PSPermGen       total 21504K, used 4822K [0x00000000f9a00000, 0x00000000faf00000, 0x00000000fec00000)
		  object space 21504K, 22% used [0x00000000f9a00000,0x00000000f9eb5bc0,0x00000000faf00000)
	 */
	@SuppressWarnings("unused")
	@Test
	public void testAllocation() {
		byte[] allocation1,allocation2,allocation3,allocation4;
		allocation1=new byte[2*_1MB];
		allocation2=new byte[2*_1MB];
		allocation3=new byte[2*_1MB];
		allocation4=new byte[4*_1MB];//出现一次Minor GC
	}
}
