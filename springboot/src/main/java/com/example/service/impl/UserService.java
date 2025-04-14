package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.service.IUserService;
import com.example.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author OliverKim
 * @since 2025-04-11
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
    
    @Resource
    private UserMapper userMapper;

    @Override
    public String login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return "User not found";
        }
        if (!user.getPassword().equals(password)) {
            return "Incorrect password";
        }
        return TokenUtils.createToken(user.getId().toString() + '-' + user.getRole(), user.getPassword());
    }
}
