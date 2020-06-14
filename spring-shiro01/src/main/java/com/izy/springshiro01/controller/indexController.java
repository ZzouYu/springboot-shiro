package com.izy.springshiro01.controller;

import com.izy.springshiro01.service.SearchService;
import com.izy.springshiro01.utils.ParseData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zouyu
 * @description
 * @date 2020/5/28
 */
@Controller
public class indexController {

    @GetMapping({"/","/index"})
    public ModelAndView  index(ModelAndView model){
        model.addObject("index","首页");
        model.setViewName("index");
       return model;
    }
    @GetMapping("/toLogin")
    public String  toLogin(){
        return "login";
    }
    @GetMapping("/login")
    public String  login(String username, String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    @GetMapping("/unAuthronize")
    @ResponseBody
    public String  unAuthronize(){
        return "没有权限访问";
    }


    @Autowired
    SearchService searchService;
    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    @ResponseBody
    public List<Map<String,Object>> search(@PathVariable("keyword") String keyword ,
                                           @PathVariable("pageNo") int pageNo,
                                           @PathVariable("pageSize") int pageSize ) throws IOException {
        return searchService.search(keyword,pageNo,pageSize);
    }
    @GetMapping("/searchKey")
    public String searchKey()  {
        return "search";
    }

    @Autowired
    ParseData parseData;
    @GetMapping("/creatIndex")
    @ResponseBody
    public Boolean  creatIndex(String keyword){
        try {
           return  parseData.parseContent(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
