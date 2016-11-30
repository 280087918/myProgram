package com.john.taskDetail.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 签到明细
 * @author zhang.hc
 * @date 2016年11月30日 下午6:44:25
 */
@Data
@ToString
@NoArgsConstructor
public class TaskDetailVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8651075926813471910L;

	//主键id
	private String id;
	
	//操作时间
	private Date optTime;
	
	//是否签到，1：签到，0：清除
	private Integer isSign;
	
	//冗余字段，任务主表id
	private String taskId;
	
	//签到用户
	private String signUser;
}
