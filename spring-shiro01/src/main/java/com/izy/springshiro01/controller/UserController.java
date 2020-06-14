package com.izy.springshiro01.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zouyu
 * @description
 * @date 2020/5/28
 */
@Controller
public class UserController {
    @GetMapping("user/add")
    @RequiresPermissions("user:add")
    public String add(){
        return "user/add";
    }
    @GetMapping("user/update")
    @RequiresPermissions("user:update")
    public String update(){
        return "user/update";
    }
}
