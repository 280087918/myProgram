package com.john.jmockit.utils;

import com.john.jmockit.entity.Commodity;

public class CommodityUtil {
	
	public static String assembleBrand(Commodity commodity) {
		System.out.println("assembleBrand ......");
		return commodity.getName() + commodity.getStock();
	}
}
