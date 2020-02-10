package com.cdx.controller.cargo;


import com.alibaba.dubbo.config.annotation.Reference;

import com.cdx.controller.Base.BaseController;
import com.cdx.controller.qiniuyun.FileUploadUtil;
import com.cdx.domain.cargo.ContractProduct;
import com.cdx.domain.cargo.ContractProductExample;
import com.cdx.domain.cargo.Factory;
import com.cdx.domain.cargo.FactoryExample;
import com.cdx.service.cargo.ContractProductService;
import com.cdx.service.cargo.ContractService;
import com.cdx.service.cargo.FactoryService;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class contractProductController extends BaseController {


    @Reference
    private ContractService contractService;

    @Reference
    private FactoryService factoryService;
    @Reference
    private ContractProductService contractProductService;



    @Autowired
    private FileUploadUtil fileUploadUtil;
    /**
     * 分页查询货物列表页面
     *
     * @param contractId
     * @return
     */
    @RequestMapping("/list")
    public String list(String contractId,
                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "size", defaultValue = "5") Integer pageSize) {
        // 查询所有厂家信息,根据ctype属性查询
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        // 添加到请求作用域中
        request.setAttribute("factoryList", factoryList);
        // 把id添加到请求作用域中
        request.setAttribute("contractId", contractId);
        // 查询指定订单下的所有货物信息
        // 根据合同id查询
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria criteria = contractProductExample.createCriteria();
        criteria.andContractIdEqualTo(contractId);

        PageInfo pageInfo = contractProductService.findAll(contractProductExample, pageNum, pageSize);
        // 把分页信息添加到请求域中
        request.setAttribute("page", pageInfo);
        return "cargo/product/product-list";
    }

    /**
     * 保存货物列表
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {
        // 查询数据库的内容
        ContractProduct contractProductServiceById = contractProductService.findById(contractProduct.getId());

        if(productPhoto != null){
            // 判断是否传递了图片数据
            // 判断之前是否有图片存在
            if(contractProductServiceById != null && contractProductServiceById.getProductImage() != null){
                // 如果有图片，删除之前的图片
                String productImage = contractProductServiceById.getProductImage();
                Boolean delete = fileUploadUtil.delete(productImage);
                System.out.println(delete);
            }
            // 文件上传到七牛云中
            String fildPath = fileUploadUtil.upload(productPhoto);
            // 修改数据库字段
            contractProduct.setProductImage(fildPath);
        }


        // 判断是修改还是添加
        if(StringUtils.isEmpty(contractProduct.getId())){
            // 如果id为空，表示添加

            // 保存信息到数据库中
            contractProductService.save(contractProduct);
        }else {
            // 如果存在id属性，为修改操作
            contractProductService.update(contractProduct);
        }

        //跳转到货物编辑页面
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractProduct.getContractId();
    }


    /**
     * 跳转到修改页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        // 查询所有的生产厂家
        // 查询所有厂家信息,根据ctype属性查询
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(null);
        // 添加到请求作用域中
        request.setAttribute("factoryList", factoryList);
        // 根据id查询该条货物信息
        ContractProduct contractProduct = contractProductService.findById(id);
        // 添加到作用域中
        request.setAttribute("contractProduct",contractProduct);
        return "cargo/product/product-update";
    }

    /**
     * 删除货物信息
     */
    @RequestMapping("/delete")
    public String delete(String id,String contractId){
        // 查询出货物的信息
        ContractProduct contractProduct = contractProductService.findById(id);
        // 删除该条货物
        contractProductService.delete(id);
        // 把合同id添加到请求域中
        request.setAttribute("contractId",contractId);
        //跳转到货物编辑页面
        return "forward:/cargo/contractProduct/list.do";
    }
}
