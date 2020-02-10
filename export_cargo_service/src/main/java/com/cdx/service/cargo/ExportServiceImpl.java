package com.cdx.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdx.dao.cargo.ExportDao;
import com.cdx.dao.cargo.ExportProductDao;
import com.cdx.dao.cargo.ExtCproductDao;
import com.cdx.dao.cargo.ExtEproductDao;
import com.cdx.domain.cargo.*;
import com.cdx.domain.vo.ExportProductResult;
import com.cdx.domain.vo.ExportResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ExportDao exportDao; // 报运单
    @Autowired
    private ExportProductDao exportProductDao; // 报运货物
    @Autowired
    private ExtEproductDao extEproductDao; // 报运附件
    @Autowired
    private ContractService contractService; // 合同
    @Autowired
    private ContractProductService contractProductService;// 货物列表
    @Autowired
    private ExtCproductDao extCproductDao; // 附件
    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Export export) {
        // 设置id属性
        export.setId(UUID.randomUUID().toString());
        //  分割id
        String[] contractIds = export.getContractIds().split(",");
        // 合同号
        String customerContract = "";
        // 货物数量
        int proNum = 0;
        // 附件数量
        int extNum = 0;
        // 创建报运货物表
        List<ExportProduct> exportProductList = new ArrayList<>();
        for (String contractId : contractIds) {
            // 查询指定id的合同
            Contract contract = contractService.findById(contractId);
            // 获取合同号
            customerContract += contract.getContractNo() + " ";
            // 修改货物和附件数量
            proNum += contract.getProNum();
            extNum += contract.getExtNum();
            // 修改合同的状态
            contract.setState(2);
            contractService.update(contract);
            // 查询合同下所有的货物
            ContractProductExample contractProductExample = new ContractProductExample();
            ContractProductExample.Criteria contractProductCriteria = contractProductExample.createCriteria();
            contractProductCriteria.andContractIdEqualTo(contractId);
            List<ContractProduct> productList = contractProductService.selectByExample(contractProductExample);
            // 遍历货物列表
            for (ContractProduct contractProduct : productList) {
                // 创建报运货物
                ExportProduct exportProduct = new ExportProduct();
                // 赋值属性
                BeanUtils.copyProperties(contractProduct,exportProduct);
                // 设置随机id属性
                exportProduct.setId(UUID.randomUUID().toString());
                // 赋值报运单id
                exportProduct.setExportId(export.getId());
                // 报运报运单
                exportProductDao.insertSelective(exportProduct);
                // 查询货物下所有的附件
                ExtCproductExample extCproductExample = new ExtCproductExample();
                ExtCproductExample.Criteria extCproductCriteria = extCproductExample.createCriteria();
                ExtCproductExample.Criteria criteria = extCproductCriteria.andContractProductIdEqualTo(contractProduct.getId());
                List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
                // 遍历附件，保存报运附件信息
                for (ExtCproduct extCproduct : extCproducts) {
                    // 创建报运附件信息
                    ExtEproduct extEproduct = new ExtEproduct();
                    // 复制数据
                    BeanUtils.copyProperties(extCproduct,extEproduct);
                    // 复制报运货物Id
                    extEproduct.setExportProductId(exportProduct.getId());
                    // 赋值报运单id
                    extEproduct.setExportId(export.getId());
                    // 赋值随机id
                    extEproduct.setId(UUID.randomUUID().toString());
                    // 保存到数据库中
                    extEproductDao.insertSelective(extEproduct);

                }
            }
        }


        //保存合同号
        export.setCustomerContract(customerContract);

        // 修改货物和附件数量
        export.setExtNum(extNum);
        export.setProNum(proNum);
        // 设置报运单状态
        export.setState(0);
        // 保存到数据库中
        exportDao.insertSelective(export);

    }

    @Override
    public void update(Export export) {
        // 更新报运单数据
        exportDao.updateByPrimaryKeySelective(export);
        // 遍历根据货物数据
        if(export != null && export.getExportProducts() != null){
            for (ExportProduct exportProduct : export.getExportProducts()) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        // 分页查询
        PageHelper.startPage(page,size);
        // 查询数据
        List<Export> list = exportDao.selectByExample(example);
        // 返回分页数据
        return new PageInfo(list);
    }

    @Override
    public void exportE(ExportResult result) {
        // 查询指定的报运单
        Export export = exportDao.selectByPrimaryKey(result.getExportId());
        // 更新状态
        export.setState(result.getState());
        // 更新备注
        export.setRemark(result.getRemark());

        // 更新货物信息
        Set<ExportProductResult> exportProductResultSet = result.getProducts();
        if(exportProductResultSet != null) {
            for (ExportProductResult exportProductResult : exportProductResultSet) {
                // 查询报运单货物信息
                ExportProduct exportProduct = exportProductDao.selectByPrimaryKey(exportProductResult.getExportProductId());
                // 修改状态
                exportProduct.setTax(exportProductResult.getTax());
                // 修改数据库
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
        // 修改数据库的报运单数据
        exportDao.updateByPrimaryKeySelective(export);

    }

    @Override
    public void exportR(Export export) {
        // 修改为已上报状态
        export.setState(1);
        // 修改数据库
        exportDao.updateByPrimaryKeySelective(export);

    }

    @Override
    public List<Export> findByStatus(long i) {
        // 根据状态查询
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andStateEqualTo(i);
        return exportDao.selectByExample(exportExample);
    }
}
