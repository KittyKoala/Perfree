package com.perfree.controller.admin;

import com.perfree.common.FileUtil;
import com.perfree.common.Pager;
import com.perfree.common.ResponseBean;
import com.perfree.controller.BaseController;
import com.perfree.model.User;
import com.perfree.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * 用户相关
 * @author Perfree
 */
@Controller
@RequestMapping("/admin")
public class UserController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    private UserService userService;

    /**
     * 用户管理列表页
     * @return String
     */
    @RequestMapping("/user")
    public String index() {
        return "admin/pages/user/user_list";
    }

    /**
     * 用户添加页
     * @return String
     */
    @RequestMapping("/user/addPage")
    public String addPage() {
        return "admin/pages/user/user_add";
    }

    /**
     * 头像上传
     * @return String
     */
    @PostMapping("/user/uploadImg")
    @ResponseBody
    public ResponseBean uploadImg(HttpServletRequest request) {
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multiFile = multipartRequest.getFile("file");
            String path = FileUtil.uploadMultiFile(multiFile, uploadPath, "avatar");
            return ResponseBean.success("上传成功", path);
        }catch (Exception e){
            logger.error("上传失败: {}", e.getMessage());
            return ResponseBean.fail("上传失败", e.getMessage());
        }
    }

    /**
     * 用户编辑页
     * @return String
     */
    @GetMapping("/user/editPage/{id}")
    public String editPage(@PathVariable("id") String id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("userForm", user);
        return "admin/pages/user/user_edit";
    }


    /**
     * 添加用户
     * @return String
     */
    @PostMapping("/user/add")
    @ResponseBody
    public ResponseBean add(@RequestBody @Valid User user) {
        if (StringUtils.isBlank(user.getPassword())){
            logger.error("密码不能为空且在6-12字符之间: {}", user.toString());
            return ResponseBean.fail("密码不能为空且在6-12字符之间", null);
        }
        if (userService.getUserByAccount(user.getAccount()) != null){
            logger.error("账户已存在: {}", user.toString());
            return ResponseBean.fail("账户已存在", null);
        }
        if (userService.add(user) > 0) {
            return ResponseBean.success("添加成功", null);
        }
        logger.error("用户添加失败: {}", user.toString());
        return ResponseBean.fail("添加失败", null);
    }

    /**
     * 用户管理列表数据
     * @return String
     */
    @PostMapping("/user/list")
    @ResponseBody
    public Pager<User> list(@RequestBody Pager<User> pager) {
        return userService.list(pager);
    }

    /**
     * 更新标签
     * @return String
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ResponseBean update(@RequestBody @Valid User user) {
        if (userService.update(user) > 0) {
            return ResponseBean.success("更新成功", null);
        }
        logger.error("用户更新失败: {}", user.toString());
        return ResponseBean.fail("更新失败", null);
    }

    /**
     * 删除标签
     * @return String
     */
    @PostMapping("/user/del")
    @ResponseBody
    public ResponseBean del(@RequestBody String ids) {
        String[] idArr = ids.split(",");
        if (Arrays.asList(idArr).contains(getUser().getId().toString())){
            logger.error("不能删除当前登录账户: {}", ids);
            return ResponseBean.fail("不能删除当前登录账户", null);
        }
        if (userService.del(idArr) > 0) {
            return ResponseBean.success("删除成功", null);
        }
        logger.error("用户删除失败: {}", ids);
        return ResponseBean.fail("删除失败", null);
    }

    /**
     * 重置密码
     * @return String
     */
    @PostMapping("/user/resetPassword")
    @ResponseBody
    public ResponseBean resetPassword(@RequestBody User user) {
        if (userService.resetPassword(user) > 0) {
            return ResponseBean.success("重置密码为123456成功", null);
        }
        logger.error("用户重置密码失败: {}", user.toString());
        return ResponseBean.fail("重置密码失败", null);
    }

    /**
     * 更改状态
     * @return String
     */
    @PostMapping("/user/changeStatus")
    @ResponseBody
    public ResponseBean changeStatus(@RequestBody User user) {
        if (userService.changeStatus(user) > 0) {
            return ResponseBean.success("修改成功", null);
        }
        logger.error("用户修改失败: {}", user.toString());
        return ResponseBean.fail("修改失败", null);
    }
}
