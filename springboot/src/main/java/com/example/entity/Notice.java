package com.example.entity;

import lombok.Data;

/**
 * 系统公告信息
 */
@Data
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private String time;
}
