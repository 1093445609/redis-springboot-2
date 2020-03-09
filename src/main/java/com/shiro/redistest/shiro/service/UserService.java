package com.shiro.redistest.shiro.service;

import com.shiro.redistest.shiro.pojo.User;

import java.util.List;


public interface UserService {

    User getPwdByName(String name);

    List<String> listRoles(String name);


    List<String>  listPermissions(String name);
    Integer  insert(User user);

}
