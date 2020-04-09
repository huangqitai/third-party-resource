package com.youmeek.ssm.manage.controller;


import com.youmeek.ssm.manage.pojo.SysUser;
import com.youmeek.ssm.manage.service.SysUserService;
import com.youmeek.ssm.common.vo.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youmeek.ssm.common.controller.FormController;

import javax.annotation.Resource;
import java.util.List;
/**
 * @author 作者
 * @Title 注释标题
 * @Description: 注释内容
 * @date 2019年12月20日09:34:30
 */
@Controller
@RequestMapping("/sysUserController")
public class SysUserController extends FormController<SysUser> {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 查询列表
	 * @description 根据传入的参数filed和searchText判断是否查出完整数据列表
	 *  两个参数都为空 就 查询全部,
	 *  两个参数都不为空 就 根据字段进行查询
	 * @param field 查询字段
	 * @param searchText  查询条件
	 * @return 用户列表 List<SysUser>
	 */
    @RequestMapping("/list")
    @ResponseBody
	public List<SysUser> list(String field,String searchText){
		List<SysUser> sysUsers;
		//两者均为空
		if ((field == null || "".equals(field))&&("".equals(searchText)|| searchText == null)){
    		sysUsers = sysUserService.getAll();
		}
		//查询条件不为空
		else {
			sysUsers = sysUserService.getByField(field,searchText);
		}
	    return sysUsers;
    }

	/**
	 * 插入一条数据
	 * @description
	 * @param sysUser
	 * @return 返回插入成功信息JSON数据
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public JSONResult insert(SysUser sysUser){

		int insertNum = sysUserService.insert(sysUser);
		JSONResult jsonResult;
		if (insertNum > 0){
			 jsonResult = new JSONResult(SUCCESS_INSERT,SUCCESS_CODE);
		}
		else{
             jsonResult = new JSONResult(FAIL_INSERT,FAIL_CODE);
		}

		return jsonResult;
	}

	/**
	 * 更新一条数据
	 * @description
	 * @param sysUser
	 * @return 返回更新成功信息JSON数据
	 */
	@RequestMapping("/update")
	@ResponseBody
	public JSONResult update(SysUser sysUser){

		int updateNum = sysUserService.update(sysUser);
		JSONResult jsonResult;
		if (updateNum > 0) {
			 jsonResult = new JSONResult(SUCCESS_UPDATE, SUCCESS_CODE);
		}
		else{
			 jsonResult = new JSONResult(FAIL_UPDATE, FAIL_CODE);
		}
		return jsonResult;

	}

}
