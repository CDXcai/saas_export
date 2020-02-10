package com.cdx.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.controller.Base.BaseController;
import com.cdx.domain.cargo.ContractProduct;
import com.cdx.service.cargo.ContractProductService;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传excel文件
 */
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductUploadController extends BaseController {


    @Reference
    private ContractProductService contractProductService;

    /**
     * 转发到添加货物页面
     * @return
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId){
        // 把合同id添加到作用域中
        request.setAttribute("contractId",contractId);
        // 转发到上传文件页面
        return "cargo/product/product-import";
    }


    /**
     * 把文件中的货物添加到数据库中
     * @param excel
     * @return
     */
    @RequestMapping("/import")
    public String importExcel(String contractId,@RequestParam(name = "file") MultipartFile excel) throws IOException {
        // 创建一个货物列表
        List<ContractProduct>  products = new ArrayList<>();

        // 创建一个工作簿对象
        Workbook wb = new XSSFWorkbook(excel.getInputStream());
        // 获得第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        // 获取总共有多少行数据
        int lastRowNum = sheet.getLastRowNum();
        // 遍历行数据,第一行是标题，所以不需要遍历
        for(int i = 1; i <= lastRowNum ; i ++ ){
            // 获取行对象
            Row row = sheet.getRow(i);
            // 创建一个货物对象
            ContractProduct contractProduct = new ContractProduct();
            // 设置企业id和name
            contractProduct.setCompanyId(super.companyId);
            contractProduct.setCompanyName(super.companyName);
            // 设置合同Id
            contractProduct.setContractId(contractId);
            // 获取并设置文件中的生产厂家
            String factoryName = getValue(row.getCell(1));
            contractProduct.setFactoryName(factoryName);
            // 获取货号
            String productNo = getValue(row.getCell(2));
            contractProduct.setProductNo(productNo);
            // 数量
            String cnumber = getValue(row.getCell(3));
            contractProduct.setCnumber(Double.valueOf(cnumber).intValue());
            // 包装单位
            String packingUnit = getValue(row.getCell(4));
            contractProduct.setPackingUnit(packingUnit);
            // 装率
            String loadingRate = getValue(row.getCell(5));
            contractProduct.setLoadingRate(loadingRate);
            // 箱数
            String boxNum = getValue(row.getCell(6));
            contractProduct.setBoxNum(Double.valueOf(boxNum).intValue());
            // 单价
            String price = getValue(row.getCell(7));
            contractProduct.setPrice(Double.parseDouble(price));
            // 货物描述
            String productDesc = getValue(row.getCell(8));
            contractProduct.setProductDesc(productDesc);
            // 要求
            String productRequest = getValue(row.createCell(9));
            contractProduct.setProductRequest(productRequest);
            // 把对象添加到集合中
            products.add(contractProduct);
        }

        // 调用service保存数据
        contractProductService.saveList(products);



        // 把货物id添加到请求域中
        request.setAttribute("contractId",contractId);
        // 再次跳转到当前页面
        return "cargo/product/product-import";
    }


    /**
     * 获取单元格中的数据
     * @return
     */
    public String getValue(Cell cell){
        // 获取cell的类型
        CellType cellType = cell.getCellType();
        // 针对不同的类型，使用不同的方法返回数据内容
        switch (cellType){
            case NUMERIC:
                return cell.getNumericCellValue()+"";
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue()+"";
        }
        return "";
    }
}
