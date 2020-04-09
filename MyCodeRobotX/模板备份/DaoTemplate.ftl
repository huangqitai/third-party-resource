package ${bussPackage}.dao.${entityPackage}.Impl;


import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ibase.pms.dao.MyAbstractDAO;
import com.ibase.common.dao.impl.AbstractDAO;
import ${bussPackage}.dao.${entityPackage}.I${className}DAO;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.entity.${entityPackage}.${className}DTO;

/**
 * ${className}DAO实现
 * <br>
 * <b>功能：</b>${className}DaoImpl<br>
 */
 
@Repository
public class ${className}DaoImpl extends AbstractDAO<${className}> implements I${className}Dao {
	
	private static final String SELECT_BY_MAP_REPAGER = "selectByMap";
	
	/**
	 * 列表
	 * @param pager
	 */
	public Pager selectByMap(Pager pager){
		return getByPager(SELECT_BY_MAP_REPAGER, pager);
	}
}
