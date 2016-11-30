package com.john.task.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 主任务Vo
 * @author zhang.hc
 * @date 2016年11月30日 下午6:00:01
 */
@Data
@ToString
@NoArgsConstructor
public class TaskVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2980190628083214377L;
	
	//任务id
	private String id;
	
	//创建时间
	private Date ceateDate;
	
	//开始时间
	private Date beginDate;
	
	//结束时间
	private Date endDate;
	
	//总计天数
	private Integer totalDateCount;
	
	//已计天数
	private Integer alreadyDateCount;
	
	//每天可记录次数
	private Integer perDateCount;
	
	//创建用户
	private String createUser;
	
	//是否已删除，1：已删除，0:未删除
	private Integer deleteFlag;
}
