package com.youmeek.ssm.manage.service.impl;


import com.youmeek.ssm.manage.mapper.SysUserMapper;
import com.youmeek.ssm.manage.pojo.SysUser;
import com.youmeek.ssm.manage.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Resource
	private SysUserMapper sysUserMapper;

    /**
     * 根据id查询
     * @param id id
     * @return 实体
     */
	@Override
	public SysUser getById(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

    /**
     * 查询所有
     * @return 实体列表
     */
	@Override
	public List<SysUser> getAll() {
		return sysUserMapper.getAll();
	}

    /**
     * 不限定字段模糊查询
     * @param searchText 查询条件
     * @return 实体列表
     */
	@Override
	public List<SysUser> getByFields(String searchText) {
		List<String> fields = sysUserMapper.selectFields("sys_user","ssm");
		return sysUserMapper.getByFields(fields,searchText);
	}

    /**
     * 限定字段模糊查询
     * @param field 指定字段
     * @param searchText 查询条件
     * @return 实体列表
     */
    @Override
    public List<SysUser> getByField(String field, String searchText) {
        return sysUserMapper.getByField(field,searchText);
    }

    /**
     * 插入实体
     * @param 实体
     * @return 受影响的行数，大于0则表示插入成功
     */
    @Override
	public int insert(SysUser sysUser) {
		return sysUserMapper.insertSelective(sysUser);
	}

    /**
     *更新实体信息
     * @param 实体
     * @return 受影响的行数，大于0 则更新成功
     */
	@Override
	public int update(SysUser sysUser) {
		return sysUserMapper.updateByPrimaryKeySelective(sysUser);
	}

    /**
     * 查询数据表的所有字段
     * @return 字段列表
     */
    @Override
    public List<String> selectFields(String table_name,String db_name) {
        return sysUserMapper.selectFields(table_name,db_name);
    }
}
