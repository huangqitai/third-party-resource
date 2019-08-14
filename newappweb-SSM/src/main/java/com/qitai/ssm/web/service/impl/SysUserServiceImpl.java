package com.qitai.ssm.web.service.impl;

import com.qitai.ssm.web.mapper.SysUserMapper;
import com.qitai.ssm.web.pojo.SysUser;
import com.qitai.ssm.web.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Resource
	private SysUserMapper sysUserMapper;


	@Override
	public SysUser getById(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}
}
