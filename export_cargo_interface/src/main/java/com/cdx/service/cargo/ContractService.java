package com.cdx.service.cargo;

import com.cdx.domain.cargo.Contract;
import com.cdx.domain.cargo.ContractExample;
import com.cdx.domain.vo.ContractProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface ContractService {

	//根据id查询
    Contract findById(String id);

    //保存
    void save(Contract contract);

    //更新
    void update(Contract contract);

    //删除
    void delete(String id);

    //分页查询
	public PageInfo findAll(ContractExample example, int page, int size);
    // 查询出货表
    List<ContractProductVo> findContractProdcutPrint(String companyId, String time);
}
