package com.emp2.dao;

import com.emp2.entity.Emp;
import com.emp2.entity.EmpExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EmpDao {
	long countByExample(EmpExample example);

	int deleteByExample(EmpExample example);

	int deleteByPrimaryKey(Integer empno);

	int insert(Emp record);

	int insertSelective(Emp record);

	List<Emp> selectByExample(EmpExample example);

	Emp selectByPrimaryKey(Integer empno);

	int updateByExampleSelective(@Param("record") Emp record, @Param("example") EmpExample example);

	int updateByExample(@Param("record") Emp record, @Param("example") EmpExample example);

	int updateByPrimaryKeySelective(Emp record);

	int updateByPrimaryKey(Emp record);
}
