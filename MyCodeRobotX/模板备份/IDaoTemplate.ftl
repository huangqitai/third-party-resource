package ${bussPackage}.${entityPackage}.dao;

import com.ibase.common.dao.IBaseDAO;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.entity.${entityPackage}.${className}DTO;

/**
 * ${className}DAO接口
 * <br>
 * <b>功能：</b>${className}Dao<br>
 */
public interface I${className}Dao extends IBaseDAO<${className}>{
	
	/**
	 * 列表
	 * @param pager
	 */
	public Pager selectByMap(Pager pager);
}
