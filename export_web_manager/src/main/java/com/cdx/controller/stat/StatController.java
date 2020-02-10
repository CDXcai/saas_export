package com.cdx.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.controller.Base.BaseController;
import com.cdx.service.StatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {
    @Reference
    private StatService statService;
    /**
     * 跳转页面
     * @param chartsType
     * @return
     */
    @RequestMapping("/toCharts")
    public String toCharts(String chartsType){
        // 跳转到对应的jsp页面
        return "stat/stat-" + chartsType;
    }


    /**
     * 厂家销量图
     * [
     *                         {value: 335, name: '直接访问'},
     *                         {value: 310, name: '邮件营销'},
     *                         {value: 274, name: '联盟广告'},
     *                         {value: 235, name: '视频广告'},
     *                         {value: 400, name: '搜索引擎'}
     *                     ]
     * @return
     */
    @RequestMapping("/getFactoryData")
    @ResponseBody // 响应json数据
    public List<Map> factory(){
        // 查询企业厂家销量情况
        return statService.findFactoryData(companyId);

    }

    /**
     * 查询系统压力
     * @return
     */
    @RequestMapping("/getOnlineData")
    @ResponseBody
    public List<Map> getOnlineData(){

        return statService.findOnlineData(companyId);
    }

    /**
     * 查询销量排行
     * @return
     */
    @RequestMapping("/getSellData")
    @ResponseBody
    public List<Map> getSellData(){

        return statService.findSellData(companyId);
    }

}
