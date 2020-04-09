package ${bussPackage}.${entityPackage}.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${bussPackage}.${entityPackage}.pojo.${className};
import ${bussPackage}.${entityPackage}.service.${className}Service;
import ${bussPackage}.common.vo.JSONResult;
import ${bussPackage}.common.controller.FormController;

import javax.annotation.Resource;
import java.util.List;
 
/**
 * @Title:${descTitle}
 * @Description:${description}
 * @author ${author}
 * @date: 创建时间：${currentDate}
 */
@Controller
@RequestMapping("/${lowerName}Controller")
public class ${className}Controller extends FormController<${className}>{

	@Resource
	private ${className}Service ${lowerName}Service;

	/**
	* 查询列表
	* @description 根据传入的参数filed和searchText判断是否查出完整数据列表
	*  查询的字段或者搜索条件为空 就 查询全部,
	*  两个参数都不为空 就 根据字段进行查询
	* @param field 查询字段
	* @param searchText  查询条件
	* @return 数据列表 List<${className}>
	*/
	@RequestMapping("/list")
	@ResponseBody
	public List<${className}> list(String field,String searchText){
	    List<${className}> ${lowerName}s;
		//字段或者条件为空
		if (field == null || "".equals(field)||"".equals(searchText)|| searchText == null){
		    ${lowerName}s = ${lowerName}Service.getAll();
		}

		//查询条件不为空
		else {
		    ${lowerName}s = ${lowerName}Service.getByField(field,searchText);
		}
		return ${lowerName}s;
	}

	/**
	* 插入一条数据
	* @description
	* @param ${lowerName}
	* @return 返回插入成功信息JSON数据
	*/
	@RequestMapping("/insert")
	@ResponseBody
	public JSONResult insert(${className} ${lowerName}){
		int insertNum = ${lowerName}Service.insert(${lowerName});
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
	* @param ${lowerName}
	* @return 返回更新成功信息JSON数据
	*/
	@RequestMapping("/update")
	@ResponseBody
	public JSONResult update(${className} ${lowerName}){

		int updateNum = ${lowerName}Service.update(${lowerName});
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
