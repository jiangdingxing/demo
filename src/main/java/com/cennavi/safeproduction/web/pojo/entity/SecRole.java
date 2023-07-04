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
@TableName("sec_role")
public class SecRole implements Serializable {
private static final long serialVersionUID = 1L;

/**
 * 
 */
@TableField("id")
private String id;

/**
 * 角色名称
 */
@TableField("name")
private String name;

/**
 * 创建时间
 */
@TableField("create_time")
private Date createTime;

/**
 * 创建用户
 */
@TableField("create_user")
private String createUser;

/**
 * 最后修改时间
 */
@TableField("last_update_time")
private Date lastUpdateTime;

/**
 * 最后修改用户
 */
@TableField("last_update_user")
private String lastUpdateUser;
}
