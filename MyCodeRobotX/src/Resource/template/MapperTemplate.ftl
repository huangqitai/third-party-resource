package ${bussPackage}.${entityPackage}.mapper;


import ${bussPackage}.${entityPackage}.pojo.${className};
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ${className}Mapper {


/**
* 插入一条数据
* @param ${lowerName} 记录
* @return
*/
int insert(${className} ${lowerName});


/**
* 指定字段模糊查询
* @param field 字段名
* @param searchText 查询条件
* @return 实体列表
*/
List<${className}> getByField(@Param("field")String field, @Param("searchText")String searchText);

/**
* 查询所有数据的列表
* @return 实体列表
*/
List<${className}> getAll();

/**
* 更新一条数据
* @param ${lowerName} 记录
* @return
*/
int updateByPrimaryKeySelective(${className} ${lowerName});


}