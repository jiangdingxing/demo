package com.cennavi.safeproduction.web.pojo.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;

/**
 * 
 * 
 * @author jiangDingXing
 * @date 2023/03/30 15:52
 */
@Data
@TableName("sec_user")
public class SecUser implements Serializable {
private static final long serialVersionUID = 1L;

/**
 * 主键
 */
@TableField("id")
private String id;

/**
 * 用户名
 */
@TableField("username")
private String username;

/**
 * 密码
 */
@TableField("passwrod")
private String passwrod;

/**
 * 真实姓名
 */
@TableField("real_name")
private String realName;

/**
 * 创建时间
 */
@TableField("create_time")
private Date createTime;

/**
 * 最后更新时间
 */
@TableField("last_update_time")
private Date lastUpdateTime;

/**
 * 最后更新用户
 */
@TableField("last_update_user")
private Date lastUpdateUser;

/**
 * 账号过期时间
 */
@TableField("expiration_time")
private Date expirationTime;

/**
 * 保单号
 */
@TableField("insurance_order_no")
private String insuranceOrderNo;

/**
 * 是否启用 1启用 2禁用
 */
@TableField("enable")
private Integer enable;

/**
 * md5加密盐值
 */
@TableField("salt")
private String salt;
}
