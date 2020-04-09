package com.youmeek.ssm;

import com.youmeek.ssm.manage.pojo.SysUser;
import com.youmeek.ssm.manage.service.SysUserService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class SSMTest {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Resource
	private SysUserService sysUserService;

	@Test
	public void test1() {
		SysUser sysUser = sysUserService.getById(1L);
		logger.info("--------------------------------" + sysUser.toString());
	}

	@Test
	public void test2(){
		List<String> fields = sysUserService.selectFields("sys_user","ssm");
		for (String field : fields ) {
			System.out.println(field);
		}
	}

	@Test
	public void test3(){
		List<SysUser> sysUsers = sysUserService.getByFields("张");
		for (SysUser sysUser: sysUsers ) {
			System.out.println(sysUser.toString());
		}
	}
}
