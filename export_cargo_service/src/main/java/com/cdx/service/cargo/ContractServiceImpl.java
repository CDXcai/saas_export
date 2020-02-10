package com.cdx.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdx.dao.cargo.ContractDao;
import com.cdx.dao.cargo.ContractProductDao;
import com.cdx.dao.cargo.ExtCproductDao;
import com.cdx.domain.cargo.*;
import com.cdx.domain.vo.ContractProductVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ExtCproductDao extCproductDao;
    @Override
    public Contract findById(String id) {
        Contract contract = contractDao.selectByPrimaryKey(id);
        return contract;
    }

    @Override
    public void save(Contract contract) {
        // 随机id
        contract.setId(UUID.randomUUID().toString());
        // 总金额
        contract.setTotalAmount(0d);
        // 设置状态，草稿
        contract.setState(0);
        // 货物数量0
        contract.setProNum(0);
        // 附件数量0
        contract.setExtNum(0);
        // 创建时间|
        contract.setCreateTime(new Date());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        // 保存
        contractDao.insertSelective(contract);

    }

    @Override
    public void update(Contract contract) {
        // 修改订单内容
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        try {
            // 查找出该订单下所有的货物
            // 创建查找规则
            ContractProductExample contractProductExample = new ContractProductExample();
            ContractProductExample.Criteria criteria = contractProductExample.createCriteria();
            criteria.andContractIdEqualTo(id);
            List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);
            //
            // 遍历删除所有货物
            for (ContractProduct contractProduct : contractProducts) {
                // 查询货物所有的附件
                // 创建一个查询规则
                ExtCproductExample extCproductExample = new ExtCproductExample();
                // 根据货物id查询数据
                ExtCproductExample.Criteria criteria1 = extCproductExample.createCriteria();
                criteria1.andContractProductIdEqualTo(contractProduct.getId());
                // 获取货物的所有附件
                List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
                // 遍历删除所有的附件
                for (ExtCproduct extCproduct : extCproducts) {
                    extCproductDao.deleteByPrimaryKey(extCproduct.getId());
                }
                // 删除货物
                contractProductDao.deleteByPrimaryKey(contractProduct.getId());

            }
            // 删除指定的订单
            contractDao.deleteByPrimaryKey(id);
        } catch (Exception e) {

            System.out.println("发生异常");
        }

    }

    @Override
    public PageInfo findAll(ContractExample example, int page, int size) {
        // 分页查询
        PageHelper.startPage(page,size);
        // 查询数据
        List<Contract> list = contractDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public List<ContractProductVo> findContractProdcutPrint(String companyId, String time) {
        return contractDao.findContractProdcutPrint(companyId,time);
    }

}
