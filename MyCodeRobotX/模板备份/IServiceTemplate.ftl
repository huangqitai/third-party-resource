package ${bussPackage}.service.${entityPackage};

import com.ibase.common.bean.Pager;
import com.ibase.common.service.IBaseService;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.entity.${entityPackage}.${className}DTO;

/**
 * ${className}Service接口
 * <br>
 * <b>功能：</b>${className}Dao<br>
 */
public interface I${className}Service extends IBaseService<${className}> {

	/**
	 * 列表
	 * @param pager
	 */
	public Pager selectByMap(Pager pager);
	
	/**
	 * 更新
	 * @param ${lowerName}
	 */
	public void update${className}(${className}DTO ${lowerName});
	/**
	 * 保存
	 * @param ${lowerName}
	 */
	public void insert${className}(${className}DTO ${lowerName});
}