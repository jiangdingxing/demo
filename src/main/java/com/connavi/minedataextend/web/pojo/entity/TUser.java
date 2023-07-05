package com.connavi.minedataextend.web.pojo.entity;

import java.util.Date;

/**
 * <ul>
 * <li>table name:  t_user</li>
 * <li>table comment:  </li>
 * <li>author name: jiangDingXing</li>
 * <li>create time: 2023-07-05 09:55:18</li>
 * </ul>
 */ 
public class TUser extends BaseBO{

	private static final String MAPPER = "maindb/TUserMapper.xml"; 
	@Override
	public String getMapper() {
		return MAPPER;
	}
	/*id*/
	private Integer id;
	/*用户名称*/
	private String username;
	/*用户密码*/
	private String password;
	/*用户类型*/
	private Long type;
	/*名称*/
	private String name;
	/*邮件*/
	private String mail;
	/*联系电话*/
	private String contact;
	/*类型*/
	private Long certtype;
	/*信息*/
	private String info;
	/*创建时间*/
	private String createtime;
	private String vcode;
	private String vtime;
	private Long locked;
	/*邮箱状态*/
	private Long mailstatus;
	/*联系邮箱状态*/
	private Long contactstatus;
	/*公司*/
	private String company;
	/*校验签名串（已弃用）*/
	private String digest;
	/*32 uuid 逐渐使用uuid取代自增id*/
	private String uid;
	/*逻辑删除标志符*/
	private Integer isDel;
	/*公司名称*/
	private String companyId;
	/*角色编码*/
	private String roleCode;
	/*是否修改过密码*/
	private Integer updatePwd;
	/*组织id*/
	private String orgId;
	/*有效期开始时间*/
	private Date startTime;
	/*有效期截止时间*/
	private Date endTime;
	/*组织名称*/
	private String orgName;
	/*1: 开启 0:禁用*/
	private Integer status;

	public TUser() {
		super();
	}
	public TUser(Integer id,String username,String password,Long type,String name,String mail,String contact,Long certtype,String info,String createtime,String vcode,String vtime,Long locked,Long mailstatus,Long contactstatus,String company,String digest,String uid,Integer isDel,String companyId,String roleCode,Integer updatePwd,String orgId,Date startTime,Date endTime,String orgName,Integer status) {
		this.id=id;
		this.username=username;
		this.password=password;
		this.type=type;
		this.name=name;
		this.mail=mail;
		this.contact=contact;
		this.certtype=certtype;
		this.info=info;
		this.createtime=createtime;
		this.vcode=vcode;
		this.vtime=vtime;
		this.locked=locked;
		this.mailstatus=mailstatus;
		this.contactstatus=contactstatus;
		this.company=company;
		this.digest=digest;
		this.uid=uid;
		this.isDel=isDel;
		this.companyId=companyId;
		this.roleCode=roleCode;
		this.updatePwd=updatePwd;
		this.orgId=orgId;
		this.startTime=startTime;
		this.endTime=endTime;
		this.orgName=orgName;
		this.status=status;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setType(Long type){
		this.type=type;
	}
	public Long getType(){
		return type;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setMail(String mail){
		this.mail=mail;
	}
	public String getMail(){
		return mail;
	}
	public void setContact(String contact){
		this.contact=contact;
	}
	public String getContact(){
		return contact;
	}
	public void setCerttype(Long certtype){
		this.certtype=certtype;
	}
	public Long getCerttype(){
		return certtype;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public String getInfo(){
		return info;
	}
	public void setCreatetime(String createtime){
		this.createtime=createtime;
	}
	public String getCreatetime(){
		return createtime;
	}
	public void setVcode(String vcode){
		this.vcode=vcode;
	}
	public String getVcode(){
		return vcode;
	}
	public void setVtime(String vtime){
		this.vtime=vtime;
	}
	public String getVtime(){
		return vtime;
	}
	public void setLocked(Long locked){
		this.locked=locked;
	}
	public Long getLocked(){
		return locked;
	}
	public void setMailstatus(Long mailstatus){
		this.mailstatus=mailstatus;
	}
	public Long getMailstatus(){
		return mailstatus;
	}
	public void setContactstatus(Long contactstatus){
		this.contactstatus=contactstatus;
	}
	public Long getContactstatus(){
		return contactstatus;
	}
	public void setCompany(String company){
		this.company=company;
	}
	public String getCompany(){
		return company;
	}
	public void setDigest(String digest){
		this.digest=digest;
	}
	public String getDigest(){
		return digest;
	}
	public void setUid(String uid){
		this.uid=uid;
	}
	public String getUid(){
		return uid;
	}
	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}
	public Integer getIsDel(){
		return isDel;
	}
	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}
	public String getCompanyId(){
		return companyId;
	}
	public void setRoleCode(String roleCode){
		this.roleCode=roleCode;
	}
	public String getRoleCode(){
		return roleCode;
	}
	public void setUpdatePwd(Integer updatePwd){
		this.updatePwd=updatePwd;
	}
	public Integer getUpdatePwd(){
		return updatePwd;
	}
	public void setOrgId(String orgId){
		this.orgId=orgId;
	}
	public String getOrgId(){
		return orgId;
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
	public void setOrgName(String orgName){
		this.orgName=orgName;
	}
	public String getOrgName(){
		return orgName;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	@Override
	public String toString() {
		return "t_user[" + 
			"id=" + id + 
			", username=" + username + 
			", password=" + password + 
			", type=" + type + 
			", name=" + name + 
			", mail=" + mail + 
			", contact=" + contact + 
			", certtype=" + certtype + 
			", info=" + info + 
			", createtime=" + createtime + 
			", vcode=" + vcode + 
			", vtime=" + vtime + 
			", locked=" + locked + 
			", mailstatus=" + mailstatus + 
			", contactstatus=" + contactstatus + 
			", company=" + company + 
			", digest=" + digest + 
			", uid=" + uid + 
			", isDel=" + isDel + 
			", companyId=" + companyId + 
			", roleCode=" + roleCode + 
			", updatePwd=" + updatePwd + 
			", orgId=" + orgId + 
			", startTime=" + startTime + 
			", endTime=" + endTime + 
			", orgName=" + orgName + 
			", status=" + status + 
			"]";
	}
}

