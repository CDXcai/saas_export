package com.cdx.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdx.dao.cargo.ContractDao;
import com.cdx.dao.cargo.ContractProductDao;
import com.cdx.dao.cargo.ExtCproductDao;
import com.cdx.domain.cargo.Contract;
import com.cdx.domain.cargo.ExtCproduct;
import com.cdx.domain.cargo.ExtCproductExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ExtCproductServiceImpl implements ExtCproductService {
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractDao contractDao;
    @Override
    public void save(ExtCproduct extCproduct) {
        // 生成随机id
        extCproduct.setId(UUID.randomUUID().toString());
        // 获取数量
        BigDecimal cnumber = new BigDecimal(extCproduct.getCnumber()+"");
        // 获取单价
        BigDecimal price = new BigDecimal(extCproduct.getPrice()+"");
        // 计算小计
        extCproduct.setAmount(cnumber.multiply(price).doubleValue());
        // 获取合同信息
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        // 修改合同信息的价格
        contract.setTotalAmount(new BigDecimal(contract.getTotalAmount()+"").add(new BigDecimal(extCproduct.getAmount()+"")).doubleValue());
        // 附件数量+1
        contract.setExtNum(contract.getExtNum() + 1);
        // 保存附件信息
        extCproductDao.insertSelective(extCproduct);
        // 修改合同信息
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void update(ExtCproduct extCproduct) {
        // 获取之前的小计价格
        BigDecimal oldAmout = new BigDecimal(extCproductDao.selectByPrimaryKey(extCproduct.getId()+"").getAmount());
        // 重新计算小计价格
        BigDecimal price = new BigDecimal(extCproduct.getPrice()+"");
        BigDecimal cnumber = new BigDecimal(extCproduct.getCnumber()+"");
        extCproduct.setAmount(price.multiply(cnumber).doubleValue());
        // 修改数据价格
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        //获取合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        // 修改合同价格
        contract.setTotalAmount(new BigDecimal(contract.getTotalAmount()+"").subtract(oldAmout).add(new BigDecimal(extCproduct.getAmount()+"")).doubleValue());
        // 修改合同信息
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        // 删除附件
        // 查询指定id的数据
        ExtCproduct extCproduct = this.findById(id);
        // 保存价格信息
        Double amount = extCproduct.getAmount();
        // 查询合同信息
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        // 修改合同的价格信息
        contract.setTotalAmount(new BigDecimal(contract.getTotalAmount()+"").subtract(new BigDecimal(amount+"")).doubleValue());
        // 附件数量-1
        contract.setExtNum(contract.getExtNum() -1);
        // 保存合同信息
        contractDao.updateByPrimaryKeySelective(contract);
        // 删除附件信息
        extCproductDao.deleteByPrimaryKey(id);

    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        // 分页
        PageHelper.startPage(page,size);
        // 查询数据
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        // 返回分页数据
        return new PageInfo(list);
    }
}
