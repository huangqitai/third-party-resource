package ${bussPackage}.${entityPackage}.service;

import ${bussPackage}.${entityPackage}.pojo.${className};
import java.util.List;

	/**
	 * @Title:${descTitle}
	 * @Description:${description}
	 * @author ${author}
	 * @date: 创建时间：${currentDate}
	 */
	public interface ${className}Service {

		/**
		* 查询所有
		* @return 列表
		*/
		List<${className}> getAll();

		/**
		* 指定字段模糊查询
		* @param field 指定字段
		* @param searchText 查询条件
		* @return 列表
		*/
		List<${className}> getByField(String field,String searchText);

		/**
		* 插入实体
		* @param 实体
		* @return 受影响的行数，大于0则插入成功
		*/
		int insert(${className} ${lowerName});

		/**
		* 更新信息
		* @param 实体
		* @return 受影响的行数，大于0则更新成功
		*/
		int update(${className} ${lowerName});

}