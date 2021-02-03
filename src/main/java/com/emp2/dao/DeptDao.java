package com.emp2.dao;

import com.emp2.entity.Dept;
import com.emp2.entity.DeptExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DeptDao {
	long countByExample(DeptExample example);

	int deleteByExample(DeptExample example);

	int deleteByPrimaryKey(Integer deptno);

	int insert(Dept record);

	int insertSelective(Dept record);

	List<Dept> selectByExample(DeptExample example);

	Dept selectByPrimaryKey(Integer deptno);

	int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);

	int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);

	int updateByPrimaryKeySelective(Dept record);

	int updateByPrimaryKey(Dept record);
}
