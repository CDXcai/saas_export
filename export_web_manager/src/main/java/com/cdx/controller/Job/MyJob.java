package com.cdx.controller.Job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.domain.cargo.Export;
import com.cdx.domain.vo.ExportResult;
import com.cdx.service.cargo.ExportService;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;

import java.util.List;


public class MyJob {

    @Reference
    private ExportService exportService;
    /**
     * 查看是否报运成功
     */
    public void queryExport(){
        System.out.println("开始查询报运状态");
        // 查询所有已经上报的报运单数据
        try {
            List<Export> exportList = exportService.findByStatus(1l);
            if(exportList != null) {
                System.out.println("需要查询的数据条数" +exportList.size());
            }else {
                System.out.println("没有需要查询的数据");
            }
            // 遍历查询是否报运成功
            for (Export export : exportList) {
                //        // 查询报运数据
                WebClient webClient1 = WebClient.create("http://localhost:9090/ws/export/user/" + export.getId());
                ExportResult exportResult = webClient1.get(ExportResult.class);
                // 更新报运单
                exportService.exportE(exportResult);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
