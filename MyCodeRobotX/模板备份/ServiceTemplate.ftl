package ${bussPackage}.service.${entityPackage}.impl;

import org.apache.log4j.Logger;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibase.common.bean.Pager;
import com.ibase.common.entity.AbstractWorkflowEntity;
import com.ibase.common.id.UUIDHexGenerator;
import com.ibase.common.util.DataSwapUtil;
import com.ibase.pms.service.basic.AbstractAuthService;
import ${bussPackage}.dao.${entityPackage}.I${className}DAO;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.entity.${entityPackage}.${className}DTO;
import ${bussPackage}.service.${entityPackage}.I${className}Service;

/**
 * ${className}Service实现
 * <br>
 * <b>功能：</b>${className}Service<br>
 */
@Service
public class ${className}ServiceImpl extends AbstractAuthService<${className}> implements I${className}Service {
	
	//private final static Logger log= Logger.getLogger(${className}Service.class);
	

	@Autowired
    private I${className}Dao ${lowerName}dao;

	@Resource
	public void setBaseDAO(I${className}Dao dao) {
		super.setBaseDAO(dao);
	}
	
	/**
	 * 列表
	 * @param pager
	 */
	public Pager selectByMap(Pager pager){
		return ${lowerName}dao.selectByMap(pager);
	}
	
	/**
	 * 更新
	 * @param ${lowerName}
	 */
	public void update${className}(${className}DTO ${lowerName}dto){
		${className} ${lowerName} = new ${className}();
		try {
			DataSwapUtil.getInstance().dtoAsPojo(${lowerName}dto, ${lowerName});
			${lowerName}dao.update(${lowerName});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存
	 * @param ${lowerName}
	 */
	public void insert${className}(${className}DTO ${lowerName}dto){
		${className} ${lowerName} = new ${className}();
		try {
			DataSwapUtil.getInstance().dtoAsPojo(${lowerName}dto, ${lowerName});
			${lowerName}dao.insert(${lowerName});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
