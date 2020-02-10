package com.cdx.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.controller.Base.BaseController;
import com.cdx.controller.qiniuyun.FileUploadUtil;
import com.cdx.domain.cargo.ExtCproduct;
import com.cdx.domain.cargo.ExtCproductExample;
import com.cdx.domain.cargo.Factory;
import com.cdx.domain.cargo.FactoryExample;
import com.cdx.service.cargo.ExtCproductService;
import com.cdx.service.cargo.FactoryService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cargo/extCproduct")
public class extCproductController extends BaseController {
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Reference
    private ExtCproductService extCproductService;
    @Reference
    private FactoryService factoryService;
    @RequestMapping("/list")
    public String list(String contractId,String contractProductId,
                       @RequestParam(value = "page",defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "size",defaultValue = "5") Integer pageSize){
        // 分页查询附件信息
        // 组建查询规则,根据货物id查询，
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        // 获取分页查询对象
        PageInfo pageInfo = extCproductService.findAll(extCproductExample, pageNum, pageSize);
        // 添加到请求域中
        request.setAttribute("page",pageInfo);
        // 查询所有厂家信息,根据cype属性查询
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        // 添加到请求域中
        request.setAttribute("factoryList",factoryList);
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        return "cargo/extc/extc-list";
    }


    @RequestMapping("/edit")
    public ModelAndView edit(ExtCproduct extCproduct, MultipartFile productPhoto) throws Exception {
        if(productPhoto != null){
            // 判断是否传递了图片数据
            // 判断之前是否有图片存在
            if(extCproduct.getProductImage() != null){
                // 如果有图片，删除之前的图片
                String productImage = extCproduct.getProductImage();
                Boolean delete = fileUploadUtil.delete(productImage);
                System.out.println(delete);
            }
            // 文件上传到七牛云中
            String fildPath = fileUploadUtil.upload(productPhoto);
            // 修改数据库字段
            extCproduct.setProductImage(fildPath);
        }
        // 判断是否含有id属性
        if(StringUtils.isEmpty(extCproduct.getId())){
            // 如果id为空，为添加
            extCproductService.save(extCproduct);
        }else {
            // 如果id不为空，为修改
            extCproductService.update(extCproduct);
        }

        ModelAndView mv = new ModelAndView();
        // 重定向到列表页面
        mv.setViewName("redirect:/cargo/extCproduct/list.do");
        // 把货物id和合同id添加到作用域中
        Map<String, Object> model = mv.getModel();
        model.put("contractId",extCproduct.getContractId());
        model.put("contractProductId",extCproduct.getContractProductId());
        return mv;
    }


    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,
                           String contractId,
                           String contractProductId){
        // 查询附件信息
        ExtCproduct extCproduct = extCproductService.findById(id);
        // 查询所有厂家信息,根据cype属性查询
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        // 添加到请求域中
        request.setAttribute("factoryList",factoryList);
        // 添加到请求域中
        request.setAttribute("extCproduct",extCproduct);
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        return "cargo/extc/extc-update";
    }

    /**
     * 删除一条附件信息
     * @param id
     * @param contractId
     * @param contractProductId
     * @return
     */
    @RequestMapping("/delete")
    public ModelAndView delete(String id,
                         String contractId,
                         String contractProductId){

        // 删除指定id的数据
        extCproductService.delete(id);


        ModelAndView mv = new ModelAndView();
        // 重定向到列表页面
        mv.setViewName("redirect:/cargo/extCproduct/list.do");
        // 把货物id和合同id添加到作用域中
        Map<String, Object> model = mv.getModel();
        model.put("contractId",contractId);
        model.put("contractProductId",contractProductId);
        return mv;
    }
}
