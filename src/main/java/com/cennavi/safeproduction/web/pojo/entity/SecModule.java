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
@TableName("sec_module")
public class SecModule implements Serializable {
private static final long serialVersionUID = 1L;

/**
 * 
 */
@TableField("id")
private String id;

/**
 * 菜单名称
 */
@TableField("name")
private String name;

/**
 * 父级菜单id
 */
@TableField("parent_id")
private String parentId;

/**
 * 路径编码，用于排序
 */
@TableField("tree_path")
private String treePath;

/**
 * 是否子菜单 1是 0否
 */
@TableField("is_leaf")
private Integer isLeaf;
}
