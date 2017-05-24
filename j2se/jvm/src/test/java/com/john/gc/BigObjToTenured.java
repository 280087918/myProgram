package com.john.gc;

import org.junit.Test;

/**
 * 大对象直接进入老年代
 * @author Administrator
 *	VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728 -XX:+UseSerialGC
 *		3145728 = 1024 * 1024 * 3 ,也就是3MB
 *	实验结果不一致，这个参数看起来没有什么作用，无论是哪个垃圾收集器，都会既占用新生代，亦占用老年代的空间
 *	(-XX:+UseConcMarkSweepGC)
 */
public class BigObjToTenured {
	private static final int _1MB=1024*1024;
	
	/**
	 * Heap
		 PSYoungGen      total 9216K, used 4540K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
		  eden space 8192K, 55% used [0x00000000ff600000,0x00000000ffa6f130,0x00000000ffe00000)
		  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
		  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
		 ParOldGen       total 10240K, used 4096K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
		  object space 10240K, 40% used [0x00000000fec00000,0x00000000ff000010,0x00000000ff600000)
		 PSPermGen       total 21504K, used 4819K [0x00000000f9a00000, 0x00000000faf00000, 0x00000000fec00000)
		  object space 21504K, 22% used [0x00000000f9a00000,0x00000000f9eb4fb8,0x00000000faf00000)
		从结果来看，貌似没有直接分配到老年代，而是新生代和老年代都占用了空间
	 */
	@Test
	public void testPretenureSizeThreshold() {
		byte[]allocation;
		allocation=new byte[4*_1MB];//直接分配在老年代中
	}
}
