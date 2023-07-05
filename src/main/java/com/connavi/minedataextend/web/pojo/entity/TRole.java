package com.connavi.minedataextend.web.pojo.entity;

/**
 * <ul>
 * <li>table name:  t_role</li>
 * <li>table comment:  </li>
 * <li>author name: jiangDingXing</li>
 * <li>create time: 2023-07-05 09:55:18</li>
 * </ul>
 */ 
public class TRole extends BaseBO{

	private static final String MAPPER = "maindb/TRoleMapper.xml"; 
	@Override
	public String getMapper() {
		return MAPPER;
	}
	/*id*/
	private Long id;
	/*code码*/
	private String code;
	/*名称*/
	private String name;
	/*类型*/
	private Long type;
	/*描述*/
	private String describe;
	/*是否删除*/
	private Integer isDel;
	/*父类id*/
	private Long parentId;
	/*所在组织id*/
	private String orgId;

	public TRole() {
		super();
	}
	public TRole(Long id,String code,String name,Long type,String describe,Integer isDel,Long parentId,String orgId) {
		this.id=id;
		this.code=code;
		this.name=name;
		this.type=type;
		this.describe=describe;
		this.isDel=isDel;
		this.parentId=parentId;
		this.orgId=orgId;
	}
	public void setId(Long id){
		this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setCode(String code){
		this.code=code;
	}
	public String getCode(){
		return code;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setType(Long type){
		this.type=type;
	}
	public Long getType(){
		return type;
	}
	public void setDescribe(String describe){
		this.describe=describe;
	}
	public String getDescribe(){
		return describe;
	}
	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}
	public Integer getIsDel(){
		return isDel;
	}
	public void setParentId(Long parentId){
		this.parentId=parentId;
	}
	public Long getParentId(){
		return parentId;
	}
	public void setOrgId(String orgId){
		this.orgId=orgId;
	}
	public String getOrgId(){
		return orgId;
	}
	@Override
	public String toString() {
		return "t_role[" + 
			"id=" + id + 
			", code=" + code + 
			", name=" + name + 
			", type=" + type + 
			", describe=" + describe + 
			", isDel=" + isDel + 
			", parentId=" + parentId + 
			", orgId=" + orgId + 
			"]";
	}
}

