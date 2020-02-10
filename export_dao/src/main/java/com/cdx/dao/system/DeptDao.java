package com.cdx.dao.system;



import com.cdx.domain.system.Dept;

import java.util.List;


public interface DeptDao {

	//根据企业id查询全部
	List<Dept> findAll(String companyId);

	//根据id查询
    Dept findById(String deptId);

    //根据id删除
    int delete(String deptId);

    // 逻辑删除
    int deleteState(String deptId);

	//保存
    int save(Dept dept);

	//更新
    int update(Dept dept);
}