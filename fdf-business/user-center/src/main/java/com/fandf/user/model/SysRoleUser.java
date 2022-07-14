package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

/**
 * @author fandongfeng
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_user")
public class SysRoleUser extends Model<SysRoleUser> {
	private static final long serialVersionUID = 3929037889257033310L;
	private Long userId;
	private Long roleId;
}
