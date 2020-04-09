package ${bussPackage}.${entityPackage}.service.impl;

import ${bussPackage}.${entityPackage}.mapper.${className}Mapper;
import ${bussPackage}.${entityPackage}.pojo.${className};
import ${bussPackage}.${entityPackage}.service.${className}Service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
/**
 * @Title:${descTitle}
 * @Description:${description}
 * @author ${author}
 * @date: 创建时间：${currentDate}
 */
@Service
public class ${className}ServiceImpl implements ${className}Service {

	@Resource
	private ${className}Mapper ${lowerName}Mapper;


	/**
	* 查询所有
	* @return 实体列表
	*/
	@Override
	public List<${className}> getAll() {
		return ${lowerName}Mapper.getAll();
	}

	/**
	* 限定字段模糊查询
	* @param field 指定字段
	* @param searchText 查询条件
	* @return 实体列表
	*/
	@Override
	public List<${className}> getByField(String field, String searchText) {
		return ${lowerName}Mapper.getByField(field,searchText);
	}

	/**
	* 插入实体
	* @param 实体
	* @return 受影响的行数，大于0则表示插入成功
	*/
	@Override
	public int insert(${className} ${lowerName}) {
		return ${lowerName}Mapper.insert(${lowerName});
	}

	/**
	*更新实体信息
	* @param 实体
	* @return 受影响的行数，大于0 则更新成功
	*/
	@Override
	public int update(${className} ${lowerName}) {
		return ${lowerName}Mapper.updateByPrimaryKeySelective(${lowerName});
	}
}
