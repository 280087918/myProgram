package com.john.car.vo;

/**
 * 车辆Vo
 * @author zhang.hc
 * @date 2016年4月8日 上午11:01:16
 */
public class CarVo {
	//id
	private String id;
	
	//品牌
	private String name;
	
	//型号
	private String model;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "CarVo [id=" + id + ", name=" + name + ", model=" + model + "]";
	}
}
