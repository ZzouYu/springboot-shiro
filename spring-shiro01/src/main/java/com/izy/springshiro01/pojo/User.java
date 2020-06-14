package com.izy.springshiro01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author zouyu
 * @description
 * @date 2020/5/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class User {
    private int id;
    private String name;
    private String password;
    private String perms;
}
