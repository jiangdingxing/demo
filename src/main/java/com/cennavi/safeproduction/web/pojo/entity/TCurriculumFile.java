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
@TableName("t_curriculum_file")
public class TCurriculumFile implements Serializable {
private static final long serialVersionUID = 1L;

/**
 * 
 */
@TableField("id")
private String id;

/**
 * 文件名称
 */
@TableField("name")
private String name;

/**
 * 文件类型
 */
@TableField("file_type")
private String fileType;

/**
 * 上传时间
 */
@TableField("upload_time")
private Date uploadTime;

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

/**
 * 文件资源url
 */
@TableField("file_url")
private String fileUrl;

/**
 * 文件备注
 */
@TableField("remark")
private String remark;
}
