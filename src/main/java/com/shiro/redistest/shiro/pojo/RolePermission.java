package com.shiro.redistest.shiro.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermission implements Serializable {

  private long id;
  private long rid;
  private long pid;


}
