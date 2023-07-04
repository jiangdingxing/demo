package com.cennavi.safeproduction.web.pojo.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 
 * 
 * @author jiangDingXing
 * @date 2023/03/30 15:52
 */
@Data
@TableName("sec_user_role")
public class SecUserRole implements Serializable {
private static final long serialVersionUID = 1L;

/**
 * 
 */
@TableField("id")
private Integer id;

/**
 * 用户id
 */
@TableField("user_id")
private String userId;

/**
 * 角色id
 */
@TableField("role_id")
private String roleId;
}
