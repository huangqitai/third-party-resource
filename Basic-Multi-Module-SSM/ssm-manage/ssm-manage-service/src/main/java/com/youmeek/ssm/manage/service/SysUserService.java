package com.youmeek.ssm.manage.service;


import com.youmeek.ssm.manage.pojo.SysUser;

import java.util.List;

public interface SysUserService {
	/**
	 * 根据id查询
	 * @param id id
	 * @return 实体
	 */
	SysUser getById(Long id);

	/**
	 * 查询所有
	 * @return 列表
	 */
	List<SysUser> getAll();

	/**
	 * 模糊查询，不指定字段
	 * @param searchText 查询条件
	 * @return 列表
	 */
	List<SysUser> getByFields(String searchText);

	/**
	 * 指定字段模糊查询
	 * @param field 指定字段
	 * @param searchText 查询条件
	 * @return 列表
	 */
	List<SysUser> getByField(String field,String searchText);

	/**
	 * 插入实体
	 * @param 实体
     * @return 受影响的行数，大于0则插入成功
	 */
	int insert(SysUser sysUser);

	/**
	 * 更新信息
	 * @param 实体
     * @return 受影响的行数，大于0则更新成功
	 */
	int update(SysUser sysUser);

	/**
	 * 查询数据表的所有字段
	 * @return 字段列表
	 */
	List<String> selectFields(String table_name,String db_name);
}
