package com.cdx.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.controller.Base.BaseController;
import com.cdx.domain.cargo.*;
import com.cdx.domain.system.User;
import com.cdx.domain.vo.ExportProductVo;
import com.cdx.domain.vo.ExportResult;
import com.cdx.domain.vo.ExportVo;
import com.cdx.service.cargo.ContractProductService;
import com.cdx.service.cargo.ContractService;
import com.cdx.service.cargo.ExportProductService;
import com.cdx.service.cargo.ExportService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报运单管理
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;
    @Reference
    private ContractService contractService;
    @Reference
    private ContractProductService contractProductService;
    @RequestMapping("/contractList.do")
    public String contractList(@RequestParam(value = "page",defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "size",defaultValue = "5") Integer pageSize){
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        // 根据企业id查询
        criteria.andCompanyIdEqualTo(companyId);
        // 查询状态为1的
        criteria.andStateEqualTo(1);
        PageInfo page = contractService.findAll(contractExample, pageNum, pageSize);
        // 添加到请求域中
        request.setAttribute("page",page);
        // 请求转发到jsp页面
        return "cargo/export/export-contractList";
    }

    /**
     * 报运数据
     * @return
     */
    @RequestMapping("/toExport")
    public String toExport(String id){
        request.setAttribute("id",id);
        return "cargo/export/export-toExport";
    }

    /**
     * 添加或者修改报运数据
     * @param export
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Export export){
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);
        // 获取登录用户
        User user = (User) session.getAttribute("loginInfo");
        if(StringUtils.isEmpty(export.getId())){
            // 没有id，添加
            // 设置时间
            export.setCreateTime(new Date());
            // 设置创建人id
            export.setCreateBy(user.getId());
            // 设置创建部门
            export.setCreateDept(user.getDeptId());
            exportService.save(export);
            return "redirect:/cargo/export/contractList.do";

        }else {
            // 有id数据，修改
            // 设置时间
            export.setUpdateBy(user.getId());
            export.setUpdateTime(new Date());
            exportService.update(export);
            return "redirect:/cargo/export/list.do";
        }

    }


    /**
     * 查询出口报运数据
     * @return 转发查询页面
     */
    @RequestMapping("/list")
    public String list(@RequestParam(name = "page",defaultValue = "1") Integer pageIndex,
                       @RequestParam(name = "size",defaultValue = "5") Integer pageSize){
        // 根据企业id查询需要报运的数据
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        // 查询分页数据
        PageInfo page = exportService.findAll(exportExample, pageIndex, pageSize);
        // 添加到请求域中
        request.setAttribute("page",page);
        return "cargo/export/export-list";
    }


    /**
     * 跳转到修改页面
     * @param id 报运单id
     * @return 转发页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        // 根据id查询报运单
        Export export = exportService.findById(id);
        // 查询报运单下下所有的货物信息
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria exportProductExampleCriteria = exportProductExample.createCriteria();
        exportProductExampleCriteria.andExportIdEqualTo(export.getId());
        // 查询报运货物信息
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        // 赋值给报运对象
        export.setExportProducts(exportProductList);
        // 添加到请求域中
        request.setAttribute("export",export);
        return "cargo/export/export-update";
    }


    /**
     * 提交报运信息
     * @param id
     * @return
     */
    @RequestMapping("/exportE")
    public String exportE(String id){
        // 查询报运单数据
        Export export = exportService.findById(id);
        // 构建报运单数据
        ExportVo exportVo = new ExportVo();
        // 复制报运单数据
        BeanUtils.copyProperties(export,exportVo);
        // 设置关联关系
        exportVo.setExportId(export.getId());
        // 查询报运单下所有的货物信息
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        // 报运需要上传的货物数据
        List<ExportProductVo> exportProductVoList = new ArrayList<>();
        // 遍历货物信息，创建报运单数据
        for (ExportProduct exportProduct : exportProductList) {
            // 创建上传货物对象
            ExportProductVo exportProductVo = new ExportProductVo();
            // 复制属性
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            // 赋值商品id
            exportProductVo.setExportId(exportProduct.getId());
            exportProductVo.setEid(exportProduct.getId());
            // 添加到集合中
            exportProductVoList.add(exportProductVo);
        }
        // 把上传货物添加到上传报运单对象中
        exportVo.setProducts(exportProductVoList);
        //  发送报运
        WebClient webClient = WebClient.create("http://localhost:9090/ws/export/user");
        webClient.post(exportVo);
        // 更新报运单为以上报状态
        exportService.exportR(export);
        // 查询执行状态的数据
        List<Export> byStatus = exportService.findByStatus(1);
        if(byStatus != null){
            System.out.println(byStatus.size());
        }

//        // 查询报运数据
//        WebClient webClient1 = WebClient.create("http://localhost:9090/ws/export/user/" + id);
//        ExportResult exportResult = webClient1.get(ExportResult.class);
//        // 更新报运单
//        exportService.exportE(exportResult);
        // 重定向到查询页面
        return "redirect:/cargo/export/list.do";
    }
}
