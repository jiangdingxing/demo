package com.connavi.minedataextend.web.pojo.entity;

/**
 * <ul>
 * <li>table name:  t_user_role</li>
 * <li>table comment:  </li>
 * <li>author name: jiangDingXing</li>
 * <li>create time: 2023-07-05 09:55:18</li>
 * </ul>
 */ 
public class TUserRole extends BaseBO{

	private static final String MAPPER = "maindb/TUserRoleMapper.xml"; 
	@Override
	public String getMapper() {
		return MAPPER;
	}
	/*用户id*/
	private Long userId;
	/*角色id*/
	private Long roleId;

	public TUserRole() {
		super();
	}
	public TUserRole(Long userId,Long roleId) {
		this.userId=userId;
		this.roleId=roleId;
	}
	public void setUserId(Long userId){
		this.userId=userId;
	}
	public Long getUserId(){
		return userId;
	}
	public void setRoleId(Long roleId){
		this.roleId=roleId;
	}
	public Long getRoleId(){
		return roleId;
	}
	@Override
	public String toString() {
		return "t_user_role[" + 
			"userId=" + userId + 
			", roleId=" + roleId + 
			"]";
	}
}

