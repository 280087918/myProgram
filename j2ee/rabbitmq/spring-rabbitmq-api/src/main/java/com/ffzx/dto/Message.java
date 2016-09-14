package com.ffzx.dto;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


public class Message implements Delayed, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1824414079104860490L;
	
	private String id;

	private String name;
	
	private Integer age;
	
	private long activeTime;//执行时间
	
	public Message() {
		super();
	}
	
	public Message(String id, String name, Integer age, long activeTime) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.activeTime = activeTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	@Override
	public String toString() {
		return "Message [name=" + name + ", age=" + age + "]";
	}

	public int compareTo(Delayed delayed) {
		Message msg = (Message)delayed;  
        return Integer.valueOf(this.id)>Integer.valueOf(msg.id)?1:(Integer.valueOf(this.id)<Integer.valueOf(msg.id)?-1:0);
	}

	public long getDelay(TimeUnit unit) {
		return unit.convert(this.activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}
}
