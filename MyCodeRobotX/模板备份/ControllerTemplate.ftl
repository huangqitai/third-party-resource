package ${bussPackage}.web.${entityPackage}.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibase.common.bean.Pager;
import com.ibase.common.entity.AbstractWorkflowEntity;
import com.ibase.pms.portal.controller.AbstractWorkflowFormController;
import com.ibase.pms.bean.UserIdentity;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.entity.${entityPackage}.${className}DTO;
import ${bussPackage}.service.${entityPackage}.I${className}Service;
 
/**
 * ${className}Controller实现
 * <br>
 * <b>功能：</b>${className}Controller<br>
 */
@Controller
@RequestMapping("/${lowerName}") 
public class ${className}Controller extends AbstractWorkflowFormController{
	
	private final static Logger log= Logger.getLogger(EquipmentController.class);
	
	// Servrice start
	@Autowired
	private I${className}Service ${lowerName}Service; 
	
	/**
	 * 模块首页
	 * @param request
	 * @param response
	 * @param map
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap map, Pager pager){
		try{
			pager = ${lowerName}Service.selectByMap(pager);
			map.put("pager", pager);
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.frameworkView(request, "", "/${entityPackage}/${lowerName}_list_header", map);
	}
	
	/**
	 * 获取列表
	 * @param request
	 * @param response
	 * @param map
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list") 
	public ModelAndView  list(HttpServletRequest request, HttpServletResponse response, ModelMap map, Pager pager) throws Exception{
		pager = ${lowerName}Service.selectByMap(pager);
		map.put("pager", pager);
		return new ModelAndView("/${entityPackage}/${lowerName}_list", map);		
	}
	
	/**
	 * 新增
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request, ModelMap map){
		UserIdentity user = getCurrentUser();
		initWorkflow(request, null, map);
		return formView(request, "新增", "/${entityPackage}/${lowerName}_add", map);
	}
	
	/**
	 *
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/save")
	public Object save(HttpServletRequest request,${className}DTO ${lowerName}) throws Exception{
		try {
			UserIdentity user = getCurrentUser();
			processWorkflowProperty(request, ${lowerName});
			if(StringUtils.isNotEmpty(${lowerName}.getId())){
				//修改
				${lowerName}Service.update${className}(${lowerName});
			}else{
				//新增
				//需要区分公司时使用
				//${lowerName}.setCompanyId(user.getCompanyId());
				${lowerName}Service.insert${className}(${lowerName});
			}
			return success(${lowerName}, null, request);
		} catch (Exception e) {
			log.error(e.getMessage());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(STATUS_KEY, STATUS_FAIL);
			map.put(ERRORS_KEY, e.getMessage());
			return map;
		}
	}
	
	/**
	 *
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap map,String id) throws Exception{
		try{
			initWorkflow(request, null, map);
			${className} ${lowerName} = ${lowerName}Service.getById(id);
			map.put("${lowerName}",${lowerName});
		}catch(Exception e){
			logger.error("delete contact error:"+e.getMessage());
		}
		return formView(request, "编辑", "/${entityPackage}/${lowerName}_add", map);
	}
	
	
	/**
	 *
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap map,String ids) throws Exception{
		String [] idArray = ids.split(",");
		String msg = RETURN_SUCCESS;
		try{
			for(String id:idArray){
				${lowerName}Service.delete(id);
			}
		}catch (Exception e) {
			logger.error("delete ${lowerName} error:"+e.getMessage());
			msg = "error";
		}
		return msg;
	}
	
	@Override
	public String getBusinessCode() {
		return "";
	}

	@Override
	protected void updateFlowStatus(AbstractWorkflowEntity entity) {
		
	}

}
