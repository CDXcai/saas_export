package com.cdx.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdx.controller.Base.BaseController;
import com.cdx.domain.cargo.Contract;
import com.cdx.domain.cargo.ContractExample;
import com.cdx.domain.system.User;
import com.cdx.service.cargo.ContractService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {
    @Reference
    private ContractService contractService;


    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5") Integer size){
        // 组建查询规则
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        // 根据企业id查询
        criteria.andCompanyIdEqualTo(companyId);
        // 获取登录的用户对象
        User user = (User) session.getAttribute("loginInfo");
        // 判断用户的权限'
        if(user.getDegree() == 4){
            // 普通员工，则只能查看自己添加的内容
            criteria.andCreateByEqualTo(user.getId());
        }else if(user.getDegree() == 3) {
            // 部门管理员，能够看到所有员工的添加的信息
            criteria.andCreateDeptEqualTo(user.getDeptId());
        }else if(user.getDegree() == 2){
            // 如果是区域管理员，则查询区域下所有员工信息
            criteria.andCreateDeptLike(user.getDeptId()+"%");
        }
        // 根据插入时间降序排列
        contractExample.setOrderByClause("create_time desc");
        //分页查询数据
        PageInfo pageInfo = contractService.findAll(contractExample, page, size);
        //添加到请求域中
        request.setAttribute("page",pageInfo);
        return "cargo/contract/contract-list";
    }

    /**
     * 转发到添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "cargo/contract/contract-add";
    }


    /**
     * 添加或者修改方法
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Contract contract){
        // 设置公司id和公司名称
        contract.setCompanyId(companyId);
        contract.setCompanyName(companyName);
        // 判断是否有id属性，如果有，则是修改，如果没有则是添加
        if(contract.getId() != null){
            // 修改
            contractService.update(contract);
        }else {
            // 如果是添加方法，则添加创建人信息
            // 获取登录对象
            User user = (User) session.getAttribute("loginInfo");
            // 设置创建人id
            contract.setCreateBy(user.getId());
            // 设置创建所属的部门
            contract.setCreateDept(user.getDeptId());
            // 添加到数据库中
            contractService.save(contract);
        }
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        // 根据id查询订单信息
        Contract contract = contractService.findById(id);
        // 添加到请求域中
        request.setAttribute("contract",contract);
        // 请求转发到修改页面
        return "cargo/contract/contract-update";
    }



    /**
     * 提交订单状态为1
     * @param id
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String id){
        // 查找到指定的订单状态
        Contract contract = contractService.findById(id);
        // 修改状态为1
        contract.setState(1);
        // 执行修改操作
        contractService.update(contract);
        // 重定向到列表页面
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 去向订单上报状态
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id){
        // 查找到指定的订单状态
        Contract contract = contractService.findById(id);
        // 修改状态为1
        contract.setState(0);
        // 执行修改操作
        contractService.update(contract);
        // 重定向到列表页面
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 删除一条订单信息
     * @param id
     * @return

     */
    @RequestMapping("/delete")
    public String delete(String id){
        contractService.delete(id);
        // 重定向到查询页面
        return "redirect:/cargo/contract/list.do";
    }
}
