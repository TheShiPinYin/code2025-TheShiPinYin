package com.example.model.entity;

/**
 * 普通用户信息
 */
//@Data
//public class User extends Account {
//    private Integer id;
//    private String username;
//    private String password;
//    private String name;
//    private String phone;
//    private String email;
//    private String role;
//    private String token;
//    private String avatar;
//
//    // 非数据库字段
//    private String ids;
//    private String[] idsarr;
//    
//}

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Role role;
    private String avatar;
}


