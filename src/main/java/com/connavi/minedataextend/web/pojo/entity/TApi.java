package com.connavi.minedataextend.web.pojo.entity;

import java.util.Date;

/**
 * <ul>
 * <li>table name:  t_api</li>
 * <li>table comment:  </li>
 * <li>author name: jiangDingXing</li>
 * <li>create time: 2023-07-05 09:55:18</li>
 * </ul>
 */ 
public class TApi extends BaseBO{

	private static final String MAPPER = "maindb/TApiMapper.xml"; 
	@Override
	public String getMapper() {
		return MAPPER;
	}
	/*API编号*/
	private String id;
	/*API名称*/
	private String name;
	/*匹配正则URL*/
	private String url;
	/*访问方式*/
	private String method;
	/*API描述*/
	private String descri;
	/*创建人*/
	private Long createUserId;
	/*创建时间*/
	private Date createTime;
	/*更新人*/
	private Long updateUserId;
	/*更新时间*/
	private Date updateTime;
	/*API 组编号*/
	private String groupId;
	/*是否展示*/
	private boolean show;
	/*是否统计*/
	private boolean stat;
	/*排序*/
	private Integer num;
	/*是否最新*/
	private boolean newly;
	/*授权管理是否显示*/
	private boolean showAuth;

	public TApi() {
		super();
	}
	public TApi(String id,String name,String url,String method,String descri,Long createUserId,Date createTime,Long updateUserId,Date updateTime,String groupId,boolean show,boolean stat,Integer num,boolean newly,boolean showAuth) {
		this.id=id;
		this.name=name;
		this.url=url;
		this.method=method;
		this.descri=descri;
		this.createUserId=createUserId;
		this.createTime=createTime;
		this.updateUserId=updateUserId;
		this.updateTime=updateTime;
		this.groupId=groupId;
		this.show=show;
		this.stat=stat;
		this.num=num;
		this.newly=newly;
		this.showAuth=showAuth;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}
	public void setMethod(String method){
		this.method=method;
	}
	public String getMethod(){
		return method;
	}
	public void setDescri(String descri){
		this.descri=descri;
	}
	public String getDescri(){
		return descri;
	}
	public void setCreateUserId(Long createUserId){
		this.createUserId=createUserId;
	}
	public Long getCreateUserId(){
		return createUserId;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setUpdateUserId(Long updateUserId){
		this.updateUserId=updateUserId;
	}
	public Long getUpdateUserId(){
		return updateUserId;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setGroupId(String groupId){
		this.groupId=groupId;
	}
	public String getGroupId(){
		return groupId;
	}
	public void setShow(boolean show){
		this.show=show;
	}
	public boolean getShow(){
		return show;
	}
	public void setStat(boolean stat){
		this.stat=stat;
	}
	public boolean getStat(){
		return stat;
	}
	public void setNum(Integer num){
		this.num=num;
	}
	public Integer getNum(){
		return num;
	}
	public void setNewly(boolean newly){
		this.newly=newly;
	}
	public boolean getNewly(){
		return newly;
	}
	public void setShowAuth(boolean showAuth){
		this.showAuth=showAuth;
	}
	public boolean getShowAuth(){
		return showAuth;
	}
	@Override
	public String toString() {
		return "t_api[" + 
			"id=" + id + 
			", name=" + name + 
			", url=" + url + 
			", method=" + method + 
			", descri=" + descri + 
			", createUserId=" + createUserId + 
			", createTime=" + createTime + 
			", updateUserId=" + updateUserId + 
			", updateTime=" + updateTime + 
			", groupId=" + groupId + 
			", show=" + show + 
			", stat=" + stat + 
			", num=" + num + 
			", newly=" + newly + 
			", showAuth=" + showAuth + 
			"]";
	}
}

