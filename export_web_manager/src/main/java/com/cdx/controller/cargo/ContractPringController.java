package com.cdx.controller.cargo;


import com.alibaba.dubbo.config.annotation.Reference;

import com.cdx.controller.Base.BaseController;
import com.cdx.domain.cargo.Contract;
import com.cdx.domain.vo.ContractProductVo;
import com.cdx.service.cargo.ContractProductService;
import com.cdx.service.cargo.ContractService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class ContractPringController extends BaseController {
    @Reference
    private ContractService contractService;
    @Reference
    private ContractProductService contractProductService;

    @RequestMapping("/print")
    public String print(){
        // 跳转到下载页面
        return "cargo/print/contract-print";
    }

    // 接受用户传递的日期，下载指定日期的出货单,添加模板样式
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws IOException {
        // 查询数据库，获得出货单
        List<ContractProductVo> productVos = contractService.findContractProdcutPrint(companyId, inputDate);


        // 获取模板的输入流
        InputStream is = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        // 获取文档对象
        Workbook wb = new XSSFWorkbook(is);
        // 获取表
        Sheet sheet = wb.getSheetAt(0);
        // 获取第2行
        Row styleRow = sheet.getRow(2);
         // 声明变量
        Row row;
        Cell cell;

        // 当前正在操作的行数
        int lineNumber = 2;
        CellStyle[] cellStyle =new CellStyle[10];
        for (int i = 1; i < 9 ; i++){
            cellStyle[i] = styleRow.getCell(i).getCellStyle();
        }
        // --------------------加入数据内容
        for (ContractProductVo productVo : productVos) {
            // 创建行
            row = sheet.createRow(lineNumber++);
            // 创建客户单元格
            cell = row.createCell(1);
            // 设置客户名称
            cell.setCellValue(productVo.getCustomName());
            // 设置样式为索引为1的行的数据
            cell.setCellStyle(cellStyle[1]);
            // 设置合同号
            cell = row.createCell(2);
            cell.setCellValue(productVo.getContractNo());
            cell.setCellStyle(cellStyle[2]);


            // 货号
            cell = row.createCell(3);
            cell.setCellValue(productVo.getProductNo());
            cell.setCellStyle(cellStyle[3]);

            // 数量
            cell = row.createCell(4);
            cell.setCellValue(productVo.getCnumber());
            cell.setCellStyle(cellStyle[4]);


            // 工厂
            cell = row.createCell(5);
            cell.setCellValue(productVo.getFactoryName());
            cell.setCellStyle(cellStyle[5]);


            // 工厂交期
            cell = row.createCell(6);
            cell.setCellValue(productVo.getDeliveryPeriod());
            cell.setCellStyle(cellStyle[6]);

            // 船期
            cell = row.createCell(7);
            cell.setCellValue(productVo.getShipTime());
            cell.setCellStyle(cellStyle[7]);

            // 贸易条款
            cell = row.createCell(8);
            cell.setCellValue(productVo.getTradeTerms());
            cell.setCellStyle(cellStyle[8]);

        }



        // 创建一个文件输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 把文件输出到文件输出流中
        wb.write(byteArrayOutputStream);
        // 告知浏览器下载文件
        response.setContentType("application/octet-stream;charset=utf-8");
        // 告知浏览器下载文件名称
        String fileName = new String((inputDate+"出货表.xlsx").getBytes("gb2312"),"iso8859-1");// 处理文件名中文乱码的问题
        response.addHeader("Content-Disposition","attachment;filename=" + fileName);
        // 获取通道浏览器的文件输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 告知浏览器响应数据的长度
        response.setContentLength(byteArrayOutputStream.size());
        // 把创建的文件内容写入到通向浏览器的输出流中
        byteArrayOutputStream.writeTo(outputStream);
        // 管理文件输出流
        byteArrayOutputStream.close();
        outputStream.flush();

    }
//    // 接受用户传递的日期，下载指定日期的出货单,手动添加样式
//    @RequestMapping("/printExcel")
//    public void printExcel(String inputDate) throws IOException {
//        // 查询数据库，获得出货单
//        List<ContractProductVo> productVos = contractService.findContractProdcutPrint(companyId, inputDate);
//
//        // 创建文档对象
//        Workbook wb = new XSSFWorkbook();
//        //  获得表对象
//        Sheet sheet = wb.createSheet();
//        // 设置列宽
//        sheet.setColumnWidth(1,26*256);
//        sheet.setColumnWidth(2,11*256);
//        sheet.setColumnWidth(3,29*256);
//        sheet.setColumnWidth(4,11*256);
//        sheet.setColumnWidth(5,15*256);
//        sheet.setColumnWidth(6,10*256);
//        sheet.setColumnWidth(7,10*256);
//        sheet.setColumnWidth(8,8*256);
//        //---------------------------设置标题
//        // 创建变量，保存行号
//        int lineNumber = 0;
//        // 创建一行数据
//        Row row = sheet.createRow(lineNumber++);
//        // 设置行高
//        row.setHeight((short) (36 * 20));
//        // 创建一个单元格数据
//        Cell cell = row.createCell(1);
//        // 合并单元格
//        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
//        // 写入数据内容
//        cell.setCellValue(inputDate + "出货表");
//        // 设置样式
//        CellStyle cellStyle = bigTitle(wb);
//        // 写入到样式中
//        cell.setCellStyle(cellStyle);
//        // -----------------------设置二级标题
//        // 创建第二行数据
//        row = sheet.createRow(lineNumber++);
//        // 客户标题
//        cell = row.createCell(1);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("客户");
//
//        // 合同号
//        cell = row.createCell(2);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("合同号");
//
//
//        // 货号
//        cell = row.createCell(3);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("货号");
//
//        // 数量
//        cell = row.createCell(4);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("数量");
//
//        // 工厂
//        cell = row.createCell(5);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("工厂");
//
//        // 工厂交期
//        cell = row.createCell(6);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("工厂交期");
//
//        // 期限
//        cell = row.createCell(7);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("期限");
//
//
//        // 贸易条款
//        cell = row.createCell(8);
//        // 设置样式
//        cell.setCellStyle(title(wb));
//        // 设置内容
//        cell.setCellValue("贸易条款");
//
//
//
//        // --------------------加入数据内容
//        for (ContractProductVo productVo : productVos) {
//            // 创建行
//            row = sheet.createRow(lineNumber++);
//            // 创建客户单元格
//            cell = row.createCell(1);
//            // 设置客户名称
//            cell.setCellValue(productVo.getCustomName());
//            // 设置样式
//            cell.setCellStyle(text(wb));
//            // 设置合同号
//            cell = row.createCell(2);
//            cell.setCellValue(productVo.getContractNo());
//            cell.setCellStyle(text(wb));
//
//
//            // 货号
//            cell = row.createCell(3);
//            cell.setCellValue(productVo.getProductNo());
//            cell.setCellStyle(text(wb));
//
//            // 数量
//            cell = row.createCell(4);
//            cell.setCellValue(productVo.getCnumber());
//            cell.setCellStyle(text(wb));
//
//
//            // 工厂
//            cell = row.createCell(5);
//            cell.setCellValue(productVo.getFactoryName());
//            cell.setCellStyle(text(wb));
//
//
//            // 工厂交期
//            cell = row.createCell(6);
//            cell.setCellValue(productVo.getDeliveryPeriod());
//            cell.setCellStyle(text(wb));
//
//            // 船期
//            cell = row.createCell(7);
//            cell.setCellValue(productVo.getShipTime());
//            cell.setCellStyle(text(wb));
//
//            // 贸易条款
//            cell = row.createCell(8);
//            cell.setCellValue(productVo.getTradeTerms());
//            cell.setCellStyle(text(wb));
//
//        }
//
//
//
//        // 创建一个文件输出流
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        // 把文件输出到文件输出流中
//        wb.write(byteArrayOutputStream);
//        // 告知浏览器下载文件
//        response.setContentType("application/octet-stream;charset=utf-8");
//        // 告知浏览器下载文件名称
//        String fileName = new String((inputDate+"出货表.xlsx").getBytes("gb2312"),"iso8859-1");// 处理文件名中文乱码的问题
//        response.addHeader("Content-Disposition","attachment;filename=" + fileName);
//        // 获取通道浏览器的文件输出流
//        ServletOutputStream outputStream = response.getOutputStream();
//        // 告知浏览器响应数据的长度
//        response.setContentLength(byteArrayOutputStream.size());
//        // 把创建的文件内容写入到通向浏览器的输出流中
//        byteArrayOutputStream.writeTo(outputStream);
//        // 管理文件输出流
//        byteArrayOutputStream.close();
//        outputStream.flush();
//
//    }






    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        // 创建一个单元格样式
        CellStyle style = wb.createCellStyle();
        // 创建一个字体样式
        Font font = wb.createFont();
        // 设置字体的样式
        font.setFontName("宋体");
        // 设置字体的大小
        font.setFontHeightInPoints((short)16);
        // 字体是否加粗
        font.setBold(true);//字体加粗
        // 把字体设置回样式中
        style.setFont(font);
        // 单元格横向对其方式
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        // 单元格纵向对其方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}
