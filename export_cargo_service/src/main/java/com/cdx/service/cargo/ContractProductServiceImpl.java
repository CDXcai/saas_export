package com.cdx.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdx.dao.cargo.ContractDao;
import com.cdx.dao.cargo.ContractProductDao;
import com.cdx.dao.cargo.ExtCproductDao;
import com.cdx.domain.cargo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
public class ContractProductServiceImpl implements ContractProductService {
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public void save(ContractProduct contractProduct) {
        // 生成一个随机的id
        contractProduct.setId(UUID.randomUUID().toString());
        {
            /*计算小计*/
            // 获取单价
            BigDecimal price = new BigDecimal(contractProduct.getPrice()+"");
            // 获取数量
            BigDecimal cnumber = new BigDecimal(contractProduct.getCnumber()+"");
            // 计算小计
            Double amount = price.multiply(cnumber).doubleValue();
            contractProduct.setAmount(amount);
        }
        {
            /*计算总价*/
            // 查询合同
            Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
            // 获取原来的总价
            BigDecimal totalAmount = new BigDecimal(contract.getTotalAmount()+"");
            // 加上后来增加的
            BigDecimal amout = new BigDecimal(contractProduct.getAmount()+"");
            // 更新总金额
            contract.setTotalAmount(totalAmount.add(amout).doubleValue());
            //  货物数量+1
            contract.setProNum(contract.getProNum() + 1);
            // 保存到数据库中
            contractDao.updateByPrimaryKeySelective(contract);
        }
        // 讲货物数据保存到数据库中
        contractProductDao.insertSelective(contractProduct);
    }

    /**
     * 修改货物信息
     *
     * @param contractProduct
     */
    @Override
    public void update(ContractProduct contractProduct) {

        // 查询数据库,保存以前的小计
        Double oldPrice = contractProductDao.selectByPrimaryKey(contractProduct.getId()).getAmount();
        {
            /*修改货物的信息*/
            // 修改订单的小计
            // 获取订单的单价
            BigDecimal price = new BigDecimal(contractProduct.getPrice()+"");
            // 获取订单的数量
            BigDecimal anumber = new BigDecimal(contractProduct.getCnumber()+"");
            // 修改总金额
            contractProduct.setAmount(price.multiply(anumber).doubleValue());
            // 修改数据库
            contractProductDao.updateByPrimaryKeySelective(contractProduct);
        }
        {
            /*修改订单的总价*/
            // 查询订单
            Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
            // 减去以前的小计价格
            contract.setTotalAmount(new BigDecimal(contract.getTotalAmount()).subtract(new BigDecimal(oldPrice)).doubleValue());
            // 加上现在的小计价格
            contract.setTotalAmount(new BigDecimal(contract.getTotalAmount()+"").add(new BigDecimal(contractProduct.getAmount()+"")).doubleValue());
            // 修改数据库内容
            contractDao.updateByPrimaryKeySelective(contract);
        }

    }

    @Override
    public void delete(String id) {
        // 该货物已经附件的总价格
        BigDecimal countPrice = new BigDecimal("0");
        // 查询出货物
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        // 获取该货物的价格,加入到货物总价格中
        BigDecimal bigDecimal = new BigDecimal(contractProduct.getAmount()+"");
        countPrice = countPrice.add(new BigDecimal(contractProduct.getAmount()+""));

        {
            /*删除该货物下所有的附件*/
            // 根据货物id查询所有附件数
            ExtCproductExample extCproductExample = new ExtCproductExample();
            ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
            criteria.andContractProductIdEqualTo(id);
            // 查询货物下的所有附件
            List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
            // 遍历删除
            for (ExtCproduct extCproduct : extCproducts) {
                // 添加到价格中
                countPrice = countPrice.add(new BigDecimal(extCproduct.getAmount()+""));
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }
        {
            // 删除货物数据
            contractProductDao.deleteByPrimaryKey(id);

            // 查询出该货物的合同
            Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
            // 修改总计
            contract.setTotalAmount(new BigDecimal(contract.getTotalAmount()+"").subtract(countPrice).doubleValue());
            // 数量-1
            contract.setProNum(contract.getProNum()-1);
            // 保存到数据库中
            contractDao.updateByPrimaryKeySelective(contract);
        }
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        // 分页查询
        PageHelper.startPage(page, size);
        // 查询数据
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        return new PageInfo(list);
    }


    /**
     * 保存数据到数据库中
     * @param products
     */
    @Override
    public void saveList(List<ContractProduct> products) {
        // 遍历保存数据
        for (ContractProduct product : products) {
            this.save(product);
        }
    }

    @Override
    public List<ContractProduct> selectByExample(ContractProductExample contractProductExample) {
        return contractProductDao.selectByExample(contractProductExample);
    }
}
