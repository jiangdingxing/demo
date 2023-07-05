package com.connavi.minedataextend.web.pojo.entity;

import java.util.Date;

/**
 * <ul>
 * <li>table name:  t_key_limit</li>
 * <li>table comment:  </li>
 * <li>author name: jiangDingXing</li>
 * <li>create time: 2023-07-05 09:55:18</li>
 * </ul>
 */ 
public class TKeyLimit extends BaseBO{

	private static final String MAPPER = "maindb/TKeyLimitMapper.xml"; 
	@Override
	public String getMapper() {
		return MAPPER;
	}
	/*module id,对应api表中的id*/
	private String moduleId;
	/*日调用量，此字段替代rate_limit字段*/
	private Long dayLimit;
	/*每秒访问量*/
	private Integer qps;
	/*创建时间*/
	private Date createTime;
	/*key权限组id，归属的权限组信息*/
	private String keyId;
	/*更新时间*/
	private Date updateTime;

	public TKeyLimit() {
		super();
	}
	public TKeyLimit(String moduleId,Long dayLimit,Integer qps,Date createTime,String keyId,Date updateTime) {
		this.moduleId=moduleId;
		this.dayLimit=dayLimit;
		this.qps=qps;
		this.createTime=createTime;
		this.keyId=keyId;
		this.updateTime=updateTime;
	}
	public void setModuleId(String moduleId){
		this.moduleId=moduleId;
	}
	public String getModuleId(){
		return moduleId;
	}
	public void setDayLimit(Long dayLimit){
		this.dayLimit=dayLimit;
	}
	public Long getDayLimit(){
		return dayLimit;
	}
	public void setQps(Integer qps){
		this.qps=qps;
	}
	public Integer getQps(){
		return qps;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setKeyId(String keyId){
		this.keyId=keyId;
	}
	public String getKeyId(){
		return keyId;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	@Override
	public String toString() {
		return "t_key_limit[" + 
			"moduleId=" + moduleId + 
			", dayLimit=" + dayLimit + 
			", qps=" + qps + 
			", createTime=" + createTime + 
			", keyId=" + keyId + 
			", updateTime=" + updateTime + 
			"]";
	}
}

