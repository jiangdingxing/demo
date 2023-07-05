package com.connavi.minedataextend.web.pojo.entity;

import java.util.Date;

/**
 * <ul>
 * <li>table name:  t_key</li>
 * <li>table comment:  </li>
 * <li>author name: jiangDingXing</li>
 * <li>create time: 2023-07-05 09:55:18</li>
 * </ul>
 */ 
public class TKey extends BaseBO{

	private static final String MAPPER = "maindb/TKeyMapper.xml"; 
	@Override
	public String getMapper() {
		return MAPPER;
	}
	/*用户id*/
	private Long userId;
	/*权限组id(服务限制id)*/
	private String accessGroupId;
	/*生效开始时间*/
	private Date startTime;
	/*生效截止时间*/
	private Date endTime;
	/*最后处理人*/
	private Integer lastUpdateUserId;
	/*最后处理时间*/
	private Date lastUpdateTime;
	/*主键,key_id*/
	private String id;
	/*创建时间*/
	private Date createTime;
	/*创建用户*/
	private Integer createUser;
	/*服务类型：1-->地图服务  2-->lbs服务*/
	private Integer moduleType;
	/*ip白名单*/
	private String ipWhiteList;
	/*应用id*/
	private String appId;
	/*key名称*/
	private String name;
	/*逻辑删除标志符 1.表示删除 0.表示存在*/
	private Integer isDel;
	/*是否是系统key*/
	private String isSys;

	public TKey() {
		super();
	}
	public TKey(Long userId,String accessGroupId,Date startTime,Date endTime,Integer lastUpdateUserId,Date lastUpdateTime,String id,Date createTime,Integer createUser,Integer moduleType,String ipWhiteList,String appId,String name,Integer isDel,String isSys) {
		this.userId=userId;
		this.accessGroupId=accessGroupId;
		this.startTime=startTime;
		this.endTime=endTime;
		this.lastUpdateUserId=lastUpdateUserId;
		this.lastUpdateTime=lastUpdateTime;
		this.id=id;
		this.createTime=createTime;
		this.createUser=createUser;
		this.moduleType=moduleType;
		this.ipWhiteList=ipWhiteList;
		this.appId=appId;
		this.name=name;
		this.isDel=isDel;
		this.isSys=isSys;
	}
	public void setUserId(Long userId){
		this.userId=userId;
	}
	public Long getUserId(){
		return userId;
	}
	public void setAccessGroupId(String accessGroupId){
		this.accessGroupId=accessGroupId;
	}
	public String getAccessGroupId(){
		return accessGroupId;
	}
	public void setStartTime(Date startTime){
		this.startTime=startTime;
	}
	public Date getStartTime(){
		return startTime;
	}
	public void setEndTime(Date endTime){
		this.endTime=endTime;
	}
	public Date getEndTime(){
		return endTime;
	}
	public void setLastUpdateUserId(Integer lastUpdateUserId){
		this.lastUpdateUserId=lastUpdateUserId;
	}
	public Integer getLastUpdateUserId(){
		return lastUpdateUserId;
	}
	public void setLastUpdateTime(Date lastUpdateTime){
		this.lastUpdateTime=lastUpdateTime;
	}
	public Date getLastUpdateTime(){
		return lastUpdateTime;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}
	public Integer getCreateUser(){
		return createUser;
	}
	public void setModuleType(Integer moduleType){
		this.moduleType=moduleType;
	}
	public Integer getModuleType(){
		return moduleType;
	}
	public void setIpWhiteList(String ipWhiteList){
		this.ipWhiteList=ipWhiteList;
	}
	public String getIpWhiteList(){
		return ipWhiteList;
	}
	public void setAppId(String appId){
		this.appId=appId;
	}
	public String getAppId(){
		return appId;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}
	public Integer getIsDel(){
		return isDel;
	}
	public void setIsSys(String isSys){
		this.isSys=isSys;
	}
	public String getIsSys(){
		return isSys;
	}
	@Override
	public String toString() {
		return "t_key[" + 
			"userId=" + userId + 
			", accessGroupId=" + accessGroupId + 
			", startTime=" + startTime + 
			", endTime=" + endTime + 
			", lastUpdateUserId=" + lastUpdateUserId + 
			", lastUpdateTime=" + lastUpdateTime + 
			", id=" + id + 
			", createTime=" + createTime + 
			", createUser=" + createUser + 
			", moduleType=" + moduleType + 
			", ipWhiteList=" + ipWhiteList + 
			", appId=" + appId + 
			", name=" + name + 
			", isDel=" + isDel + 
			", isSys=" + isSys + 
			"]";
	}
}

