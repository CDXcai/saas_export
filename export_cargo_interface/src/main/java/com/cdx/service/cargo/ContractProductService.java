package com.cdx.service.cargo;


import com.cdx.domain.cargo.ContractProduct;
import com.cdx.domain.cargo.ContractProductExample;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 业务层接口
 */
public interface ContractProductService {

	/**
	 * 保存
	 */
	void save(ContractProduct contractProduct);

	/**
	 * 更新
	 */
	void update(ContractProduct contractProduct);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	ContractProduct findById(String id);

	/**
	 * 分页查询
	 */
	PageInfo findAll(ContractProductExample example, int page, int size);

	// 遍历保存
	public void saveList(List<ContractProduct> products);

	// 根据指定的规则查询
	List<ContractProduct> selectByExample(ContractProductExample contractProductExample);

}
