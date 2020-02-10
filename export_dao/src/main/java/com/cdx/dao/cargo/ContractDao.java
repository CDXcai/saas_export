package com.cdx.dao.cargo;

import com.cdx.domain.cargo.Contract;
import com.cdx.domain.cargo.ContractExample;
import com.cdx.domain.vo.ContractProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    /**
     * 查询指定月份的出货表
     * @param companyId
     * @param time
     * @return
     */
    List<ContractProductVo> findContractProdcutPrint(@Param("companyId") String companyId,
                                                     @Param("time") String time);
}