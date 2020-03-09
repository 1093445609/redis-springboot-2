package com.shiro.redistest.shiro.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRole implements Serializable {

    private long id;
    private long uid;
    private long rid;


}
