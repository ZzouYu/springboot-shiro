package com.izy.springshiro01.service.impl;

/**
 * @author zouyu
 * @description
 * @date 2020/5/28
 */

import com.izy.springshiro01.mapper.UserMapper;
import com.izy.springshiro01.pojo.User;
import com.izy.springshiro01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User findUser(String name) {
        Example example = new Example(User.class);
        Example.Criteria name1 = example.createCriteria().andEqualTo("name", name);
        return userMapper.selectOneByExample(example);
    }
}
