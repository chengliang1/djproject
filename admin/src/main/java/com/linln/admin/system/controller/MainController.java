package com.linln.admin.system.controller;

import com.linln.admin.system.utils.Floyd;
import com.linln.admin.system.validator.UserValid;
import com.linln.common.constant.AdminConst;
import com.linln.common.data.URL;
import com.linln.common.enums.ResultEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.SpringContextUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.Menu;
import com.linln.modules.system.domain.Upload;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.enums.MenuTypeEnum;
import com.linln.modules.system.service.MenuService;
import com.linln.modules.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 8/14
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 后台主体内容
     */
    @GetMapping("/")
    @RequiresPermissions("index")
    public String main(Model model) {
        // 获取当前登录的用户
        User user = ShiroUtil.getSubject();

        // 菜单键值对(ID->菜单)
        Map<Long, Menu> keyMenu = new HashMap<>(16);

        // 管理员实时更新菜单
        if (user.getId().equals(AdminConst.ADMIN_ID)) {
            List<Menu> menus = menuService.getListBySortOk();
            menus.forEach(menu -> keyMenu.put(menu.getId(), menu));
        } else {
            // 其他用户需从相应的角色中获取菜单资源
            user.getRoles().forEach(role -> {
                role.getMenus().forEach(menu -> {
                    if (menu.getStatus().equals(StatusEnum.OK.getCode())) {
                        keyMenu.put(menu.getId(), menu);
                    }
                });
            });
        }

        // 封装菜单树形数据
        Map<Long, Menu> treeMenu = new HashMap<>(16);
        keyMenu.forEach((id, menu) -> {
            if (!menu.getType().equals(MenuTypeEnum.NOT_MENU.getCode())) {
                if (keyMenu.get(menu.getPid()) != null) {
                    keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
                } else {
                    if (menu.getType().equals(MenuTypeEnum.TOP_LEVEL.getCode())) {
                        treeMenu.put(Long.valueOf(menu.getSort()), menu);
                    }
                }
            }
        });

        model.addAttribute("user", user);
        model.addAttribute("treeMenu", treeMenu);
        return "/main";
    }

    /**
     * 主页
     */
    @GetMapping("/index")
    @RequiresPermissions("index")
    public String index(Model model) {
        return "/system/main/index";
    }

    /**
     * 算法页面
     * @return
     */
    @GetMapping("/floydmap")
    public String floydmap(){
        return "/system/main/floydmap";
    }

    /**
     * 司机招聘
     * @return
     */
    @GetMapping("/recruitment")
    public String recruitment(){
        return "/system/main/recruitment";
    }

    /**
     * 常见问答
     * @return
     */
    @GetMapping("/answer")
    public String answer(){
        return "/system/main/answer";
    }

    /**
     * 新闻动态
     * @return
     */
    @GetMapping("/dynamic")
    public String dynamic(){
        return "/system/main/dynamic";
    }

    /**
     * 感恩节活动
     * @return
     */
    @GetMapping("/grateful")
    public String grateful(){
        return "/system/dynamic/grateful";
    }

    /**
     * 南京代驾司机中途溺亡
     * @return
     */
    @GetMapping("/safety")
    public String safety(){
        return "/system/dynamic/safety";
    }

    /**
     * 联系我们
     * @return
     */
    @GetMapping("/linkus")
    public String linkUs(){
        return "/system/main/linkus";
    }


    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/userInfo")
    @RequiresPermissions("index")
    public String toUserInfo(Model model) {
        User user = ShiroUtil.getSubject();
        model.addAttribute("user", user);
        return "/system/main/userInfo";
    }

    /**
     * 算法计算
     * @return
     */
    @PostMapping("/floyd")
    @RequiresPermissions("index")
    public ResponseEntity<String> floyd(String str){
        /*System.out.println(str+"---");
        String[] s = str.split("_");
        String driverAj = s[0];
        System.out.println(driverAj);*/
        List<int[]> result = Floyd.getResult();
        return ResponseEntity.ok("ok");
    }

    /**
     * 修改用户头像
     */
    @PostMapping("/userPicture")
    @RequiresPermissions("index")
    @ResponseBody
    public ResultVo userPicture(@RequestParam("picture") MultipartFile picture) {
        UploadController uploadController = SpringContextUtil.getBean(UploadController.class);
        ResultVo imageResult = uploadController.uploadPicture(picture);
        if (imageResult.getCode().equals(ResultEnum.SUCCESS.getCode())) {
            User subject = ShiroUtil.getSubject();
            subject.setPicture(((Upload) imageResult.getData()).getPath());
            userService.save(subject);
            ShiroUtil.resetCookieRememberMe();
            return ResultVoUtil.SAVE_SUCCESS;
        } else {
            return imageResult;
        }
    }

    /**
     * 保存修改个人信息
     */
    @PostMapping("/userInfo")
    @RequiresPermissions("index")
    @ResponseBody
    public ResultVo userInfo(@Validated UserValid valid, User user) {

        // 复制保留无需修改的数据
        User subUser = ShiroUtil.getSubject();
        String[] fields = {"id", "username", "password", "salt", "picture", "dept", "roles"};
        EntityBeanUtil.copyProperties(subUser, user, fields);

        // 保存数据
        userService.save(user);
        BeanUtils.copyProperties(user, subUser);
        ShiroUtil.resetCookieRememberMe();
        return ResultVoUtil.success("保存成功", new URL("/userInfo"));
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/editPwd")
    @RequiresPermissions("index")
    public String toEditPwd() {
        return "/system/main/editPwd";
    }

    /**
     * 保存修改密码
     */
    @PostMapping("/editPwd")
    @RequiresPermissions("index")
    @ResponseBody
    public ResultVo editPwd(String original, String password, String confirm) {
        // 判断原来密码是否有误
        User subUser = ShiroUtil.getSubject();
        String oldPwd = ShiroUtil.encrypt(original, subUser.getSalt());
        if (original.isEmpty() || "".equals(original.trim()) || !oldPwd.equals(subUser.getPassword())) {
            throw new ResultException(ResultEnum.USER_OLD_PWD_ERROR);
        }

        // 判断密码是否为空
        if (password.isEmpty() || "".equals(password.trim())) {
            throw new ResultException(ResultEnum.USER_PWD_NULL);
        }

        // 判断两次密码是否一致
        if (!password.equals(confirm)) {
            throw new ResultException(ResultEnum.USER_INEQUALITY);
        }

        // 修改密码，对密码进行加密
        String salt = ShiroUtil.getRandomSalt();
        String encrypt = ShiroUtil.encrypt(password, salt);
        subUser.setPassword(encrypt);
        subUser.setSalt(salt);

        // 保存数据
        userService.save(subUser);
        ShiroUtil.resetCookieRememberMe();
        return ResultVoUtil.success("修改成功");
    }
}
