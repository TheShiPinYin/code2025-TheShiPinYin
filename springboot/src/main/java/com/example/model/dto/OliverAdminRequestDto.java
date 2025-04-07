package com.example.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author OliverKim
 * @since 2025-04-07
 */

// 自己百度dto的作用，不要用什么都往entity里面塞

@Data
public class OliverAdminRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    /**
     * 按道理来说，role是不建议直接用字符串的，应该是单独的表配置，这里引用id就行了，不过我就不搞了
     */
    @NotBlank
    private String role;
    private String avatar;
    
}
