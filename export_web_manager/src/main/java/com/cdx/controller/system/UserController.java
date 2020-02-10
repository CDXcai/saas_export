package com.cdx.controller.system;

import com.cdx.common.utils.Encrypt;
import com.cdx.common.utils.MailUtil;
import com.cdx.controller.Base.BaseController;
import com.cdx.controller.Utils.MailProducer.MailProducer;
import com.cdx.domain.system.Dept;
import com.cdx.domain.system.Role;
import com.cdx.domain.system.User;
import com.cdx.service.system.DeptService;
import com.cdx.service.system.RoleService;
import com.cdx.service.system.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleService roleService;
    /**
     * 用户列表查询
     * @return
     */
    @RequestMapping(value = "/list",name = "用户列表查询")
    public String list(@RequestParam(value = "size",defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "page",defaultValue = "1") Integer pageNum){
        // 查询所有的用户数据
        PageInfo pageInfo = userService.findByPage(companyId,pageSize,pageNum);
        // 添加到请求域中
        request.setAttribute("page",pageInfo);
        return "system/user/user-list";
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd",name = "跳转到添加页面")
    public String toAdd(){
        // 查询部门信息
        List<Dept> deptList = deptService.findAll(companyId);
        // 添加到请求域中
        request.setAttribute("deptList",deptList);
        return "system/user/user-add";
    }

    // 发送右键
    @Autowired
    private MailProducer mailProducer;
    // 添加或者修改方法
    @RequestMapping(value = "/edit",name = "添加或者修改方法")
    public String edit(User user){
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        // 报运原来的密码
        String oldPassword = user.getPassword();
        // 密码加密
        user.setPassword(Encrypt.md5(user.getPassword(),user.getEmail()));
        if(StringUtils.isEmpty(user.getId())){
            // 添加
            userService.save(user);
            try {
                // 准备要发送的数据
                Map<String,String> sendMailContent = new HashMap<>();
                // 准备发送的邮箱
                sendMailContent.put("to",user.getEmail());
                // 邮箱的主题
                sendMailContent.put("subject","成功");
                // 右键的内容
                sendMailContent.put("content","oldPassword:" + oldPassword);
                // 发送给中间件信息，通知发送邮件信息
                mailProducer.send(sendMailContent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            // 修改
            userService.update(user);
        }
        // 重定向到列表页面
        return "redirect:/system/user/list.do";
    }

    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate",name = "跳转到修改页面")
    public String toUpdate(String id){
        // 查询部门信息
        List<Dept> deptList = deptService.findAll(companyId);
        // 添加到请求域中
        request.setAttribute("deptList",deptList);
        // 查询用户信息
        User user = userService.findById(id);
        // 添加到请求域中
        request.setAttribute("user",user);
        return "system/user/user-update";
    }


    /**
     * 删除用户数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",name = "删除用户数据")
    public String delete(String id){
        // 删除用户数据
        userService.delete(id);
        // 重定向到列表页面
        return "redirect:/system/user/list.do";
    }


    /**
     * 跳转到角色分配页面
     * @return
     */
    @RequestMapping("/roleList")
    public String roleList(String id){
        // 查询用户信息
        User user = userService.findById(id);
        // 查询所有的权限信息
        List<Role> roleList = roleService.findAll(companyId);
        // 查询用户拥有的权限信息
        List<Role> userRole = roleService.findByUser(id);
        StringBuffer userRoleStr = new StringBuffer();
        for (Role role : userRole) {
            userRoleStr.append(role.getId()+",");
        }
        // 数据添加到请求域中国
        // 判断用户是否无任何权限
        if(userRoleStr.length() > 1){
            request.setAttribute("userRoleStr",userRoleStr.substring(0,userRoleStr.length()-1));
        }
        request.setAttribute("roleList",roleList);
        request.setAttribute("user",user);
        // 转发到user-role页面
        return "system/user/user-role";
    }

    /**
     * 保存用户角色信息
     * @param userid
     * @param roleIds
     * @return
     */
    @RequestMapping("/changeRole")
    public String changeRole(String userid,String roleIds){
        // 修改用户的角色信息
        userService.updateToRole(userid,roleIds);
        // 重定向到列表页面
        return "redirect:/system/user/list.do";
    }

}
