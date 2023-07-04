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
@TableName("t_curriculum")
public class TCurriculum implements Serializable {
private static final long serialVersionUID = 1L;

/**
 * 
 */
@TableField("id")
private String id;

/**
 * 课程名称
 */
@TableField("curriculum_name")
private String curriculumName;

/**
 * 课程简介
 */
@TableField("curriculum_describe")
private String curriculumDescribe;

/**
 * 主讲人
 */
@TableField("speaker")
private String speaker;

/**
 * 上传时间
 */
@TableField("upload_time")
private Date uploadTime;

/**
 * 课程类型名称
 */
@TableField("type_name")
private String typeName;

/**
 * 最后更新时间
 */
@TableField("last_update_time")
private Date lastUpdateTime;

/**
 * 最后更新用户
 */
@TableField("last_update_user")
private String lastUpdateUser;
}
