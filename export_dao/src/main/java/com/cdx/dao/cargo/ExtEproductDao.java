package com.cdx.dao.cargo;

import com.cdx.domain.cargo.ExtEproduct;
import com.cdx.domain.cargo.ExtEproductExample;

import java.util.List;

public interface ExtEproductDao {
	/**
	 * 根据id删除
	 */
    int deleteByPrimaryKey(String id);

	/**
	 * 保存
	 */
    int insertSelective(ExtEproduct record);

	/**
	 * 条件查询
	 */
    List<ExtEproduct> selectByExample(ExtEproductExample example);

	/**
	 * 根据id查询
	 */
    ExtEproduct selectByPrimaryKey(String id);

	/**
	 * 更新
	 */
    int updateByPrimaryKeySelective(ExtEproduct record);
}