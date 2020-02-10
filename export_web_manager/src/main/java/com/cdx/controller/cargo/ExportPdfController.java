package com.cdx.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.common.utils.BeanMapUtils;
import com.cdx.common.utils.DownloadUtil;
import com.cdx.controller.Base.BaseController;
import com.cdx.dao.cargo.ExportDao;
import com.cdx.domain.cargo.Export;
import com.cdx.domain.cargo.ExportProduct;
import com.cdx.domain.cargo.ExportProductExample;
import com.cdx.service.cargo.ExportProductService;
import com.cdx.service.cargo.ExportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.export.ReadOnlyPartJasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cargo/export")
public class ExportPdfController extends BaseController {
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;

    /**
     * 下载pdf报表
     *
     * @param id
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(String id) {

        try {
            // 创建一个map,用来获取报运单数据
            Map<String, Object> exportMap = new HashMap<>();
            // 查询报运单数据
            Export export = exportService.findById(id);
            // 讲报运单属性转化为map集合
            exportMap = BeanMapUtils.beanToMap(export);
            // 查询报运单下所有的货物信息
            ExportProductExample exportProductExample = new ExportProductExample();
            ExportProductExample.Criteria exportProductExampleCriteria = exportProductExample.createCriteria();
            exportProductExampleCriteria.andExportIdEqualTo(id);
            List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
            // 获取模板的输入流
            InputStream inputStream = session.getServletContext().getResourceAsStream("jasper/export.jasper");

            // 数据填充到pdf中
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, exportMap, new JRBeanArrayDataSource(exportProductList.toArray()));
            // 文件导出的目录
            String filePaht = session.getServletContext().getContextPath() + "/"+id+".pdf";
            // 把PDF文件导出到本地
            JasperExportManager.exportReportToPdfFile(jasperPrint,filePaht);
            // 通知浏览器下载文件
            DownloadUtil downloadUtil = new DownloadUtil();
            downloadUtil.download(filePaht,"货运报表.pdf",response,true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
