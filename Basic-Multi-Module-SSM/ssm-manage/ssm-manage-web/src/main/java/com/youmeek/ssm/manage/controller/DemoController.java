package com.youmeek.ssm.manage.controller;


import com.youmeek.ssm.manage.pojo.SysUser;
import com.youmeek.ssm.manage.service.SysUserService;
import com.youmeek.ssm.common.vo.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 作者
 * @Title 注释标题
 * @Description: 注释内容
 * @date 2019年12月20日09:34:30
 */
@Controller
@RequestMapping("/demoController")
public class DemoController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<SysUser> queryAll() {
        List<SysUser> userList = sysUserService.getAll();
        return userList;
    }

    /**
     * 获取列表
     *
     * @param request
     * @param response
     * @param searchText
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<SysUser> queryLike(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false,defaultValue = "") String searchText) {
		List<SysUser> userList = sysUserService.getByFields(searchText);
        return userList;
    }

    /**
     * @param field
     * @param searchText
     * @return
     * @description 描述信息
     */
    @RequestMapping("/queryLikeByField")
    @ResponseBody
    public List<SysUser> queryLokeByField(String field, String searchText) {
        List<SysUser> sysUsers = sysUserService.getByField(field, searchText);
        return sysUsers;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public JSONResult insertUser(SysUser sysUser, HttpServletResponse response) {
        sysUserService.insert(sysUser);

        JSONResult jsonResult = new JSONResult();
        jsonResult.setMessage("插入成功");
        jsonResult.setSuccess("0");
        return jsonResult;

    }

    @RequestMapping("/update")
    @ResponseBody
    public JSONResult updateUser(SysUser sysUser, HttpServletResponse response) {
        sysUserService.update(sysUser);
        JSONResult jsonResult = new JSONResult();
        jsonResult.setMessage("修改成功");
        jsonResult.setSuccess("0");
        return jsonResult;
    }


}
